package cn.sau.sauoh.entity;

import lombok.Data;

@Data
public class MedicineOrder {
    private Integer id;
    private Integer medicalRecordId;
    private Integer medicineId;
    private Integer medicineNum;
}