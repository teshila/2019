package com.ly.email.ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailUtils2 {
	
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 获得配置文件属性
     */
    @Value("${mail.username}")
    private String mailFrom;
    @Value("${mail.to}")
    private String mailTo;

    /**
     * 发送简单邮件
     * @param title
     * @param text
     */
    public void simpleMailMessage(String title, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);//哪个账号发送一般就是自己配的
        message.setTo(mailTo);//发到哪
        message.setSubject(title);
        message.setText(text);
        javaMailSender.send(message);
    }
   
    /**
     * html邮件
     * @param title
     * @param text
     */
    public void htmlMailMessage(String title, String text){
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            helper.setSubject(title);
            helper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }
}
