package cn.sau.sauoh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Boolean checked;

    public Doctor(Integer userId, String name, String sex, String phone, Date workedTime, String level, String hospital, Integer departmentId, Boolean checked) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.workedTime = workedTime;
        this.level = level;
        this.hospital = hospital;
        this.departmentId = departmentId;
        this.checked = checked;
    }
}