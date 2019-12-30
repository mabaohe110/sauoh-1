package cn.sau.sauoh.security.service;

import cn.sau.sauoh.config.MailConfig;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.repository.UserRoleMapper;
import cn.sau.sauoh.security.entity.RegisterUser;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.EmailUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

/**
 * @author nullptr
 * @date 2019/12/29 23:23
 */
@Service
public class AuthService {

    private static final String NO_EXIST = "noExist";
    private static final String REGISTERED = "registered";
    private static final String REGISTERING = "registering";

    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;
    private EmailUtils emailUtils;


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setUtils(EmailUtils emailUtils) {
        this.emailUtils = emailUtils;
    }

    /**
     * 检查指定的字段处于何种状态（一般用于用户名和邮箱的检查）
     *
     * @param filedName 字段名
     * @param filed     字段值
     * @return 字段处于何种状态的描述，包括：noExist不存在、registered已注册（有数据且check_code为空）、
     * registering 注册中（有数据、check_code不为空且create_time离当前时间不超过半个小时）
     */
    @Transactional(rollbackFor = SQLException.class)
    public String fieldStatus(String filedName, String filed) {
        //数据库中是否存在数据，不存在表示可用
        User user = "username".equals(filedName) ? userMapper.selectByUsername(filed) : userMapper.selectByEmail(filed);
        if (user == null) {
            return NO_EXIST;
        }
        //检查check_code
        if (user.getCheckCode() == null) {
            return REGISTERED;
        }
        Instant createTime = user.getCreateTime().toInstant();
        Duration duration = Duration.between(createTime, Instant.now());
        //注册流程已过，将这条记录删除
        if (duration.toMinutes() > 30) {
            userMapper.deleteById(user);
            return NO_EXIST;
        }
        //注册流程中
        return REGISTERING;
    }


    /**
     * 处理创建账户的过程
     *
     * @param registerUser 前端传递过来的User数据
     * @return 存入数据库后的User数据
     */
    @Transactional(rollbackFor = DuplicateKeyException.class)
    public User userRegisterProcess(RegisterUser registerUser) {
        User user = new User();
        //生成了一个随机字符串用于checkCode
        String checkCode = RandomStringUtils.randomAlphanumeric(50);
        while (!checkCodeAvailable(checkCode)) {
            checkCode = RandomStringUtils.randomAlphanumeric(50);
        }

        user.setUsername(registerUser.getUsername());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());
        user.setCheckCode(checkCode);
        user.setCreateTime(Timestamp.from(Instant.now()));
        //这里如果username和email有重复，会因为数据库的 unique 约束而抛出异常，最后由异常处理器负责
        userMapper.insert(user);
        //邮件模板内容
        String url = "http://" + MailConfig.getDefaultHostAndPort() + "/auth/checkaddress?checkcode=" + user.getCheckCode();
        Context context = new Context();
        context.setVariable("emailAddress", user.getEmail());
        context.setVariable("url", url);
        emailUtils.sendEmail(user.getEmail(), "邮箱验证", "addressCheck.html", context);
        return user;
    }

    /**
     * 检查 checkCode 是否可用，即：如果已存在不可用，不存在才可用。
     *
     * @param checkCode checkCode
     * @return true 表示可用、false 表示不可用
     */
    public boolean checkCodeAvailable(String checkCode) {
        return !userMapper.checkCodeExist(checkCode);
    }

    /**
     * 检查邮箱地址的相关过程，返回的结果表示是否处理成功
     *
     * @param checkCode checkCode
     * @return true表示处理成功，false表示处理失败（可能的原因有：checkCode不存在或验证时间已过）
     */
    @Transactional(rollbackFor = SQLException.class)
    public boolean checkEmailAddressProcess(String checkCode) {
        if (!userMapper.checkCodeExist(checkCode)) {
            //不存在checkCode
            return false;
        }
        User user = userMapper.selectByCheckCode(checkCode);
        Duration duration = Duration.between(user.getCreateTime().toInstant(), Instant.now());
        if (duration.toMinutes() > 30L) {
            //存在但超时
            return false;
        }
        user.setCheckCode(null);
        userMapper.updateById(user);
        //为用户添加 patient 身份
        userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(Constant.ROLE_CODE_PATIENT).build());
        return true;
    }

}

