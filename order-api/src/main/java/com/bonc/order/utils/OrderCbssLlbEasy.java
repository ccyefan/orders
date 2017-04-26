package com.bonc.order.utils;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.bonc.order.inf.CbssLlbEasyRequestParam;
import com.bonc.order.inf.CbssLlbEasyResponseParam;
import com.google.gson.Gson;

public class OrderCbssLlbEasy {
	static Logger logger = Logger.getLogger(OrderCbssLlbEasy.class.getName());

	// 测试环境
	// private static String host = "http://133.160.98.26:8098/hnsap/opensap/orderFlowElementW.do";

	// 正式环境
	//private static String host = "http://133.160.98.46:8098/hnsap/opensap/orderFlowElementW.do";

	public static CbssLlbEasyResponseParam order(CbssLlbEasyRequestParam param,String host) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(15000).setSocketTimeout(15000).build();
		httpPost.setConfig(requestConfig);

		CbssLlbEasyResponseParam reParam = null;
		CloseableHttpResponse response = null;
		Gson gson = new Gson();
		try {
//			String jsonString = "msg=" + gson.toJson(param);
			String jsonString = "msg=" + param;
			System.out.println("4G接口请求报文:"+jsonString);
			StringEntity entity = new StringEntity(jsonString);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			logger.info(EntityUtils.toString(entity));
			System.out.println("entity"+entity);
			httpPost.setEntity(entity);
			response = httpclient.execute(httpPost);

			logger.info(response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity2 = response.getEntity();
				if (entity2 != null) {
					String retJson = EntityUtils.toString(entity2, "utf-8");
					System.out.println("1111111111111111111111111");
					System.out.println(retJson);
					System.out.println("1111111111111111111111111");
					logger.info(retJson);
					reParam = (CbssLlbEasyResponseParam) gson.fromJson(retJson, CbssLlbEasyResponseParam.class);
					if (reParam != null) {
						if ("1".equals(reParam.getMsgFlag())) {
							logger.info("订购失败！,失败原因：" + reParam.getErrmsg());
						} else if ("0".equals(reParam.getMsgFlag())) {
							logger.info("订购成功！");
						} else {
							logger.info("订购失败！,cbss接口程序异常：" + reParam.getErrmsg());
						}
					}
				}
				EntityUtils.consume(entity2);
			}
		}catch (ConnectTimeoutException e3){
			reParam = new CbssLlbEasyResponseParam();
			reParam.setErrmsg("网络超时，请稍后重试！");
			reParam.setMsgFlag("1");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("4G订购接口调用失败，异常信息:" + e);
			reParam = new CbssLlbEasyResponseParam();
			reParam.setErrmsg("4G订购接口调用失败，异常信息:" + e.toString());
			reParam.setMsgFlag("1");
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("4G订购接口调用失败，response关闭异常，异常信息:" + e);
				}
			}
		}
		return reParam;
	}
	
	public static String order3G(String host) {
        CloseableHttpClient httpClient = getHttpClient();
        String response = null;
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(host);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(150000).setConnectionRequestTimeout(150000).setSocketTimeout(150000).build();
            get.setConfig(requestConfig);
            System.out.println("执行get请求:...."+get.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpClient.execute(get);
            try{
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
                    response = EntityUtils.toString(entity);
                    System.out.println("-------------------------------------------------");
                    System.out.println("响应内容:" + response);
                    System.out.println("-------------------------------------------------");
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
                closeHttpClient(httpClient);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
		return response;
	}
	private static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }
    private static void closeHttpClient(CloseableHttpClient client) throws IOException{
        if (client != null){
            client.close();
        }
    }

}
