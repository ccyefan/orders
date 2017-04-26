package com.bonc.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.order.domain.Work;
import com.bonc.order.repository.WorkRepository;
import com.bonc.product.domain.Product;
import com.bonc.rabbitmq.TestDao2;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/*
 * 接收消息
 */

public class TaskRecv {
	
	
	public void taskRecv() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		System.out.println("Task start"+new Date());
		ConnectionFactory connFacTask = new ConnectionFactory();
		
		connFacTask.setHost("130.76.1.206");
		connFacTask.setVirtualHost("datasyn-henan");
		connFacTask.setUsername("syn-henan");
		connFacTask.setPassword("bonc1qazse4");
        
		Connection conn = connFacTask.newConnection();
		
		Channel channelTask = conn.createChannel();
		
		String exchangeName = "syn";
		
		channelTask.exchangeDeclare(exchangeName, "topic",true);
		
		//接收任务表
		channelTask.queueBind("task", exchangeName, "tasktable");
		
		//配置好获取消息的方式
		QueueingConsumer consumerTask = new QueueingConsumer(channelTask);
		channelTask.basicConsume("task", false, consumerTask);
		
		//循环获取消息
		while (true) {
			
			//获取消息，如果没有消息，这一步将会一直阻塞
			Delivery delivery = consumerTask.nextDelivery();
			
			String msgTa = new String(delivery.getBody());
			
			
			JSONObject obj = new JSONObject().fromObject(msgTa);
			Work work = (Work) JSONObject.toBean(obj,Work.class);
			
			Work work2 = new Work();
			work2.setWXhsh(work.getWXhsh());
			work2.setTelNu(work.getTelNu());
			
			work2.setId(work.getId());
//			work2.setProduct(work.getProduct());
//			work2.setElementid(work.getElementid());
//			work2.setPackageid(work.getPackageid());
			work2.setStartTime(work.getStartTime());
			work2.setEndTime(work.getEndTime());
			TestDao2 testDao2 = new TestDao2();
			testDao2.insertTaskValue(work2);
			
			String routingKey = delivery.getEnvelope().getRoutingKey();
			System.out.println(" [x] Received routingKey = " + routingKey + ",msg = " + msgTa + ".");
			
			System.out.println("[X] Done");
			channelTask.basicAck(delivery.getEnvelope().getDeliveryTag(), true);//返回接收到消息的确认信息
			
			
		}
	}
}
