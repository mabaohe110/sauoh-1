package cn.sau.sauoh.security.utils;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 当前登陆用户的工具类
 */
@Component
public class CurrentUser {

    //因为获取登陆用户信息的任务很频繁，所以用final修饰
    private final UserService userService;

    @Autowired
    public CurrentUser(UserService userService) {
        this.userService = userService;
    }

    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public User getCurrentUser() {
        return userService.getByUsername(getCurrentUserName());
    }
}