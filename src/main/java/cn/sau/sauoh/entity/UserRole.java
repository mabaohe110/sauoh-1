package cn.sau.sauoh.entity;

import lombok.Data;

@Data
public class UserRole {
    private Integer userId;
    private Integer roleId;

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}