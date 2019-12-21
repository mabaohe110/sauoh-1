package cn.sau.sauoh.controller.form;

import cn.sau.sauoh.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author nullptr
 * @date 2019/12/20 19:24
 */
@Data
public class UserForm {

    @Email(message = "邮箱格式错误！")
    @NotNull(message = "邮箱不能为空！")
    private String username;

    @NotNull(message = "密码不能为空！")
    private String password;

    public User turnToUser() {
        return User.builder().username(this.username).password(this.password).build();
    }
}
