package cn.sau.sauoh.security.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author nullptr
 * @date 2019/12/29 22:48
 */
@Data
public class LoginUser {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Boolean rememberMe;
}
