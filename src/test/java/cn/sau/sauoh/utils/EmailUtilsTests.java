package cn.sau.sauoh.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

/**
 * @author nullptr
 * @date 2019/12/20 15:36
 */
@Slf4j
@SpringBootTest
public class EmailUtilsTests {

    private EmailUtils utils;

    @Autowired
    public void setService(EmailUtils utils) {
        this.utils = utils;
    }

    @Test
    void sendTest() {
        String address = "justitacsl@outlook.com";
        String url = "http://localhost:8080/user/checkmail?checkcode=" + "123123123";

        Context context = new Context();
        context.setVariable("emailAddress", address);
        context.setVariable("url", url);

        utils.sendEmail(address, "邮件测试", "addressCheck.html", context);
        log.debug("邮件发送成功!");
    }
}
