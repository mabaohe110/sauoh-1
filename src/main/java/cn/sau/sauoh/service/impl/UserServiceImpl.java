package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.*;
import cn.sau.sauoh.service.UserService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.UserVM;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
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
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
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
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean saveVmBatch(List<UserVM> vmList) {
        vmList.forEach(this::saveVm);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
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
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, RRException.class})
    public boolean updateBatchById(List<UserVM> vmList) {
        vmList.forEach(this::updateById);
        return true;
    }

    @Override
    public Map<String, Object> getInfoByUsername(String username) {
        Map<String, Object> userInfo = new LinkedHashMap<>();
        User user = userMapper.selectByUsername(username);
        if(user == null){
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        List<String> roles = roleMapper.selectAllByUsername(username);
        userInfo.put("uservm", UserVM.buildeWithUserAndRole(user, roles));
        Patient patient = patientMapper.selectByUserId(user.getId());
        if(patient != null){
            userInfo.put("patient", patient);
        }
        if(roles.contains("ROLE_DOCTOR")){
            Doctor doctor = doctorMapper.selectByUserId(user.getId());
            if(doctor != null){
                userInfo.put("doctor", doctor);
            }
        }
        return userInfo;
    }
}