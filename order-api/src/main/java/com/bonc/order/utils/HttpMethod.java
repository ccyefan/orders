package com.bonc.order.utils;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.bonc.order.inf.CbssLlbRequestParam;
import com.bonc.order.inf.CbssLlbResponseParam;
import com.bonc.order.inf.RequestUser;
import com.google.gson.Gson;
public class HttpMethod {
	static Logger logger = Logger.getLogger(HttpMethod.class.getName());
	/**
	 * 三户资料查询
	 * @param param
	 * @param host
	 * @return
	 */
	private static boolean  passe;  //是否通过验证
	public static boolean sanhuCheck(RequestUser param,String host){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000).setConnectionRequestTimeout(2000)
				.setSocketTimeout(10000).build();
		httpPost.setConfig(requestConfig);

		//ResponseUser reParam = null;
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
			if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  //请求成功
				HttpEntity entity2 = response2.getEntity();
				if (entity2 != null) {
					String retJson = EntityUtils.toString(entity2, "utf-8");
					System.out.println("200响应消息："+retJson);
					JSONObject jsonObject = JSONObject.fromObject(retJson);
					String msgFlag=(String) jsonObject.get("msgFlag");
					if("0".equals(msgFlag)){
						System.out.println("三户查询验证成功！");
						passe=true;
					}else{
						System.out.println("三户查询验证失败！,业务异常");
						passe=false;
					}
				}else{
					passe=false;
				}
			}else{ //请求失败 业务异常。
				System.out.println("业务异常，接口调用返回码非200");
				passe=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("接口调用异常，可能是地址配置错误，或网络没有打通。");
			passe=false;
		} finally {
			if (response2 != null) {
				try {
					response2.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return passe;
	}
	
	/**
	 * 流量办理
	 * @param param
	 * @param host
	 * @return
	 */
	public static CbssLlbResponseParam order(CbssLlbRequestParam param,String host) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000).setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000).build();
		httpPost.setConfig(requestConfig);

		CbssLlbResponseParam reParam = null;
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
					System.out.println("1111111111111111111111111");
					System.out.println(retJson);
					System.out.println("1111111111111111111111111");
					logger.info(retJson);
					reParam = (CbssLlbResponseParam) gson.fromJson(retJson,
							CbssLlbResponseParam.class);
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
		} catch (ConnectTimeoutException e3){
			reParam = new CbssLlbResponseParam();
			reParam.setErrmsg("网络超时，请稍后重试！");
			reParam.setMsgFlag("1");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("4G订购接口调用失败，异常信息:" + e);
			reParam = new CbssLlbResponseParam();
			reParam.setErrmsg("4G订购接口调用失败，异常信息:" + e.toString());
			reParam.setMsgFlag("1");
		} finally {
			if (response2 != null) {
				try {
					response2.close();
				} catch (IOException e) {
					logger.error("4G订购接口调用失败，response关闭异常，异常信息:" + e);
				}
			}
		}
		return reParam;
	}

}
