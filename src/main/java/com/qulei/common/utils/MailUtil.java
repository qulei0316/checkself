package com.qulei.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


/**
 * Created by Administrator on 2017/12/18.
 */
@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String Sender;

    @Autowired
    private JavaMailSender mailSender;

    //发送普通邮件
    public void sendSimpleMail(String to,String subject,String text) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    //发送html激活邮件
    public void sendHtmlMail(String to,String code){
        MimeMessage message = null;
        try{
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(Sender);
            helper.setTo(to);
            helper.setSubject("邮箱验证激活");

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>邮箱验证</h1>")
                    .append("<h3>请点击激活您的账号</h3>")
                    .append("<a herf='www.baidu.com'>wwww</a>");
            helper.setText(sb.toString(),true);
    }catch (Exception e){
        e.printStackTrace();
    }
        mailSender.send(message);
    }

}
