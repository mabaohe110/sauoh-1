package cn.sau.sauoh.security.controller;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.security.entity.RegisterUser;
import cn.sau.sauoh.security.service.AuthService;
import cn.sau.sauoh.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 登陆相关的controller
 *
 * @author nullptr
 * @date 2019/12/29 20:27
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

//    登陆实际由 Spring Security 接管
//    @PostMapping("/login")
//    public R login(){}

    /**
     * 用户注册时可 ajax 请求查看输入的 username(email) 是否满足唯一性约束，或是否正在注册流程中
     */
    @GetMapping("/check")
    public R checkFiled(@NotNull @RequestParam String fieldName,
                        @NotNull @RequestParam String field) {
        String status = authService.fieldStatus(fieldName, field);
        return R.ok().put("status", status);
    }

    @PostMapping("/register")
    public R handleRegisterProcess(@Valid @RequestBody RegisterUser registerUser, HttpServletResponse response) {
        User user = authService.userRegisterProcess(registerUser);
        return R.created(response).put("msg", "请尽快进行邮箱验证").put("user", user);
    }

    //todo 提供传统的controller，再编写一个view
    @GetMapping("/checkaddress")
    public R checkEmailAddressProcess(@RequestParam String checkcode) {
        if (authService.checkEmailAddressProcess(checkcode)) {
            return R.ok().put("msg", "邮箱验证成功");
        }
        return R.ok().put("msg", "验证失败，请重新注册");
    }
}
