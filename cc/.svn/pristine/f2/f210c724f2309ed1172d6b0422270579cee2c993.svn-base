package com.ly.email.ref;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.chainsaw.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Test {
	/*MimeMessage mimeMsg = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, false, "utf-8");
	mimeMsg.setContent("333", "text/html");
	helper.setTo("1039288191@qq.com");
	helper.setSubject("333");
	helper.setFrom("1039288191@qq.com");
	mailSender.send(mimeMsg);*/
	
	
	@Autowired
	private static JavaMailSender mailSender;

	public static void main(String[] args) {
		
	
	//JavaMailSender  javaMailSender = mailSender.createMimeMessage();
    MimeMessage mime = mailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
        helper = new MimeMessageHelper(mime, true, "utf-8");
        helper.setTo("1039288191@qq.com");// 收件人邮箱地址
        helper.setFrom("1039288191@qq.com");// 收件人
        helper.setSubject("SpringMailTest");// 主题
        helper.setText("测试Spring自带邮件发送功能");// 正文
    } catch (MessagingException me) {
        me.printStackTrace();
    }
    mailSender.send(mime);
	
	}
}
