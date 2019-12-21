package cn.sau.sauoh.controller;

import cn.sau.sauoh.controller.form.UserForm;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * @author nullptr
 * @date 2019/12/20 19:20
 */
@Controller("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String handleRegisterProcess(@Valid UserForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "registerForm";
        }
        String status = userService.usernameAvailable(form.getUsername());
        if ("overtime".equals(status)) {
            userService.deleteUser(form.turnToUser());
        }
        if ("noexist".equals(status) || "overtime".equals(status)) {
            User user = userService.userRegisterProcess(form.turnToUser());
            return "redirect:/user/profile/" + user.getId();
        }

        String errorMsg = "registered".equals(status) ? "邮箱已被注册！" : "请您检查邮箱验证后直接登陆！";
        errors.reject("emailError", errorMsg);
        return "registerForm";
    }

}
