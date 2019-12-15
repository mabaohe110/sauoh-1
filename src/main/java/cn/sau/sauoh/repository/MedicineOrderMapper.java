package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicineOrder;
import java.util.List;

public interface MedicineOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MedicineOrder record);

    MedicineOrder selectByPrimaryKey(Integer id);

    List<MedicineOrder> selectAll();

    int updateByPrimaryKey(MedicineOrder record);
}