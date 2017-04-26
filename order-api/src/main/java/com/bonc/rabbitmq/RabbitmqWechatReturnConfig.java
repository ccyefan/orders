package com.bonc.rabbitmq;


import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bonc.rabbitmq.WechatReturnRecv;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;


public class RabbitmqWechatReturnConfig extends TimerTask {

	@Override
	public void run(){
		WechatReturnRecv wechatReturnRecv = new WechatReturnRecv();
		try {
			wechatReturnRecv.WereturnRecv();
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
