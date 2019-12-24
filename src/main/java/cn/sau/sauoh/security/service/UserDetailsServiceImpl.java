package cn.sau.sauoh.security.service;

import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.repository.RoleMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.security.entity.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nullptr
 * @date 2019/12/24 16:36
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;
    private RoleMapper roleMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        List<Role> roles = roleMapper.selectAllByUserId(user.getId());

        return JwtUser.builder().username(user.getUsername()).password(user.getPassword())
                    .enable(user.getCheckCode()==null).authorities(roles).build();
    }
}
