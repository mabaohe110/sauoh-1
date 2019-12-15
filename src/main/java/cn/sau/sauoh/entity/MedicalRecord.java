package cn.sau.sauoh.entity;

import lombok.Data;

@Data
public class MedicalRecord {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private String patientStatus;
    private String doctorAdvice;
}