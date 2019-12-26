package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}