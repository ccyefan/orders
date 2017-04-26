package com.bonc.order.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class javademo {
	public static final String RPC_QUEUE_NAME = "rpc_queue";
	
	public static void main(String[] args) {
		/*int result = add(1,3);
		int result2 = 3+4;
		System.out.println("hello:"+result2);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("msg", "ok");*/
		System.out.println("MethodResult:"+sw(2));
	}
	public static int add(int a,int b){
		return a+b;
	}
	public static int sw(int a){
		int c = 0;
		switch (a) {
		case 1:
			c = 1;
			break;
		case 2:
			c = 2;
			break;
		}
		return c;
	}
	
	
	public static String codeall(String workid,int tenantId,String nettype,
			String ordersId,String telNumber,String FlowId){
		JSONObject jsonReturn = new JSONObject();
		switch (tenantId) {
		case 33:
			if("50".equals(nettype)){
				System.out.println("走4G接口");

				String 	orderNumber;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("operatorId", "ha-huaw6");
				jsonObj.put("city", "760");
				jsonObj.put("ordersId", ordersId);
				jsonObj.put("serialNumber", telNumber);
				jsonObj.put("flowId", FlowId);
				String jsonString = jsonObj.toString();
				String param_4G = "msg=" + jsonString;
				System.out.println("4G接口的参数:"+param_4G);
				
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost("http://localhost:8080/form/hjl/flow");
				httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
				RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(15000).setSocketTimeout(15000).build();
				httpPost.setConfig(requestConfig);
				CloseableHttpResponse response = null;
				String msgFlag;
				try{
					System.out.println("4G接口请求报文:"+param_4G);
					StringEntity entity = new StringEntity(jsonString);
					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json");
					System.out.println("entity"+entity);
					httpPost.setEntity(entity);
					response = httpclient.execute(httpPost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity2 = response.getEntity();
						if(entity2!=null){

							String retJson = EntityUtils.toString(entity2, "utf-8");
							System.out.println("11111111111111111111111111111");
							System.out.println(retJson);
							System.out.println("11111111111111111111111111111");
							JSONObject retJsonObj = JSONObject.fromObject(retJson);
							msgFlag = (String) retJsonObj.get("msgFlag");
							if("0".equals(msgFlag)){
								JSONObject resultJson = (JSONObject) retJsonObj.get("resultJson");
								String proOrderId = (String) resultJson.get("provOrderId");
								orderNumber = proOrderId; 
								jsonReturn.put("workid", workid);
								jsonReturn.put("msg", "success");
								jsonReturn.put("sequenceId", orderNumber);
							}else{
								jsonReturn.put("workid",workid);
								jsonReturn.put("msg", "fail");
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					if (response != null) {
						try {
							response.close();
						} catch (IOException e) {
							System.out.println("4G订购接口调用失败，response关闭异常，异常信息:" + e);
						}
					}
				}
			}else{
				System.out.println("走3G接口");
				String hosts = "http://localhost:8080"+"/3g/bss/simpleProChange" +
						"/businessNumber/" + telNumber+
						"/key/" +MD5.MD5Encode("112"+telNumber)+
						"/agentPassword/0123456789/mark/O" +
						"/serviceId/" +FlowId+
						"/takeEffectType/NOW/delayEffectDay/0/bewel/11/remark/22";
				System.out.println("2G/3G流量包订购地址"+hosts);
		        CloseableHttpClient httpClient = HttpClients.createDefault();
		        try {
		            HttpGet get = new HttpGet(hosts);
		            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(150000).setConnectionRequestTimeout(150000).setSocketTimeout(150000).build();
		            get.setConfig(requestConfig);
		            System.out.println("执行get请求:...."+get.getURI());
		            CloseableHttpResponse httpResponse = null;
		            httpResponse = httpClient.execute(get);
		            try{
		                HttpEntity entity = httpResponse.getEntity();
		                if (null != entity){
		                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
		                    String response = EntityUtils.toString(entity);
		                    System.out.println("-------------------------------------------------");
		                    System.out.println("响应内容:" + response);
		                    System.out.println("-------------------------------------------------");
		        			JSONObject json=JSONObject.fromObject(response);
		        			JSONObject contentObj = (JSONObject) json.get("content");
		        			String mark = contentObj.getString("mark");  
		        			System.out.println("mark:"+mark);
		        			if (response!=null&&"1".equals(mark)){
		        				String sequenceId = contentObj.getString("sequenceId");
		        				jsonReturn.put("workid", workid);
		        				jsonReturn.put("msg", "success");
		        				jsonReturn.put("sequenceId", sequenceId);
		        			}else{
		        				jsonReturn.put("workid", workid);
		        				jsonReturn.put("msg", "fail");
		        			}
		                }
		            }
		            finally{
		                httpResponse.close();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        finally{
		            try{
		            	httpClient.close();
		            } catch (IOException e){
		                e.printStackTrace();
		            }
		        }
			}
			System.out.println(tenantId);
			break;
		case 34:
			System.out.println(tenantId);
			break;
		}
		return jsonReturn.toString();
	}
	
	public static Map<String,String> Code4G(String ordersId,String telNumber,String FlowId){

		//全局的结果和流水号
		String 	orderNumber;
		Map<String,String> rmap = new HashMap<String,String>();
		//参数组装  jsonstring
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("operatorId", "ha-huaw6");
		jsonObj.put("city", "760");
		jsonObj.put("ordersId", ordersId);
		jsonObj.put("serialNumber", telNumber);
		jsonObj.put("flowId", FlowId);
		String param_4G = jsonObj.toString();
		System.out.println("4G拼接的参数:"+param_4G);
//		String hosts = env.getRequiredProperty(HOST)+"/4g/hnsap/opensap/orderFlowElementW.do";
//		System.out.println("4G 流量包订购地址"+hosts);
//		String hosts = env.getRequiredProperty(HOST)+"/form/hjl/flow";
//		CbssLlbEasyResponseParam llbResponse = OrderCbssLlbEasy.order(llbRequest,hosts);
		//发送请求  POST
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/form/hjl/flow");
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(15000).setSocketTimeout(15000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String msgFlag;
		try{
			String jsonString = "msg=" + param_4G;
			System.out.println("4G接口请求报文:"+jsonString);
			StringEntity entity = new StringEntity(jsonString);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			System.out.println("entity"+entity);
			httpPost.setEntity(entity);
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity2 = response.getEntity();
				if(entity2!=null){
					String retJson = EntityUtils.toString(entity2, "utf-8");
					System.out.println("11111111111111111111111111111");
					System.out.println(retJson);
					System.out.println("11111111111111111111111111111");
					JSONObject retJsonObj = JSONObject.fromObject(retJson);
					msgFlag = (String) retJsonObj.get("msgFlag");
					if("0".equals(msgFlag)){ //成功时有流水号，失败没有流水号
						JSONObject resultJson = (JSONObject) retJsonObj.get("resultJson");
						String proOrderId = (String) resultJson.get("provOrderId");//B侧流水号
						orderNumber = proOrderId; 
						rmap.put("msg", "success");
						rmap.put("orderNumber", orderNumber);
					}else{
						rmap.put("msg", "fail");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					System.out.println("4G订购接口调用失败，response关闭异常，异常信息:" + e);
				}
			}
		}
		return rmap;
	}
	public static String sendMQ(String request) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
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
		
		channel.basicPublish( "" , RPC_QUEUE_NAME , props ,  request.getBytes("UTF-8"));

		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume( responseQueue , consumer);
		String response = null;
		boolean flag = true;
		while(flag){
			
			Delivery delivery = consumer.nextDelivery();
			
			if(delivery.getProperties().getCorrelationId().equals(correlationId)){
				String result = new String(delivery.getBody());
				response = result;
				flag = false;
			}
		}
		channel.close();
		conn.close();
		return response;
	}
}