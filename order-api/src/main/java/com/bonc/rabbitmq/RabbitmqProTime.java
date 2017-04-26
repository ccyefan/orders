package com.bonc.rabbitmq;

import java.util.Timer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class RabbitmqProTime implements CommandLineRunner{

	@Override
	public void run(String... arg0) throws Exception {
		Timer timer = new Timer();
        int delay = 2*1000; //2秒后开始 
        int period = 1000;
        timer.scheduleAtFixedRate(new RabbitmqProConfig(), delay, period);
        
        
        Timer timer1 = new Timer();
        int delay1 = 3*1000; //3秒后开始 
        int period1 = 1000;
        timer1.scheduleAtFixedRate(new RabbitmqTaskConfig(), delay1, period1);
        
        Timer timer2 = new Timer();
        int delay2 = 4*1000; //4秒后开始 
        int period2 = 1000;
        timer2.scheduleAtFixedRate(new RabbitmqWechatReturnConfig(), delay2, period2);
	}
}
