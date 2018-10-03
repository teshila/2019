package com.ly.task;

<<<<<<< HEAD
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
=======
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> abebbca1b0253d4b89498f312d2e72e27210c742
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.email.EmailServiceImpl;
import com.ly.email.MailModel;

@Component
public class JobTask {
<<<<<<< HEAD

	@Autowired
	private JavaMailSender mailSender;

	private static Logger logger = Logger.getLogger(JobTask.class); // 获取logger实例

	@Scheduled(cron = "0/15 *  *  * * ? ") // 每5秒执行一次
	public void task01() throws MessagingException {
		StringBuffer emailBf = new StringBuffer();
		logger.info("普通Info信息");
		// logger.error("报错信息", new IllegalArgumentException("非法参数"));

		/*MimeMessage mimeMsg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, false, "utf-8");
		mimeMsg.setContent("333", "text/html");
		helper.setTo("1039288191@qq.com");
		helper.setSubject("333");
		helper.setFrom("1039288191@qq.com");
		mailSender.send(mimeMsg);*/
		
		
		
		
		
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
		
		
		
		
		
		
		
		
		
		

=======
	
	@Autowired
	private EmailServiceImpl email;
	
	private static Logger logger = Logger.getLogger(JobTask.class); // 获取logger实例

	 @Scheduled(cron="0 0/5 *  * * ? ")   //每5秒执行一次  
	public void task01() {
		StringBuffer emailBf = new StringBuffer();
		logger.info("普通Info信息");
		emailBf.append("<table style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'><tbody>");
		// logger.error("报错信息", new IllegalArgumentException("非法参数"));
		 
		 
		 email.sendingEmail("18269215167@139.com", "22", "<a href='/a'>dd</a>");
>>>>>>> abebbca1b0253d4b89498f312d2e72e27210c742
		System.out.println("111");
	}

}
