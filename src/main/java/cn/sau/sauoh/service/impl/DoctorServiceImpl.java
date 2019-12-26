package cn.sau.sauoh.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sau.sauoh.repository.DoctorMapper;
import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.service.DoctorService;


@Service("doctorService")
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

}