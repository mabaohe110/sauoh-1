package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.DoctorList;
import cn.sau.sauoh.entity.Medicine;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.DoctorListMapper;
import cn.sau.sauoh.repository.MedicineMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceAdminServiceImpl extends AdminServiceImpl implements ProvinceAdminService {

    private UserMapper userMapper;

    private MedicineMapper medicineMapper;

    private UserRoleMapper userRoleMapper;

    private DoctorListMapper doctorListMapper;

    @Autowired
    public void setMedicineMapper(MedicineMapper medicineMapper) { this.medicineMapper = medicineMapper; }

    @Autowired
    public void setUserMapper(UserMapper userMapper) { this.userMapper = userMapper; }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) { this.userRoleMapper = userRoleMapper; }

    @Autowired
    public void setDoctorListMapper(DoctorListMapper doctorListMapper) { this.doctorListMapper = doctorListMapper; }

    @Override
    public int insertCityAdmin(User user){
        int flag1 = userMapper.insert(user);
        int user_id = user.getId();
        int role_id = 2;
        UserRole userRole = new UserRole(user_id,role_id);
        int flag2 = userRoleMapper.insert(userRole);
        int flag = 0;
        if(flag1 == flag2){
            flag = 1;
        }
        return flag;
    }

    @Override
    public int deleteCityAdmin(int user_id){
        int role_id = 2;
        int flag1 = userRoleMapper.deleteByPrimaryKey(user_id,role_id);
        int flag2 = userMapper.deleteByPrimaryKey(user_id);
        int flag = 0;
        if(flag1 == flag2){
            flag = 1;
        }
        return flag;
    }

    @Override
    public int updateCityAdmin(User user){ return userMapper.updateByPrimaryKey(user); }

    @Override
    public List<User> selectCityAdmin(){ return userMapper.selectCityAdmin(); }

    @Override
    public int updateMedicinePrice(Medicine medicine){ return medicineMapper.updateByPrimaryKey(medicine); }

    @Override
    public List<DoctorList> selectDoctorUnchecked(){ return doctorListMapper.selectDoctorUnchecked(); }

}
