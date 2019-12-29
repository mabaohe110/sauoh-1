package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.service.UserRoleService;
import cn.sau.sauoh.utils.RRException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserRole> getAllByUserId(Integer userId) {
        if (userMapper.selectById(userId) == null) {
            throw new RRException("userId not exist", 404);
        }
        return userRoleMapper.selectAllByUserId(userId);
    }
}