package com.bonc.rabbitmq;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bonc.rabbitmq.WechatRecv;

//@Component
public class RabbitmqWechatConfig implements CommandLineRunner{

	@Override
	public void run(String... arg0) throws Exception {
		WechatRecv wechatRecv = new WechatRecv();
		wechatRecv.wecRec();
		
	}

}
