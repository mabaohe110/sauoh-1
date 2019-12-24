package cn.sau.sauoh.web;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/22 11:44
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setUserService(AuthService authService) {
        this.authService = authService;
    }

    @ExceptionHandler()
    @PostMapping("/register")
    public @ResponseBody User userRegister(@RequestBody Map<String, String> registerUser) {
        String username = registerUser.get("username");
        String email = registerUser.get("email");
        String password = registerUser.get("password");
        //todo 对上面三个值进行验证

        return authService.userRegisterProcess(User.builder().username(username).email(email).password(password).build());
    }

    @GetMapping("/checkaddress")
    public String checkEmailAddressProcess(@RequestParam String checkcode) {
        authService.checkEmailAddressProcess(checkcode);
        return "registered";
    }
}
