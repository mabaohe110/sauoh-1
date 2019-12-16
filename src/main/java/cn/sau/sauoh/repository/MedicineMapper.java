package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Medicine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Medicine record);

    Medicine selectByPrimaryKey(Integer id);

    List<Medicine> selectAll();

    int updateByPrimaryKey(Medicine record);
}