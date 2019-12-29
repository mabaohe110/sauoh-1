package cn.sau.sauoh.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sau.sauoh.repository.RoleMapper;
import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.service.RoleService;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}