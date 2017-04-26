package com.bonc.order.utils;

import java.io.IOException;
import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class RPCClient {

	public static final String RPC_QUEUE_NAME = "rpc_queue";

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{

		ConnectionFactory connFac = new ConnectionFactory();
		connFac.setHost("172.16.63.252");
		connFac.setVirtualHost("datasyn");
 		connFac.setUsername("admin");
 		connFac.setPassword("admin");
 		
		Connection conn = connFac.newConnection();
		Channel channel = conn.createChannel();

		//响应QueueName ，服务端将会把要返回的信息发送到该Queue
		String responseQueue = channel.queueDeclare().getQueue();
        System.out.println(":"+responseQueue);
		String correlationId = UUID.randomUUID().toString();

		BasicProperties props = new BasicProperties.Builder().replyTo(responseQueue).correlationId(correlationId).build();
		
		String message = "is_zhoufeng3";
		
		channel.basicPublish( "" , RPC_QUEUE_NAME , props ,  message.toString().getBytes("UTF-8"));

		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume( responseQueue , consumer);
		
		boolean flag = true;
		while(flag){
			
			Delivery delivery = consumer.nextDelivery();
			
			if(delivery.getProperties().getCorrelationId().equals(correlationId)){
				String result = new String(delivery.getBody());
				System.out.println(result);
				flag = false;
			}
		}
		System.out.println(flag);
		channel.close();
		conn.close();
		
	}

}
