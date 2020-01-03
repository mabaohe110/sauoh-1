package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.MedicalRecordMapper;
import cn.sau.sauoh.repository.PatientMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.service.PatientService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.PatientVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


@Service("patientService")
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    private UserMapper userMapper;
    private PatientMapper patientMapper;
    private UserRoleMapper userRoleMapper;

    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPatientMapper(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setMedicalRecordMapper(MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordMapper = medicalRecordMapper;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean saveVm(PatientVM vm) {
        //先 user表
        User user = vm.getUser();
        //insert 之后主键回填
        userMapper.insert(user);
        vm.setUserId(user.getId());
        //再patient表
        Patient patient = vm.getPatient();
        patient.setUserId(user.getId());
        patientMapper.insert(patient);
        vm.setPatientId(patient.getId());
        //最后user_role 表
        userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(Constant.ROLE_CODE_PATIENT).build());
        return true;
    }

    @Override
    public boolean saveVmBatch(List<PatientVM> vmList) {
        vmList.forEach(this::saveVm);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateById(Patient patient) {
        if (patient.getId() == null) {
            throw RRException.badRequest("id不能为空");
        }
        Patient saved = patientMapper.selectById(patient.getId());
        if (saved == null) {
            throw RRException.notFound("id不存在");
        }
        if (!saved.getUserId().equals(patient.getUserId())) {
            throw RRException.forbinden("userId字段不能修改");
        }
        patientMapper.updateById(patient);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateBatchById(Collection<Patient> patientList) {
        patientList.forEach(this::updateById);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeById(Serializable id) {
        Patient patient = patientMapper.selectById(id);
        if (patient == null) {
            throw RRException.notFound("id不存在");
        }
        //问诊记录表中的 patientId 全部设置为 null
        List<MedicalRecord> records = medicalRecordMapper.selectAllRecordsByPatientId(patient.getId());
        records.forEach(record -> {
            record.setPatientId(null);
            medicalRecordMapper.update(record, null);
        });
        //删除身份
        userRoleMapper.deleteAllByUserId(patient.getUserId());
        patientMapper.deleteById(id);
        //删除user表
        userMapper.deleteById(patient.getUserId());
        log.warn("delete patient:" + patient.toString());
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.forEach(this::removeById);
        return true;
    }
}