package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Medicine;
import cn.sau.sauoh.repository.MedicineMapper;
import cn.sau.sauoh.service.MedicineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("medicineService")
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

}