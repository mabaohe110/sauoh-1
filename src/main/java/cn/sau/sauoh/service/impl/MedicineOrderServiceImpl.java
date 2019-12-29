package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.repository.MedicineMapper;
import cn.sau.sauoh.repository.MedicineOrderMapper;
import cn.sau.sauoh.service.MedicineOrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("medicineOrderService")
public class MedicineOrderServiceImpl extends ServiceImpl<MedicineOrderMapper, MedicineOrder> implements MedicineOrderService {

    private MedicineMapper medicineMapper;

    @Autowired
    public void setMedicineMapper(MedicineMapper medicineMapper) {
        this.medicineMapper = medicineMapper;
    }

    @Override
    public Map<String, Object> pageToMap(Page<MedicineOrder> page) {

        page.getRecords().forEach(item -> {

        });
        return null;
    }
}