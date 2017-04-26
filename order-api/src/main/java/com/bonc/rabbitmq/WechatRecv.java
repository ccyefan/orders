package com.bonc.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import com.bonc.product.domain.WechatUser;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;


public class WechatRecv {

	
	
	public void wecRec() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
			System.out.println("Wechat start"+new Date());
			ConnectionFactory connFac = new ConnectionFactory();
			
			connFac.setHost("130.76.1.206");
	        connFac.setVirtualHost("data-weixin");
	        connFac.setUsername("syn-weixin");
	        connFac.setPassword("bonc1qazse4");
			
			Connection conn = connFac.newConnection();
			
			Channel channel = conn.createChannel();
			
			String exchangeName = "syn-weixin";
			
			channel.exchangeDeclare(exchangeName, "topic",true);
			
			//接收任务表
			channel.queueBind("weixin-msg", exchangeName, "weixin");
			
			//配置好获取消息的方式
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume("weixin-msg", false, consumer);//打开消息应答机制
			
			//循环获取消息
			while (true) {
				
				//获取消息，如果没有消息，这一步将会一直阻塞
				Delivery delivery = consumer.nextDelivery();
				
				String msgWe = new String(delivery.getBody());
				
				JSONObject obj = new JSONObject().fromObject(msgWe);
				WechatUser wechatUser = (WechatUser) JSONObject.toBean(obj,WechatUser.class);
				
				WechatUser wechatUser2 = new WechatUser();
				wechatUser2.setOpenId(wechatUser.getOpenId());
				wechatUser2.setTelphone(wechatUser.getTelphone());
				TestDao2 testDao2 = new TestDao2();
				testDao2.insertWechatUserValue(wechatUser2);
				
				String routingKey = delivery.getEnvelope().getRoutingKey();
				System.out.println(" [x] Received routingKey = " + routingKey + ",msg = " + msgWe + ".");
				
//				String sendUrl = "http://localhost:9006/form/findUrl?openId="+wechatUser.getOpenId()+"&tel="+wechatUser.getTelphone();
//				WchatUtils.httpStrRequest(sendUrl, "GET",null);
//				WorkEntityController wo =new WorkEntityController();
//				workEntityController.findUrl(wechatUser.getOpenId(),wechatUser.getTelphone());
				
				String sendUrl = "http://localhost:9006/form/findUrl?openId="+wechatUser.getOpenId()+"&tel="+wechatUser.getTelphone();
				WchatUtils.httpStrRequest(sendUrl, "GET",null);				
				
				System.out.println("[X] Done");
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);//返回接收到消息的确认信息
				
			}
		}

}
