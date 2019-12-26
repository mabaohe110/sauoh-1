package cn.sau.sauoh.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sau.sauoh.repository.MedicineOrderMapper;
import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.service.MedicineOrderService;


@Service("medicineOrderService")
public class MedicineOrderServiceImpl extends ServiceImpl<MedicineOrderMapper, MedicineOrder> implements MedicineOrderService {

}