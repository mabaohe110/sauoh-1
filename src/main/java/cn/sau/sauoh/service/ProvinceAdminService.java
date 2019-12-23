package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.DoctorList;
import cn.sau.sauoh.entity.Medicine;
import cn.sau.sauoh.entity.User;

import java.util.List;

public interface ProvinceAdminService extends AdminService{
    int insertCityAdmin(User user);

    int deleteCityAdmin(int user_id);

    int updateCityAdmin(User user);

    List<User> selectCityAdmin();

    int updateMedicinePrice(Medicine medicine);

    List<DoctorList> selectDoctorUnchecked();
}
