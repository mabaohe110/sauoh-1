//package cn.sau.sauoh.web.rest;
//
//import cn.sau.sauoh.entity.User;
//import cn.sau.sauoh.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author nullptr
// * @date 2019/12/26 16:29
// */
//@RestController
//public class AuthController {
//
//    private AuthService authService;
//
//    @Autowired
//    public void setUserService(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @ExceptionHandler()
//    @PostMapping("/register")
//    public @ResponseBody
//    User userRegister(@RequestBody Map<String, String> registerUser) {
//        //这里不使用 User直接转化是因为 password 字段使用了 JsonIgnore 注解
//        String username = registerUser.get("username");
//        String email = registerUser.get("email");
//        String password = registerUser.get("password");
//        if (username == null || email == null || password == null) {
//
//        }
//        return authService.userRegisterProcess(User.builder().username(username).email(email).password(password).build());
//    }
//
//    @GetMapping("/checkaddress")
//    public @ResponseBody
//    Map<String, String> checkEmailAddressProcess(@RequestParam String checkcode) {
//        String msg = authService.checkEmailAddressProcess(checkcode);
//        Map<String, String> response = new HashMap<>();
//        response.put("result", msg);
//        return response;
//    }
//}
