package cn.sau.sauoh.web;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.AuthService;
import cn.sau.sauoh.web.err.CustomBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/22 11:44
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthRest {

    private AuthService authService;

    @Autowired
    public void setUserService(AuthService authService) {
        this.authService = authService;
    }

    @ExceptionHandler()
    @PostMapping("/register")
    public @ResponseBody
    User userRegister(@RequestBody Map<String, String> registerUser) {
        //这里不使用 User直接转化是因为 password 字段使用了 JsonIgnore 注解
        String username = registerUser.get("username");
        String email = registerUser.get("email");
        String password = registerUser.get("password");
        if (username == null || email == null || password == null) {
            throw new CustomBadRequestException("username, email and password all required");
        }
        return authService.userRegisterProcess(User.builder().username(username).email(email).password(password).build());
    }

    @GetMapping("/checkaddress")
    public @ResponseBody Map<String, String> checkEmailAddressProcess(@RequestParam String checkcode) {
        String msg = authService.checkEmailAddressProcess(checkcode);
        Map<String, String> response = new HashMap<>();
        response.put("result", msg);
        return response;
    }
}
