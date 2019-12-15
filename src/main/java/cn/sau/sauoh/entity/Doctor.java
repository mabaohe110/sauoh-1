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
    private String hostipal;
    private Integer departmentId;
}