package cn.sau.sauoh.service;

import cn.sau.sauoh.config.MailConfig;
import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.repository.UserMapper;
import cn.sau.sauoh.utils.EmailUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;


/**
 * @author nullptr
 * @date 2019/12/20 13:58
 */
@Service
public class UserService {

    private UserMapper userMapper;
    private EmailUtils emailUtils;

    @Autowired
    public void setMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUtils(EmailUtils emailUtils) {
        this.emailUtils = emailUtils;
    }

    /**
     * 检查指定的邮箱处于何种状态
     *
     * @param username 用户名
     * @return 邮箱处于何种状态的描述，包括：noExist不存在、registered已注册（有数据且check_code为空）、
     * registering 注册中（有数据、check_code不为空且create_time离当前时间不超过半个小时）、
     * overtime 注册过期（有数据、check_code不为空但create_time离当前时间超过了半个小时)
     */
    public String usernameAvailable(String username) {
        //数据库中是否存在数据，不存在表示可用
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return "noExist";
        }
        //检查check_code
        if (user.getCheckCode() == null) {
            return "registered";
        }
        Instant createTime = user.getCreateTime().toInstant();
        Duration duration = Duration.between(createTime, Instant.now());
        return duration.toMinutes() < 30 ? "registering" : "overtime";
    }

    /**
     * 处理创建账户的过程
     *
     * @param input 前端传递过来的 User 数据(经过数据验证)
     * @return 存入数据库后的User数据
     */
    public User userRegisterProcess(User input) {
        String checkCode = RandomStringUtils.randomAlphanumeric(50);
        while (!checkCodeAvailable(checkCode)) {
            checkCode = RandomStringUtils.randomAlphanumeric(50);
        }

        input.setCheckCode(checkCode);
        input.setCreateTime(Timestamp.from(Instant.now()));
        userMapper.insertUser(input);

        String url = "http://" + MailConfig.getDefaultHostAndPort() + "/user/checkmail" + input.getCheckCode();

        Context context = new Context();
        context.setVariable("emailAddress", input.getUsername());
        context.setVariable("url", url);

        emailUtils.sendEmail(input.getUsername(), "邮箱验证", "emailTemplate.html", context);
        return input;
    }

    /**
     * 检查 checkCode 是否可用，即：如果已存在不可用，不存在才可用。
     *
     * @param checkCode checkCode
     * @return true 表示可用、fasle 表示不可用
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
        userMapper.updateByPrimaryKey(user);
        return true;
    }

    public void deleteUser(User user) {
        User exist = userMapper.selectByUsername(user.getUsername());
        userMapper.deleteByPrimaryKey(exist.getId());
    }
}
