package com.bonc.Flow;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class Order {
	static Logger logger = Logger.getLogger(Order.class.getName());
	 
   //private static String host ="http://133.160.98.26:8098/hnsap/opensap/changUserProdOrElePreSubmitw.do"
	 private static String host = "http://133.160.98.26:8098/hnsap/opensap/changeTradeSubmitw.do";

	public static Reponse order(Request param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(800).setConnectionRequestTimeout(800)
				.setSocketTimeout(8000).build();
		httpPost.setConfig(requestConfig);

		Reponse reParam = null;
		CloseableHttpResponse response2 = null;
		Gson gson = new Gson();
		try {
			String jsonString = "msg=" + gson.toJson(param);
			StringEntity entity = new StringEntity(jsonString);
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			logger.info(EntityUtils.toString(entity));
			httpPost.setEntity(entity);
			response2 = httpclient.execute(httpPost);

			logger.info(response2.getStatusLine());
			if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity2 = response2.getEntity();
				if (entity2 != null) {
					String retJson = EntityUtils.toString(entity2, "utf-8");
					logger.info(retJson);
					reParam = (Reponse) gson.fromJson(retJson,
							Reponse.class);
					if (reParam != null) {
						if ("1".equals(reParam.getMsgFlag())) {
							logger.info("订购失败！,失败原因：" + reParam.getErrmsg());
						} else if ("0".equals(reParam.getMsgFlag())) {
							logger.info("订购成功！");
						}
					}
				}
				EntityUtils.consume(entity2);
			}
		} catch (Exception e) {
			logger.error("接口调用失败，异常信息:" + e);
			reParam = new Reponse();
			reParam.setErrmsg("接口调用失败，异常信息:" + e.toString());
			reParam.setMsgFlag("1");
		} finally {
			if (response2 != null) {
				try {
					response2.close();
				} catch (IOException e) {
					logger.error("接口调用失败，response关闭异常，异常信息:" + e);
				}
			}
		}
		return reParam;
	}

}
