package cn.sau.sauoh.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sau.sauoh.repository.MedicineMapper;
import cn.sau.sauoh.entity.Medicine;
import cn.sau.sauoh.service.MedicineService;


@Service("medicineService")
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

}