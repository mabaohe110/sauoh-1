package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.*;
import cn.sau.sauoh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl {

    private UserMapper userMapper;

    private UserRoleMapper userRoleMapper;

    private PatientMapper patientMapper;

    private PatientListMapper patientListMapper;

    private DoctorMapper doctorMapper;

    private DoctorListMapper doctorListMapper;

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

    @Autowired
    public void setDoctorListMapper(DoctorListMapper doctorListMapper) {
        this.doctorListMapper = doctorListMapper;
    }

    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper){ this.doctorMapper = doctorMapper;}

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

    int insertDoctor(User user, UserRole userRole, Doctor doctor){
        int flag1 = userMapper.insert(user);
        int user_id = user.getId();
        int role_id = 3;
        int flag2 = userRoleMapper.insert(userRole);
        int flag3 = doctorMapper.insert(doctor);
        int flag = 0;
        if(flag1 == flag2 && flag2 == flag3){
            flag = 1;
        }
        return flag;
    }

    int deleteDoctor(int doctor_id,int user_id ){
        int role_id = 4;
        int flag1 = doctorMapper.deleteByPrimaryKey(doctor_id);
        int flag2 = userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        int flag3 = userMapper.deleteByPrimaryKey(user_id);
        int flag = 0;
        if(flag1 == flag2 && flag2 == flag3){
            flag = 1;
        }
        return flag;
    }

    int updateDoctor(Doctor doctor){ return doctorMapper.updateByPrimaryKey(doctor); }

    List<DoctorList> selectDoctor(){ return doctorListMapper.selectAll(); }

}
