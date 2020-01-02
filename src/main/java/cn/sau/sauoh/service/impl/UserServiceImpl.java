package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.*;
import cn.sau.sauoh.service.UserService;
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
import java.util.concurrent.atomic.AtomicBoolean;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;
    private RoleMapper roleMapper;
    private PatientMapper patientMapper;
    private DoctorMapper doctorMapper;

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

    @Autowired
    public void setPatientMapper(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public UserVM getById(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw RRException.notFound("id指定的记录不存在");
        }
        List<UserRole> userRoles = userRoleMapper.selectAllByUserId(id);
        List<String> roles = new ArrayList<>();
        userRoles.forEach(userRole -> {
            String str = roleMapper.selectById(userRole.getRoleId()).getName();
            roles.add(str);
        });
        return UserVM.buildeWithUserAndRole(user, roles);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public UserVM save(UserVM vm) {
        if (vm.getId() != null) {
            throw RRException.badRequest("插入时不能含有主键值");
        }
        if (vm.getRoles().isEmpty()) {
            throw RRException.badRequest("无法保存不含身份数据的用户");
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
        return vm;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean updateById(UserVM vm) {
        if (vm.getId() == null) {
            throw RRException.badRequest("修改时必须指明ID字段");
        }
        if (vm.getRoles().isEmpty()) {
            throw RRException.badRequest("无法保存不含身份数据的用户");
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
        AtomicBoolean flag = new AtomicBoolean(true);
        vmList.forEach(vm -> {
            if (!updateById(vm)) {
                flag.set(false);
            }
        });
        return flag.get();
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeById(Serializable id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw RRException.badRequest("id为null");
        }
        //user_role表
        userRoleMapper.deleteAllByUserId(user.getId());
        userMapper.deleteById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, RRException.class})
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        idList.forEach(this::removeById);
        return true;
    }
}