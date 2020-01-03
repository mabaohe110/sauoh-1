package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.web.vm.MedicineOrderVM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface MedicineOrderService extends IService<MedicineOrder> {

    MedicineOrderVM getByMedicalRecordId(Integer mrId);

    boolean saveVm(MedicineOrderVM vm);

    boolean saveVmBatch(List<MedicineOrderVM> vmList);

    boolean updateByMrId(MedicineOrderVM vm);

    boolean updateByMrIdVmBatch(List<MedicineOrderVM> vmList);

    boolean removeByMrId(Integer mrId);

    boolean removeByMrIds(Integer[] mrIds);
}

