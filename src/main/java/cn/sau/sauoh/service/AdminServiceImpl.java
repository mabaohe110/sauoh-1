package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.entity.PatientList;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.PatientListMapper;
import cn.sau.sauoh.repository.PatientMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl {

    private UserMapper userMapper;

    private UserRoleMapper userRoleMapper;

    private PatientMapper patientMapper;

    private PatientListMapper patientListMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }
    @Autowired
    public void setPatientMapper(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }
    @Autowired
    public void setPatientListMapper(PatientListMapper patientListMapper) {
        this.patientListMapper = patientListMapper;
    }

    int insertPatient(User user, UserRole userRole, Patient patient){
        int flag1 = userMapper.insert(user);
        int user_id = user.getId();
        int role_id = 4;
        int flag2 = userRoleMapper.insert(userRole);
        int flag3 = patientMapper.insert(patient);
        int flag = 0;
        if(flag1 == flag2 && flag2 == flag3){
            flag = 1;
        }
        return flag;
    }

    int deletePatient(int patient_id,int user_id ){
        int role_id = 4;
        int flag1 = patientMapper.deleteByPrimaryKey(patient_id);
        int flag2 = userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        int flag3 = userMapper.deleteByPrimaryKey(user_id);
        int flag = 0;
        if(flag1 == flag2 && flag2 == flag3){
            flag = 1;
        }
        return flag;
    }

    int updatePatient(Patient patient){
        return patientMapper.updateByPrimaryKey(patient);
    }

    List<PatientList> selectPatient(){
        return patientListMapper.selectAll();
    }
}
