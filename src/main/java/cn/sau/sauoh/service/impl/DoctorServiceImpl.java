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
import cn.sau.sauoh.web.vm.DoctorRecordVM;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 与 Patient 不同，新建医生时用户并不默认具有 Doctor 权限，所以 save doctor 时也要添加 user_role中的记录
     */
    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean save(Doctor doctor) {
        Integer userId = doctor.getUserId();
        //设置为未审核
        doctor.setChecked(Constant.DOCTOR_NON_CHECK);
        doctorMapper.insert(doctor);
        UserRole userRole = UserRole.builder().userId(userId).roleId(Constant.ROLE_CODE_DOCTOR).build();
        userRoleMapper.insert(userRole);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
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
    public DoctorRecordVM getVmById(Integer id) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor == null) {
            //controller 中进行了null值的处理
            return null;
        }
        List<MedicalRecord> mrList = medicalRecordMapper.selectAllRecordsByDoctorId(id);
        //好评次数
        AtomicInteger applauseCount = new AtomicInteger(0);
        mrList.forEach(mr -> {
            if (mr.getPatientAppraise().compareTo(7) >= 0) {
                applauseCount.getAndIncrement();
            }
        });
        float applauseRase = (float) (applauseCount.get() / mrList.size());
        return DoctorRecordVM.builder().id(doctor.getId()).doctorName(doctor.getName()).phone(doctor.getPhone())
                .hospital(doctor.getHospital()).level(doctor.getLevel()).workTime(doctor.getWorkedTime())
                .recordNums(mrList.size()).applauseRase(applauseRase).build();
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean updateById(Doctor doctor) {
        Doctor saved = doctorMapper.selectById(doctor.getId());
        if (saved == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        if (!saved.getUserId().equals(doctor.getUserId())) {
            throw RRException.forbinden(Constant.ERROR_MSG_USERID_CAN_NOT_UPDATE);
        }
        doctorMapper.updateById(doctor);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean updateBatchById(Collection<Doctor> doctorList) {
        doctorList.forEach(this::updateById);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean removeById(Serializable id) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor.getId() == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        doctorMapper.deleteById(id);
        //删除user表，user_role会级联删除
        userMapper.deleteById(doctor.getUserId());
        log.warn("delete doctor:" + doctor.toString());
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        log.warn("delete doctors:" + idList.size());
        idList.forEach(this::removeById);
        return true;
    }
}