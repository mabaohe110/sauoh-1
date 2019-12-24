package cn.sau.sauoh.security.entity;

import lombok.Data;

/**
 * @author nullptr
 * @date 2019/12/24 16:35
 */
@Data
public class LoginUser {
    private String username;
    private String password;
    private Boolean rememberMe;
}