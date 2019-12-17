package cn.sau.sauoh.entity;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private Integer userId;
    private String name;
    private String sex;

    public Patient(Integer userId, String name, String sex) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
    }

    public Patient() {
    }
}