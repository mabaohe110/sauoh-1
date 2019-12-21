package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.*;

import java.util.List;

public interface AdminService {
    int insertPatient(User user, Patient patient);

    int deletePatient(int patient_id,int user_id );

    int updatePatient(Patient patient);

    List<PatientList> selectPatient();

    int insertDoctor(User user, Doctor doctor);

    int deleteDoctor(int doctor_id,int user_id );

    int updateDoctor(Doctor doctor);

    public List<DoctorList> selectDoctor();
}
