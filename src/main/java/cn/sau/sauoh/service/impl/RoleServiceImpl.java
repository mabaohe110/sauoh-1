package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.repository.RoleMapper;
import cn.sau.sauoh.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}