package com.bonc.rabbitmq;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.bonc.product.domain.Product;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/*
 * 发送消息
 */
//@RestController
public class ProductSender {
	private static Logger logger = LoggerFactory.getLogger(ProductSender.class);
//	@ResponseBody
//	@RequestMapping(value = "/finl")
	 public void sed(String ar) throws Exception{
		   
         ConnectionFactory connFac = new ConnectionFactory();
         
         connFac.setHost("130.76.1.206");
         connFac.setVirtualHost("datasyn-henan");
         connFac.setUsername("syn-henan");
         connFac.setPassword("bonc1qazse4");
         
         //创建一个链接
         Connection conn = connFac.newConnection();
         
         //创建一个渠道
         Channel channel = conn.createChannel();
         
         //定义exchangName,第二个参数是Exchange的类型
         String exchangeName = "syn";
         channel.exchangeDeclare(exchangeName, "topic",true);
         
        	 
            Product product = new Product();
            product.setProduct_type("flow");
            product.setPrice(10);
            product.setProductName("10元500MB国内流量假日包(豫)");
            product.setProductOrderId("1043611");
        	 
            JSONObject jsonObject1 = JSONObject.fromObject(product);
       	 	String ms2 = jsonObject1.toString();
            
        	 String mPro = "{type:flow,price:76,productName:10元500MB国内流量假日包(豫),id:1043611}";
        	 channel.basicPublish(exchangeName, "producttable", null, ms2.getBytes());
             
        	 
        	 System.out.println("seng message["+ms2.getBytes()+"] to exchange "+exchangeName +" success!");
         
         channel.close();
         conn.close();
	}
	 
//	 /**
//	     * post请求
//	     * @param url         url地址
//	     * @param jsonParam     参数
//	     * @param noNeedResponse    不需要返回结果
//	     * @return
//	     */
//	 public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
//	        //post请求返回结果
//	        DefaultHttpClient httpClient = new DefaultHttpClient();
//	        JSONObject jsonResult = null;
//	        HttpPost method = new HttpPost(url);
//	        try {
//	            if (null != jsonParam) {
//	                //解决中文乱码问题
//	                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
//	                entity.setContentEncoding("UTF-8");
//	                entity.setContentType("application/json");
//	                method.setEntity(entity);
//	            }
//	            HttpResponse result = httpClient.execute(method);
//	            url = URLDecoder.decode(url, "UTF-8");
//	            /**请求发送成功，并得到响应**/
//	            if (result.getStatusLine().getStatusCode() != 201) {
//	                String str = "";
//	                try {
//	                    /**读取服务器返回过来的json字符串数据**/
//	                    str = EntityUtils.toString(result.getEntity());
//	                    System.err.println("lll:"+str);
//	                    if (noNeedResponse) {
//	                        return null;
//	                    }
//	                    /**把json字符串转换成json对象**/
//	                    jsonResult = JSONObject.fromObject(str);
//	                } catch (Exception e) {
//	                    logger.error("post请求提交失败:" + url, e);
//	                }
//	            }
//	        } catch (IOException e) {
//	            logger.error("post请求提交失败:" + url, e);
//	        }
//	        return jsonResult;
//	    }
//	 
//	 public JSONObject ht(){
//		 JSONObject jsonResult = JSONObject.fromObject("{\"price\":\"b\"}");
//		 return httpPost("http://127.0.0.1:9006/form/product",jsonResult,false);
//	 }
//	 public static void main(String[] args) {
//		 ProductSender po = new ProductSender();
//		 po.ht();
//	 }
	 
}
