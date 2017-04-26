package com.bonc.order.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class RPCServer {

public static final String RPC_QUEUE_NAME = "provinces-orders";
	
	public static String sayHello(String name){
		return "hello " + name;
	}
	
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ConnectionFactory connFac = new ConnectionFactory() ;
		connFac.setHost("172.16.63.252");
//		connFac.setHost("172.16.16.80");
		connFac.setVirtualHost("datasyn");
 		connFac.setUsername("admin");
// 		connFac.setUsername("bonc");
 		connFac.setPassword("admin");
// 		connFac.setPassword("bonc1q2w3e");
		
		Connection conn = connFac.newConnection() ;
		
		Channel channel = conn.createChannel() ;
		
		channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null);
		
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		channel.basicConsume(RPC_QUEUE_NAME, false , consumer);
		
		while(true){
			System.out.println("服务端等待接收消息..");
			Delivery deliver = consumer.nextDelivery();
			System.out.println("服务端成功收到消息..");
			
			Date start = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			System.out.println("Server_receive:"+df.format(start));
			
			BasicProperties props =  deliver.getProperties() ;
			System.out.println("correlationId:"+props.getCorrelationId());
			String message = new String(deliver.getBody() , "UTF-8") ;
			System.out.println("mes:"+message);
			
			JSONObject jsonObj = JSONObject.fromObject(message);
			String workid = (String)jsonObj.getString("workId");
			System.out.println("workid:"+workid);
			String ordersId = (String) jsonObj.get("ordersId");
			String telNumber = (String) jsonObj.get("telNumber");
			String FlowId = (String) jsonObj.get("flowId");
			String tenantId = (String) jsonObj.get("tenantId");
			int tenantid = Integer.parseInt(tenantId);
			String nettype = (String) jsonObj.get("nettype");
			String jsonString = javademo.codeall(workid,tenantid, nettype, ordersId, telNumber, FlowId);
			
//			String responseMessage = sayHello(message);
			
			BasicProperties responseProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();
			System.out.println("reply:"+props.getReplyTo());
			//将结果返回到客户端Queue
			
			channel.basicPublish("syn", props.getReplyTo() , responseProps , jsonString.getBytes("UTF-8"));
		 	
			Date send_after = new Date();
			System.out.println("Server_send"+df.format(send_after));
			//向客户端确认消息
			channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
			System.out.println("服务端返回消息完成..");
		}
		
	}
}