package com.ly.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.email.EmailServiceImpl;
import com.ly.email.MailModel;

@Component
public class JobTask {
	
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
		System.out.println("111");
	}

}
