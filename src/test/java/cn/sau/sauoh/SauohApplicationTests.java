package cn.sau.sauoh;

import cn.sau.sauoh.entity.*;
import cn.sau.sauoh.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SauohApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private PatientListMapper patientListMapper;
    @Autowired
    private DoctorListMapper doctorListMapper;
    @Autowired
    private MedicineMapper medicineMapper;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void contextLoads() {
    }

    /*
     * 患者增删改查测试
     * */
    @Test
    void insertPatientTest() {
        User user = new User("li1", "123456");
        userMapper.insertUser(user);
        int user_id = user.getId();
        int role_id = 4;
        UserRole userRole = new UserRole(user_id, role_id);
        userRoleMapper.insert(userRole);
        Patient patient = new Patient(user_id, "李响", "female");
        patientMapper.insert(patient);

    }

    @Test
    void deletePatientTest() {
        int patient_id = 6;
        int user_id = 13;
        int role_id = 4;
        patientMapper.deleteByPrimaryKey(patient_id);
        userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        userMapper.deleteByPrimaryKey(user_id);

    }

    @Test
    void updatePatientTest() {
        //User user = new User();
        //user要改的数据
        //userMapper.updateByPrimaryKey(user);
        Patient patient = new Patient();
        //patient要改的数据
        patient.setId(7);
        patient.setUserId(18);
        patient.setName("李不响");
        patient.setSex("female");
        patientMapper.updateByPrimaryKey(patient);

    }

    @Test
    void selectPatientTest() {
        List<PatientList> patientLists = patientListMapper.selectAll();
        System.out.println(patientLists.toString());
    }

    /*
     * 医生增删改查测试
     * */
    @Test
    void insertDoctorTest() {
        User user = new User("zhao1", "123456");
        userMapper.insertUser(user);
        int user_id = user.getId();
        int role_id = 3;
        UserRole userRole = new UserRole(user_id, role_id);
        userRoleMapper.insert(userRole);
        Date date = null;
        try {
            date = simpleDateFormat.parse("2019-12-16");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Doctor doctor
                = new Doctor(user_id, "赵云", "male", "15724600000",
                date, "普通医生", "大连医院", 2, true);
        doctorMapper.insert(doctor);

    }

    @Test
    void deleteDoctorTest() {
        int doctor_id = 3;
        int user_id = 15;
        int role_id = 3;
        doctorMapper.deleteByPrimaryKey(doctor_id);
        userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        userMapper.deleteByPrimaryKey(user_id);

    }

    @Test
    void updateDoctorTest() {
        //User user = new User();
        //user要改的数据
        //userMapper.updateByPrimaryKey(user);
        Date date = null;
        try {
            date = simpleDateFormat.parse("2019-12-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Doctor doctor = new Doctor();
        //doctor要改的数据
        doctor.setId(4);
        doctor.setUserId(16);
        doctor.setName("赵云");
        doctor.setSex("male");
        doctor.setPhone("15724600000");
        doctor.setWorkedTime(date);
        doctor.setLevel("普通医生");
        doctor.setHospital("鞍山医院");
        doctor.setDepartmentId(1);
        doctorMapper.updateByPrimaryKey(doctor);

    }

    @Test
    void selectDoctorTest() {
        List<DoctorList> doctorLists = doctorListMapper.selectAll();
        System.out.println(doctorLists.toString());
    }

    /*
     * 市级管理员增删改查测试
     * */
    @Test
    void insertCityAdminTest() {
        User user = new User("wang1", "123456");
        userMapper.insertUser(user);
        int user_id = user.getId();
        int role_id = 2;
        UserRole userRole = new UserRole(user_id, role_id);
        userRoleMapper.insert(userRole);

    }

    @Test
    void deleteCityAdminTest() {
        int user_id = 17;
        int role_id = 2;
        userRoleMapper.deleteByPrimaryKey(user_id, role_id);
        userMapper.deleteByPrimaryKey(user_id);

    }

    @Test
    void updateCityAdminTest() {
        //User user = new User();
        //user要改的数据
        //userMapper.updateByPrimaryKey(user);
    }

    @Test
    void selectCityAdminTest() {
        List<User> cityAdminList = userMapper.selectCityAdmin();
        System.out.println(cityAdminList.toString());
    }

    @Test
    void updateMedicineTest() {
        Medicine medicine = new Medicine();
        medicine.setId(1);
        medicine.setName("感冒特效药");
        medicine.setPrice(new BigDecimal("9.99"));
        medicineMapper.updateByPrimaryKey(medicine);
    }

    @Test
    void selectUncheckedTest(){
        List<DoctorList> doctorLists = doctorListMapper.selectDoctorUnchecked();
        System.out.println(doctorLists.toString());
    }
}
