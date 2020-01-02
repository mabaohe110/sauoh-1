package cn.sau.sauoh.security.service;

import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.repository.RoleMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.security.entity.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring Security 用 UserDetailsService 来抽象用户登陆服务的细节
 * 实现该接口时自定义了账户来源的细节然后在 Spring Security 的配置中加载
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
        if(user == null){
            throw new UsernameNotFoundException("username not found");
        }
        List<Role> roles = roleMapper.selectAllByUserId(user.getId());
        // 如果checkCode为空说明经过了邮箱验证
        boolean enable = user.getCheckCode() == null;
        return new RegisterUser(user.getUsername(), user.getEmail(), user.getPassword(), enable, roles);
    }
}
