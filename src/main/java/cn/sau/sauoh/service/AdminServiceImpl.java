package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.*;
import cn.sau.sauoh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

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
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public int insertPatient(User user, Patient patient) {
        int flag1 = userMapper.insert(user);
        int user_id = user.getId();
        int role_id = 4;
        UserRole userRole = new UserRole(user_id, role_id);
        int flag2 = userRoleMapper.insert(userRole);
        int flag3 = patientMapper.insert(patient);
        int flag = 0;
        if (flag1 == flag2 && flag2 == flag3) {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int deletePatient(int patient_id, int user_id) {
        int role_id = 4;
        int flag1 = patientMapper.deleteByPrimaryKey(patient_id);
        int flag2 = userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        int flag3 = userMapper.deleteByPrimaryKey(user_id);
        int flag = 0;
        if (flag1 == flag2 && flag2 == flag3) {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int updatePatient(Patient patient) {
        return patientMapper.updateByPrimaryKey(patient);
    }

    @Override
    public List<PatientList> selectPatient() {
        return patientListMapper.selectAll();
    }

    @Override
    public int insertDoctor(User user, Doctor doctor) {
        int flag1 = userMapper.insert(user);
        int user_id = user.getId();
        int role_id = 3;
        UserRole userRole = new UserRole(user_id, role_id);
        int flag2 = userRoleMapper.insert(userRole);
        int flag3 = doctorMapper.insert(doctor);
        int flag = 0;
        if (flag1 == flag2 && flag2 == flag3) {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int deleteDoctor(int doctor_id, int user_id) {
        int role_id = 4;
        int flag1 = doctorMapper.deleteByPrimaryKey(doctor_id);
        int flag2 = userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        int flag3 = userMapper.deleteByPrimaryKey(user_id);
        int flag = 0;
        if (flag1 == flag2 && flag2 == flag3) {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        return doctorMapper.updateByPrimaryKey(doctor);
    }

    @Override
    public List<DoctorList> selectDoctor() {
        return doctorListMapper.selectAll();
    }

}
