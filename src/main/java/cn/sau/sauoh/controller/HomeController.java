package cn.sau.sauoh.controller;

import cn.sau.sauoh.controller.form.UserForm;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author nullptr
 * @date 2019/12/22 11:44
 */
@Slf4j
@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegisterProcess(@Valid UserForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        String status = userService.usernameAvailable(form.getEmail());
        if ("overtime".equals(status)) {
            userService.deleteUser(form.turnToUser());
        }
        if ("noExist".equals(status) || "overtime".equals(status)) {
            User user = userService.userRegisterProcess(form.turnToUser());
            return "redirect:/home";
        }
        String errorMsg = "registered".equals(status) ? "邮箱已被注册！" : "请进行邮箱验证后直接登陆！";
        ValidationUtils.rejectIfEmpty(errors, "email", null, errorMsg);
        return "register";
    }

    @GetMapping("/checkaddress")
    public String checkEmailAddressProcess(@RequestParam String checkcode){
        userService.checkEmailAddressProcess(checkcode);
        return "registered";
    }
}
