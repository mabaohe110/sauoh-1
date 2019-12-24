package cn.sau.sauoh.web;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.AuthService;
import cn.sau.sauoh.web.rest.err.BadRequestAlertException;
import cn.sau.sauoh.web.rest.err.EmailAlreadyUsedException;
import cn.sau.sauoh.web.rest.err.UsernameAlreadyUsedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@RequestBody Map<String, String> registerUser) {
        String username = registerUser.get("username");
        if(username == null) {
            throw new BadRequestAlertException("username is null", "User", "usernameNull");
        }
        String email = registerUser.get("email");
        if(email == null){
            throw new BadRequestAlertException("email is null", "User", "emailNull");
        }
        String password = registerUser.get("password");
        if (password == null) {
            throw new BadRequestAlertException("email is null", "User", "emailNull");
        }
        if("registered".equals(authService.fieldStatus("username", username))
                || "registering".equals(authService.fieldStatus("username", username))){
            throw new UsernameAlreadyUsedException();
        }
        if("registered".equals(authService.fieldStatus("email", email))
                || "registering".equals(authService.fieldStatus("username", username))){
            throw new EmailAlreadyUsedException();
        }
        //todo 返回正确的restful内容
        authService.userRegisterProcess(User.builder().username(username).email(email).password(password).build());
        return ResponseEntity.ok().build();
    }

    //todo 完成邮箱地址验证
    @GetMapping("/checkaddress")
    public String checkEmailAddressProcess(@RequestParam String checkcode) {
        authService.checkEmailAddressProcess(checkcode);
        return "registered";
    }
}
