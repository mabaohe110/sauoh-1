package cn.sau.sauoh.web.vm;

import cn.sau.sauoh.entity.MedicineOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/29 17:04
 */
@Data
@Builder
public class MedicineOrderVM {

    @NotNull
    private Integer medicalRecordId;
    /**
     * 一个订单下的所有药品
     * key(String) 药品名
     * value(Integer) 药品数量
     */
    @NotNull
    private Map<String, Integer> medicines;

    public static MedicineOrderVM buildByMoList(List<MedicineOrder> moList) {
        Integer mrId = moList.get(0).getMedicalRecordId();
        //LinkedHashMap 保证有序
        Map<String, Integer> medicines = new LinkedHashMap<>();
        moList.forEach(medicineOrder -> medicines.put(medicineOrder.getMedicineName(), medicineOrder.getMedicineNum()));
        return MedicineOrderVM.builder().medicalRecordId(mrId).medicines(medicines).build();
    }

    @JsonIgnore
    public List<MedicineOrder> getMoList() {
        List<MedicineOrder> moList = new ArrayList<>();
        medicines.forEach((medicineName, medicineNum) -> moList.add(MedicineOrder.builder().medicalRecordId(medicalRecordId).
                medicineName(medicineName).medicineNum(medicineNum).build()));
        return moList;
    }
}
