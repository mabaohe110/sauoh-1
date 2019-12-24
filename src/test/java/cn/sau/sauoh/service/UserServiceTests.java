package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author nullptr
 * @date 2019/12/21 20:03
 */
@SpringBootTest
public class UserServiceTests {

    private UserMapper mapper;
    private AuthService service;

    @Autowired
    public void setService(AuthService service) {
        this.service = service;
    }

    @Autowired
    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void usernameAvailableTest() {
        Assert.isTrue("noExist".equals(service.fieldStatus("username", "一个数据库中没有的用户名")), "error position : noExist");
        Assert.isTrue("registered".equals(service.fieldStatus("username", "city_admin")), "error position : registered");
        Assert.isTrue("registering".equals(service.fieldStatus("username", "ttt")), "error position : registering");
        Assert.isTrue("overtime".equals(service.fieldStatus("username", "wang1")), "error position : overtime");
    }

    @Test
    @Transactional
    @Rollback
    void userRegisterProcessTest() {
        User user = User.builder().username("justitacsl@outlook.com").
                password("password").build();

        service.userRegisterProcess(user);
        Assert.notNull(mapper.selectByUsername("justitacsl@outlook.com"), "userRegisterProcess未成功保存数据");
    }

    @Test
    void checkCodeAvailableTest() {
        Assert.isTrue(!service.checkCodeAvailable("123123"), "checkCodeAvailable false验证时失败");
        Assert.isTrue(service.checkCodeAvailable("321321"), "checkCodeAvailable true验证时失败");
    }

    @Test
    @Transactional
    @Rollback
    void checkEmailAddressProcessTest() {
        Assert.isTrue(!service.checkEmailAddressProcess("这个验证码不存在"), "checkEmailAddressProcess false验证时失败");
        Assert.isTrue(!service.checkEmailAddressProcess("123123"), "checkEmailAddressProcess false验证时失败");
        Assert.isTrue(service.checkEmailAddressProcess("ttt"), "checkEmailAddressProcess true验证时失败");

        Assert.isNull(mapper.selectByPrimaryKey(28).getCheckCode(), "checkEmailAddressProcess 未成功删除checkCode");
    }
}
