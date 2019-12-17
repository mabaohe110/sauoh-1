package cn.sau.sauoh.entity;

import lombok.Data;

@Data
public class Department {
    private Integer id;
    private String name;

    public Department(String name) {
        this.name = name;
    }
}