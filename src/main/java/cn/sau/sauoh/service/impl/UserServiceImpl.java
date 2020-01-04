package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.RoleMapper;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.service.UserService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.UserVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;
    private RoleMapper roleMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserVM getById(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        List<UserRole> userRoles = userRoleMapper.selectAllByUserId(id);
        List<String> roles = new ArrayList<>();
        userRoles.forEach(userRole -> {
            String str = roleMapper.selectById(userRole.getRoleId()).getName();
            roles.add(str);
        });
        return UserVM.buildeWithUserAndRole(user, roles);
    }

    /**
     * 如果有username、email有重复值，会由于数据库完整性报错
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean saveVm(UserVM vm) {
        if (vm.getRoles().isEmpty()) {
            throw RRException.badRequest("roles不能为空");
        }
        User user = vm.getUser();
        // insert 之后主键回填
        userMapper.insert(user);
        vm.setId(user.getId());
        vm.getRoles().forEach(role -> {
            Integer roleId = roleMapper.selectRoleIdByValue(role);
            if (roleId == null) {
                throw RRException.badRequest("未知的身份字段");
            }
            userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(roleId).build());
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean saveVmBatch(List<UserVM> vmList) {
        vmList.forEach(this::saveVm);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateById(UserVM vm) {
        if (vm.getRoles().isEmpty()) {
            throw RRException.badRequest("无法保存不含身份数据的账号");
        }
        User user = vm.getUser();
        userMapper.updateById(user);
        //user_role直接把原来的全删了再加入新的
        userRoleMapper.deleteAllByUserId(vm.getId());
        vm.getRoles().forEach(role -> {
            Integer roleId = roleMapper.selectRoleIdByValue(role);
            if (roleId == null) {
                throw RRException.badRequest("未知的身份字段");
            }
            userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(roleId).build());
        });
        return true;
    }

    //如果出一个错误就全部回滚
    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateBatchById(List<UserVM> vmList) {
        vmList.forEach(this::updateById);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeById(Serializable id) {
        userRoleMapper.deleteAllByUserId((Integer) id);
        userMapper.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.forEach(this::removeById);
        return true;
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}