package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.repository.MedicalRecordMapper;
import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.service.MedicalRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("medicalRecordService")
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecord> implements MedicalRecordService {

}