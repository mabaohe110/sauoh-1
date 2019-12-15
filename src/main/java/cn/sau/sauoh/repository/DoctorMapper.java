package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Doctor;
import java.util.List;

public interface DoctorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Doctor record);

    Doctor selectByPrimaryKey(Integer id);

    List<Doctor> selectAll();

    int updateByPrimaryKey(Doctor record);
}