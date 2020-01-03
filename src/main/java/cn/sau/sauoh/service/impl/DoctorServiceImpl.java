package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.DoctorMapper;
import cn.sau.sauoh.repository.MedicalRecordMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.service.DoctorService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service("doctorService")
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {


    private DoctorMapper doctorMapper;
    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;

    private MedicalRecordMapper medicalRecordMapper;

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

    @Autowired
    public void setMedicalRecordMapper(MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordMapper = medicalRecordMapper;
    }


    /**
     * 与 Patient 不同，新建医生用户时并不默认具有 Doctoer 权限，所以 save doctor 时也要添加user_role中的记录
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean save(Doctor doctor) {
        Integer userId = doctor.getUserId();
        doctor.setChecked(Constant.DOCTOR_NON_CHECK);
        doctorMapper.insert(doctor);
        UserRole userRole = UserRole.builder().userId(userId).roleId(Constant.ROLE_CODE_DOCTOR).build();
        userRoleMapper.insert(userRole);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean saveVm(DoctorVM vm) {
        //先 user表
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
    public boolean saveVmBatch(List<DoctorVM> vmList) {
        vmList.forEach(this::saveVm);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateById(Doctor doctor) {
        Doctor saved = doctorMapper.selectById(doctor.getId());
        if (saved == null) {
            throw RRException.notFound("id不存在");
        }
        if (!saved.getUserId().equals(doctor.getUserId())) {
            throw RRException.forbinden("userId字段不能修改");
        }
        doctorMapper.updateById(doctor);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateBatchById(Collection<Doctor> doctorList) {
        doctorList.forEach(this::updateById);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeById(Serializable id) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor.getId() == null) {
            throw RRException.notFound("Id不存在");
        }
        //问诊记录表中的 patientId 全部设置为 null
        List<MedicalRecord> records = medicalRecordMapper.selectAllRecordsByDoctorId(doctor.getId());
        records.forEach(record -> {
            record.setDoctorId(null);
            medicalRecordMapper.update(record, null);
        });
        //删除身份
        userRoleMapper.deleteAllByUserId(doctor.getUserId());
        doctorMapper.deleteById(id);
        //删除user表
        userMapper.deleteById(doctor.getUserId());
        log.warn("delete patient:" + doctor.toString());
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.forEach(this::removeById);
        return true;
    }
}