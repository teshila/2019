package com.ly.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.email.EmailUtil;


@Component
public class EmailTask {
	

	private static Logger logger = Logger.getLogger(EmailTask.class); // 获取logger实例
	
	@Scheduled(cron = "0 0/8  *  * * ? ") // 每5秒执行一次
	public void task01() throws MessagingException, FileNotFoundException, IOException {
		StringBuffer emailBf = new StringBuffer();
		logger.info("普通Info信息");
		// logger.error("报错信息", new IllegalArgumentException("非法参数"));

		String html = "<html><head>" + "</head><body>werere" + "</body></html>";

		// sendEmail("785427346@qq.com", "yeah", html, true);

		EmailUtil.sendFileEmail("18269215167@139.com", "yeah", html, new File("c:/t.html"));

		System.out.println("111");
	}

}
