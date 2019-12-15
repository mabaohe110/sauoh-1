package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Patient record);

    Patient selectByPrimaryKey(Integer id);

    List<Patient> selectAll();

    int updateByPrimaryKey(Patient record);
}