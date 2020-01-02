package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.*;
import cn.sau.sauoh.service.DoctorService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;


@Service("doctorService")
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {


    private DoctorMapper doctorMapper;
    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;

    private MedicalRecordMapper medicalRecordMapper;
    private MedicineOrderMapper medicineOrderMapper;

    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Doctor getById(Serializable id) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor == null) {
            throw new RRException("doctorId不存在", HttpServletResponse.SC_NOT_FOUND);
        }
        return doctor;
    }

    @Autowired
    public void setMedicalRecordMapper(MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordMapper = medicalRecordMapper;
    }

    @Autowired
    public void setMedicineOrderMapper(MedicineOrderMapper medicineOrderMapper) {
        this.medicineOrderMapper = medicineOrderMapper;
    }

    @Override
    @Transactional(rollbackFor = {DuplicateKeyException.class, RRException.class})
    public boolean save(Doctor doctor) {
        Integer userId = doctor.getUserId();
        doctorMapper.insert(doctor);
        UserRole userRole = UserRole.builder().userId(userId).roleId(Constant.ROLE_CODE_DOCTOR).build();
        userRoleMapper.insert(userRole);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DuplicateKeyException.class, RRException.class})
    public boolean save(DoctorVM vm) {
        //先 user 表
        User user = vm.getUser();
        //insert之后回填主键
        userMapper.insert(user);
        vm.setUserId(user.getId());
        //doctor 表
        Doctor doctor = vm.getDoctor();
        doctor.setUserId(user.getId());
        doctorMapper.insert(doctor);
        vm.setDoctorId(doctor.getId());
        //最后userRole表
        UserRole userRole = UserRole.builder().userId(user.getId()).roleId(Constant.ROLE_CODE_DOCTOR).build();
        userRoleMapper.insert(userRole);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DuplicateKeyException.class, RRException.class})
    public boolean updateById(Doctor doctor) {
        Doctor saved = doctorMapper.selectById(doctor.getId());
        if (saved == null) {
            throw RRException.notFound("doctorId 不存在");
        }
        if (!saved.getUserId().equals(doctor.getUserId())) {
            throw RRException.forbinden("不能修改doctor的userId字段");
        }
        doctorMapper.updateById(doctor);
        return true;
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public boolean removeById(Serializable id) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor == null) {
            throw RRException.notFound("doctorId not exist");
        }
        //问诊记录表中的 doctorId 全部设置为 null
        List<MedicalRecord> records = medicalRecordMapper.selectAllRecordsByPatientId(doctor.getId());
        records.forEach(record -> {
            record.setPatientId(null);
            medicalRecordMapper.update(record, null);
        });
        medicalRecordMapper.deleteAllByDoctorId(doctor.getId());
        //删除身份和doctor表
        userRoleMapper.deleteAllByUserId(doctor.getUserId());
        doctorMapper.deleteById(id);
        //删除user表
        userMapper.deleteById(doctor.getUserId());
        log.warn("delete doctor:" + doctor.toString());
        return true;
    }
}