package com.laolu.shipbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 13:01
 */

@Component
public class MailTool {

    @Autowired
    JavaMailSender javaMailSender;


    public boolean sendMail(String to, String title, String content) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(title);
            mailMessage.setText(content);
            mailMessage.setFrom("mail@luwanyi.com");
            mailMessage.setTo(to);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendHtmlMail(String to, String title, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom("mail@luwanyi.com");
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(title);
            //邮件内容
            messageHelper.setText(content, true);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
