package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.PatientList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientListMapper {
    List<PatientList> selectAll();
}
