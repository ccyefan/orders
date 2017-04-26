package com.bonc.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import net.sf.json.JSONObject;

import com.bonc.product.domain.Product;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

/*
 * 接收消息
 */

public class ProductRecv {
	
	
	public void productRe() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		System.out.println("product  start"+new Date());
		ConnectionFactory connFac = new ConnectionFactory();
		
		connFac.setHost("130.76.1.206");
        connFac.setVirtualHost("datasyn-henan");
        connFac.setUsername("syn-henan");
        connFac.setPassword("bonc1qazse4");
        
		Connection conn = connFac.newConnection();
		
		Channel channel = conn.createChannel();
		
		String exchangeName = "syn";
		
		channel.exchangeDeclare(exchangeName, "topic",true);
		
		//接收t_product
		channel.queueBind("product", exchangeName, "producttable");
		
		//配置好获取消息的方式
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume("product", false, consumer);//打开消息应答机制
		
		//循环获取消息
		while (true) {
			
			
			Delivery delivery = consumer.nextDelivery();
			
			String msgPro = new String(delivery.getBody());
			
			
			//将接受到的信息转换为对象
			JSONObject obj = new JSONObject().fromObject(msgPro);
			Product product = (Product) JSONObject.toBean(obj,Product.class);
			
			Product product2 = new Product();
			
			product2.setProduct_type(product.getProduct_type());
			product2.setPrice(product.getPrice());
			product2.setProductName(product.getProductName());
			product2.setId(product.getId());
			TestDao2 testDao2 = new TestDao2();
			testDao2.insertValue(product2);
			
			String routingKey = delivery.getEnvelope().getRoutingKey();
			
			System.out.println(" [x] Received routingKey = " + routingKey + ",msg = " + msgPro + ".");
			
			System.out.println("[X] Done");
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);//返回接收到消息的确认信息
			
		}
	}
}
