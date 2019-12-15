package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicalRecord;
import java.util.List;

public interface MedicalRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MedicalRecord record);

    MedicalRecord selectByPrimaryKey(Integer id);

    List<MedicalRecord> selectAll();

    int updateByPrimaryKey(MedicalRecord record);
}