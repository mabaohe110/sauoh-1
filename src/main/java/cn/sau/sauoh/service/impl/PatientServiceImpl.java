package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.repository.PatientMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.service.PatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("patientService")
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

}