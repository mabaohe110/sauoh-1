package cn.sau.sauoh.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author nullptr
 * @date 2019/12/20 15:27
 */
@Component
@Slf4j
public class EmailUtils {

    private static final String SENDER_ADDRESS = "2646009241@qq.com";

    private JavaMailSender mailSender;
    private SpringTemplateEngine thymeleaf;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setThymeleaf(SpringTemplateEngine thymeleaf) {
        this.thymeleaf = thymeleaf;
    }

    /**
     * 发送邮件（异步执行）
     *
     * @param to      收件人邮箱地址
     * @param subject 邮箱主题
     * @param temp    模板名
     * @param context 邮箱内容，应使用已装填的 context
     */
    @Async
    public void sendEmail(String to, String subject, String temp, Context context) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            String emailText = thymeleaf.process("emailTemp/" + temp, context);
            helper.setText(emailText, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件格式错误！");
        }
    }
}