package com.bonc.rabbitmq;

import java.util.Date;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.order.domain.OUser;
import com.bonc.order.domain.Work;
import com.bonc.product.domain.Product;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/*
 * 发送消息
 */
//@RestController
public class TaskSender {
     
//	@ResponseBody
//	@RequestMapping(value = "/finl")
	 public static void main(String[] args) throws Exception{
		   
         ConnectionFactory connFac = new ConnectionFactory();
         
//         InetAddress inetAddress = InetAddress.getByName("mobile.bonc.com.cn");
//         String sd = inetAddress.getHostName();
         //RabbitMQ-Server安装在本机，所以直接用127.0.0.1   如果是远程，配置远程的IP
         connFac.setHost("130.76.1.206");
 		connFac.setVirtualHost("datasyn-henan");
 		connFac.setUsername("syn-henan");
 		connFac.setPassword("bonc1qazse4");
//         factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
//         String uri = "amqp://syn:bonc1qazse4@mobile.bonc.com.cn/";
//         connFac.setUri(uri);
//         connFac.setUri(new URI("amqp://syn:bonc1qazse4@mobile.bonc.com.cn/mq/datasyn-henan"));
//         Address[] addr = new Address[]{new Address("mobile.bonc.com.cn/mq/")};
         
         //创建一个链接
         Connection conn = connFac.newConnection();
         
         //创建一个渠道
         Channel channel = conn.createChannel();
         
         //定义exchangName,第二个参数是Exchange的类型
         String exchangeName = "syn";
         channel.exchangeDeclare(exchangeName, "topic",true);
         
         //设置不同的路由，可以分发到不同的接收端
//         String[] routing_keys = new String[]{"producttable","activitytable","tasktable"};
//         for (String routing_key : routing_keys) {
//        	 Product product = new Product();
//        	 product.setProductName("90元套餐");
//        	 product.setPrice(159);
//        	 product.setProductDetail("包含75分钟免费通话，350M流量");
//        	 product.setType("flow");
//        	 JSONObject jsonObject = JSONObject.fromObject(product);
//        	 String ms = jsonObject.toString();
//        	 channel.basicPublish(exchangeName, "producttable", null, ms.getBytes());
        	 
             Work work = new Work();
             work.setWXhsh("根据您以往订购的流量包和套餐,为您推荐10元500MB国内流量假日包(豫)");
            
             work.setTelNu("18638787085");
             
            
             long li = new Long("1043618530637800");
             work.setId(li);
			
             
             
             Product product = new Product();
             long lid = new Long("90046546");
             product.setProductOrderId(Long.toString(lid));
             
//             work.setElementid("8107971");
//             work.setPackageid("51768291");
             Date date = new Date("09/20/2016");
             work.setStartTime(date);
             Date date1 = new Date("09/22/2016");
             work.setEndTime(date1);
             
             JSONObject jsonObject1 = JSONObject.fromObject(work);
        	 String ms1 = jsonObject1.toString();
        	 
        	 String mTask = "{wxhsh:根据您以往订购的流量包和套餐,为您推荐10元500MB国内流量假日包(豫),telNumber:13261352674,id:1043618530637800,productid:90046546,elementid:8107971,packageid:51768291,startTime:2016-09-10,endTime:2016-10-11}";
        	 
        		 
        		 channel.basicPublish(exchangeName, "tasktable", null, ms1.getBytes());
			
             
//        	 ActivityDetail activityDetail = new ActivityDetail();
//        	 activityDetail.setMealPrices(24);
//        	 activityDetail.setActivityStep("120元存费送费");
//        	 JSONObject jsonObject = JSONObject.fromObject(activityDetail);
//        	 String ms = jsonObject.toString();
//        	 channel.basicPublish(exchangeName, "activitytable", null, ms.getBytes());
        	 
        	 System.out.println("seng message["+ms1.getBytes()+"] to exchange "+exchangeName +" success!");
//         }
         
         channel.close();
         conn.close();
	}
}
