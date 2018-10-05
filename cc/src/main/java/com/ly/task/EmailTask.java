package com.ly.task;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.email.MailSenderTool;


@Component
public class EmailTask {
	
	@Autowired
	private MailSenderTool mailTool;
	
	private static Logger logger = Logger.getLogger(EmailTask.class); // 获取logger实例
	
	@Scheduled(cron = "0 0/2  *  * * ? ") // 每5秒执行一次
	public void task01() throws MessagingException, FileNotFoundException, IOException {
		logger.info("普通Info信息");
		// logger.error("报错信息", new IllegalArgumentException("非法参数"));
		
		
		mailTool.creatTemplate("2039288191@qq.com", "日程提醒");
		
		System.out.println("111");
	}

}
