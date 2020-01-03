package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicalRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface MedicalRecordMapper extends BaseMapper<MedicalRecord> {

    List<MedicalRecord> selectAllRecordsByPatientId(Integer patientId);

    List<MedicalRecord> selectAllRecordsByDoctorId(Integer doctorId);

    int deleteAllByPatientId(Integer patientId);

    int deleteAllByDoctorId(Integer id);
}
