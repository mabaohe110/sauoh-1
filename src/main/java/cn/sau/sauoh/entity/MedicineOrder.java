package cn.sau.sauoh.entity;

import lombok.Data;

/**
 * @author nullptr
 */
@Data
public class MedicineOrder {
    private Integer id;
    private Integer medicalRecordId;
    private Integer medicineId;
    private Integer medicineNum;
}