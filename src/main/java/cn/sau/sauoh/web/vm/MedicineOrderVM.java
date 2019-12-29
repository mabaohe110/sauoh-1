package cn.sau.sauoh.web.vm;

import lombok.Data;

import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/29 17:04
 */
@Data
public class MedicineOrderVM {

    private Integer medicalRecordId;
    /**
     * 一个订单下的所有药品
     * key(String) 药品名
     * value(Integer) 药品数量
     */
    private Map<String, Integer> medicines;
}
