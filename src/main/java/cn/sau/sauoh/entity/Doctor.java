package cn.sau.sauoh.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Doctor {
    private Integer id;
    private Integer userId;
    private String name;
    private String sex;
    private String phone;
    private Date workedTime;
    private String level;
    private String hospital;
    private Integer departmentId;

    public Doctor(Integer userId, String name, String sex, String phone, Date workedTime, String level, String hospital, Integer departmentId) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.workedTime = workedTime;
        this.level = level;
        this.hospital = hospital;
        this.departmentId = departmentId;
    }

    public Doctor() {
    }
}