package com.ly.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobTask {

	
	
	@Scheduled(fixedRate=5*1000)
	public void task01(){
		System.out.println("111");
	}
	
	
}
