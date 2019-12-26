package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}