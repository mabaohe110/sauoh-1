package cn.sau.sauoh.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

/**
 * @author nullptr
 * @date 2019/12/20 15:36
 */
@Slf4j
@SpringBootTest
public class EmailUtilsTests {

    private EmailUtils utils;
    @Autowired
    public void setService(EmailUtils utils){
        this.utils = utils;
    }

    @Test
    void sendTest() {
        String address = "justitacsl@outlook.com";

        Context context = new Context();
        context.setVariable("msg", "消息内容测试");

        utils.sendEmail(address, "邮件测试", "addressCheck.html",context);
        log.debug("邮件发送成功!");
    }
}
