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
public class AuthServiceImpl implements AuthService {

    private static final String NO_EXIST = "noExist";
    private static final String REGISTERED = "registered";
    private static final String REGISTERING = "registering";
    private static final String OVERTIME = "overtime";

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

    @Override
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
        return duration.toMinutes() < 30 ? REGISTERING : OVERTIME;
    }

    @Override
    public User userRegisterProcess(User input) {
        String checkCode = RandomStringUtils.randomAlphanumeric(50);
        while (!checkCodeAvailable(checkCode)) {
            checkCode = RandomStringUtils.randomAlphanumeric(50);
        }
        input.setCheckCode(checkCode);
        input.setCreateTime(Timestamp.from(Instant.now()));
        userMapper.insertUser(input);
        String url = "http://" + MailConfig.getDefaultHostAndPort() + "/checkaddress?checkcode=" + input.getCheckCode();
        //邮件模板内容
        Context context = new Context();
        context.setVariable("emailAddress", input.getEmail());
        context.setVariable("url", url);
        emailUtils.sendEmail(input.getEmail(), "邮箱验证", "addressCheck.html", context);
        return input;
    }

    @Override
    public boolean checkCodeAvailable(String checkCode) {
        return !userMapper.checkCodeExist(checkCode);
    }

    @Override
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

    @Override
    public void deleteUser(User user) {
        userMapper.deleteByPrimaryKey(user.getId());
    }
}
