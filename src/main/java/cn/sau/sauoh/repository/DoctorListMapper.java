package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.DoctorList;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoctorListMapper {
    List<DoctorList> selectAll();

    List<DoctorList> selectDoctorUnchecked();
}
