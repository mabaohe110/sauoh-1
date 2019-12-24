package cn.sau.sauoh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRole {
    private Integer userId;
    private Integer roleId;
}