package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Patient;
import java.util.List;

public interface PatientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Patient record);

    Patient selectByPrimaryKey(Integer id);

    List<Patient> selectAll();

    int updateByPrimaryKey(Patient record);
}