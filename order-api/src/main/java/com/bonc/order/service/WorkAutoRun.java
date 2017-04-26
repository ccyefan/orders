package com.bonc.order.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class WorkAutoRun{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Autowired
	private WorkService workService;
//	@Scheduled(cron="0 */5 3-4,12-14 * * MON-FRI") //工作日
	public void Tasks(){
    	System.out.println("The time is :"+dateFormat.format(new Date()));
		workService.WorkFlow();  //启动完成后立刻运行
	}
}
