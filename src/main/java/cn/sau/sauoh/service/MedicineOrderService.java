package cn.sau.sauoh.service;

import cn.sau.sauoh.web.vm.MedicineOrderVM;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.sau.sauoh.entity.MedicineOrder;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface MedicineOrderService extends IService<MedicineOrder> {

    Map<String, Object> pageToMap(Page<MedicineOrder> page);
}

