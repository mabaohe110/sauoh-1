package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.repository.MedicineOrderMapper;
import cn.sau.sauoh.service.MedicineOrderService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.MedicineOrderVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("medicineOrderService")
public class MedicineOrderServiceImpl extends ServiceImpl<MedicineOrderMapper, MedicineOrder> implements MedicineOrderService {

    private MedicineOrderMapper medicineOrderMapper;

    @Autowired
    public void setMedicineOrderMapper(MedicineOrderMapper medicineOrderMapper) {
        this.medicineOrderMapper = medicineOrderMapper;
    }

    @Override
    public MedicineOrderVM getByMedicalRecordId(Integer mrId) {
        List<MedicineOrder> medicineOrderList = medicineOrderMapper.selectAllByMrId(mrId);
        if (!medicineOrderList.isEmpty()) {
            return MedicineOrderVM.buildByMoList(medicineOrderList);
        }
        throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVm(MedicineOrderVM vm) {
        List<MedicineOrder> moList = vm.getMoList();
        moList.forEach(mo -> medicineOrderMapper.insert(mo));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVmBatch(List<MedicineOrderVM> vmList) {
        vmList.forEach(this::saveVm);
        return true;
    }

    /**
     * 更新有可能删除某一条、或修改某一条，所以全部删除再全部插入
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByMrId(MedicineOrderVM vm) {
        Integer mrId = vm.getMedicalRecordId();
        medicineOrderMapper.deleteAllByMrId(mrId);
        vm.getMoList().forEach(mo -> medicineOrderMapper.insert(mo));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByMrIdVmBatch(List<MedicineOrderVM> vmList) {
        vmList.forEach(this::updateByMrId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByMrId(Integer mrId) {
        medicineOrderMapper.deleteAllByMrId(mrId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByMrIds(Integer[] mrIds) {
        for (Integer mrId : mrIds) {
            medicineOrderMapper.deleteAllByMrId(mrId);
        }
        return true;
    }
}