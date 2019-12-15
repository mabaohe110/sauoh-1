package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicineOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MedicineOrder record);

    MedicineOrder selectByPrimaryKey(Integer id);

    List<MedicineOrder> selectAll();

    int updateByPrimaryKey(MedicineOrder record);
}