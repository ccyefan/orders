package com.bonc.rabbitmq;

import net.sf.json.JSONObject;
import com.bonc.product.domain.WechatUser;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class WechatSender {

	public static void main(String[] args) throws Exception{
		   
        ConnectionFactory connFac = new ConnectionFactory();
        
        //RabbitMQ-Server安装在本机，所以直接用127.0.0.1   如果是远程，配置远程的IP
        connFac.setHost("130.76.1.206");
        connFac.setVirtualHost("data-weixin");
        connFac.setUsername("syn-weixin");
        connFac.setPassword("bonc1qazse4");
        
        //创建一个链接
        Connection conn = connFac.newConnection();
        
        //创建一个渠道
        Channel channel = conn.createChannel();
        
        //定义exchangName,第二个参数是Exchange的类型
        String exchangeName = "syn-weixin";
        channel.exchangeDeclare(exchangeName, "topic",true);
        
       	 WechatUser wechatUser = new WechatUser();
       	 wechatUser.setOpenId("oio3NwQOxL4_NDxsIxMVkYHLzuCQ");
       	 wechatUser.setTelphone("18638787085");
            
         JSONObject jsonObject1 = JSONObject.fromObject(wechatUser);
       	 String ms1 = jsonObject1.toString();
       	 channel.basicPublish(exchangeName, "weixin", null, ms1.getBytes());
            
       	 
       	 System.out.println("seng message["+ms1.getBytes()+"] to exchange "+exchangeName +" success!");
        
        channel.close();
        conn.close();
	}
	
}
