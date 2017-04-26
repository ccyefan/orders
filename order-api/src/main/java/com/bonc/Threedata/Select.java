package com.bonc.Threedata;

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
import java.io.IOException;

public class Select {

	static Logger logger = Logger.getLogger(Select.class.getName());

	private static String host = "http://133.160.98.26:8098/hnsap/opensap/checkAllUserInfoByTradeW.do";

	public static ResponseUser order(RequestUser param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000).setConnectionRequestTimeout(2000)
				.setSocketTimeout(10000).build();
		httpPost.setConfig(requestConfig);

		ResponseUser reParam = null;
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
					System.out.println(retJson);
					logger.info(retJson);
					reParam = (ResponseUser) gson.fromJson(retJson,
							ResponseUser.class);
				}
				EntityUtils.consume(entity2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response2 != null) {
				try {
					response2.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return reParam;
	}

}
