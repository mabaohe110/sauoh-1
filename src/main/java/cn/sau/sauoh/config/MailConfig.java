package cn.sau.sauoh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

/**
 * 邮箱基本配置
 */
@Configuration
public class MailConfig {

    private static final String DEFAULT_SENDER_ADDRESS = "2646009241@qq.com";

    //todo 部署前改IP和端口
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "8080";

    @Bean
    @Autowired
    public MimeMessageHelper mimeMessageHelper(JavaMailSender sender) throws MessagingException {
        return new MimeMessageHelper(sender.createMimeMessage(), true);
    }

    public static String getSenderAddress() {
        return DEFAULT_SENDER_ADDRESS;
    }

    public static String getDefaultHostAndPort() {
        return DEFAULT_HOST + ':' + DEFAULT_PORT;
    }
}
