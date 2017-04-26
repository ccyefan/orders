package com.bonc.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.QueueingConsumer.Delivery;


public class WechatReturnRecv {
       
	
	public void WereturnRecv() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		System.out.println("WechatReturn start"+new Date());
		ConnectionFactory connFac = new ConnectionFactory();
		
		connFac.setHost("130.76.1.206");
        connFac.setUsername("guest");
        connFac.setPassword("bonc1q2w3e");
		
		Connection conn = connFac.newConnection();
		
		Channel channel = conn.createChannel();
		
		String exchangeName = "ex-sync-logs";
		
		channel.exchangeDeclare(exchangeName, "topic",true);
		
		
		channel.queueBind("weixin-msgs", exchangeName, "#.core.excute.#");
		
		//配置好获取消息的方式
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume("weixin-msgs",false,consumer);
		
		//循环获取消息
		while (true) {
			
			//获取消息，如果没有消息，这一步将会一直阻塞
			Delivery delivery = consumer.nextDelivery();
			
			String msgWe = new String(delivery.getBody());
			
//			String[] aa = msgWe.split(",");
//			String[] con = aa[1].split(":");
//			String content = con[1];
//			String content1 = StringEscapeUtils.unescapeJava(content);
//		    System.err.println("--:"+content1);
//			JSONObject jsonObject = JSONObject.fromObject(msgWe);
//			System.out.println(jsonObject.getString("content"));
			
			String routingKey = delivery.getEnvelope().getRoutingKey();
			System.out.println(" [x] Received routingKey = " + routingKey + ",msg = " + msgWe + ".");
            
//			String wxurl = "http://192.168.6.117:8040/wechat/core/excute.do?token=6666666";
//			String message  = WchatUtils.httpStrRequest(wxurl, "POST",jsonObject.getString("content"));
//			System.err.println("--:"+message);
			
			
			
			System.out.println("[X] Done");
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);//返回接收到消息的确认信息
		}
	}
	

	
}
