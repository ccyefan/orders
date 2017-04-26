package com.bonc.order.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

//import com.bonc.wx.message.resp.Article;
//import com.bonc.wx.message.resp.Music;
//import com.bonc.wx.message.resp.Video;

/**
 * @ClassName: WchatUtils
 * @Description: 微信发送消息解析工具   + 短信
 * @author wangshuping
 * @date 2016年9月20日 上午10:42:32
 * @version V1.0  
 */
public class HttpUtils {
	/**
	 * 组装文本客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param content
	 *            文本消息内容
	 * @param kfAccount
	 *            客服账号
	 * @return
	 */
	public static String makeTextCustomMessage(String openId, String template_id,String url,String tel,String wxhsh) {
		StringBuffer jsonMsg = new StringBuffer("");
		// 对消息内容中的双引号进行转义
//		content = content.replace("\"", "\\\"");
		jsonMsg.append("{\"touser\":\"");
		jsonMsg.append(openId);
		jsonMsg.append("\",\"template_id\":\"");
		jsonMsg.append(template_id);
		jsonMsg.append("\",\"url\":\"");
		jsonMsg.append(url);
		jsonMsg.append("\",\"data\":{\"first\":{\"value\":\"尊敬的微信联通用户，您好！\",\"color\":\"#173177\"},\"tel\":{\"value\":\"");
		jsonMsg.append(tel);
		jsonMsg.append("\",\"color\":\"#173177\"},\"remark\":{\"value\":\"");
		jsonMsg.append(wxhsh);
		jsonMsg.append("\",\"color\":\"#173177\"}}}");
		
		return jsonMsg.toString();
	}
	public static String makeTextCustomMessage2(String openId, String template_id,String url,String tel,String date,String wxhsh){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", openId);
		jsonObj.put("template_id", template_id);
		jsonObj.put("url",url);
		JSONObject first = new JSONObject();
		first.put("value", "尊敬的微信联通用户，您好！");
		first.put("color", "#173177");
		JSONObject keyword1 = new JSONObject();
		keyword1.put("value", tel);
		keyword1.put("color", "#173177");
		JSONObject keyword2 = new JSONObject();
		keyword2.put("value", date);
		keyword2.put("color", "#173177");
		JSONObject remark = new JSONObject();
		remark.put("value", wxhsh);
		remark.put("color", "#173177");
		JSONObject jsonObjdata = new JSONObject();
		jsonObjdata.put("first",first);
		jsonObjdata.put("keyword1",keyword1);
		jsonObjdata.put("keyword2",keyword2);
		jsonObjdata.put("remark",remark);
		jsonObj.put("data", jsonObjdata);
		return jsonObj.toString();
	}
//	public static void main(String[] args) {
//		System.out.println(makeTextCustomMessage2("123", "3_t4XUHVsSxs4Bd1h5maXZ4IArwCZMEm-LtxNdYMdP0", "www.baidu.com", "1238788", "戴菲菲"));
		
		 String str="您的4G套餐共含流量**M,截止*月**日，您使用流量已达**M。您已连续两个月流量使用率超过70%，为节省您的流量资费建议您办理100元1G国内流量半年包，6个月内可尽情使用。点击详情链接进行订购，即时生效。";
//		 int index = ;
//		 int index2 = ;
//		 System.out.println(index+":"+index2);
//		 System.out.println(str.substring(str.indexOf("截止")+2, str.indexOf("您", 2)-1));
//	}

	/**
	 * 组装图片客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param mediaId
	 *            媒体文件id
	 * @return
	 */
	public static String makeImageCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	/**
	 * 组装语音客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param mediaId
	 *            媒体文件id
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	/**
	 * 组装视频客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param mediaId
	 *            媒体文件id
	 * @param thumbMediaId
	 *            视频消息缩略图的媒体id
	 * @return
	 */
	public static String makeVideoCustomMessage(String openId, String mediaId,String thumbMediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId, thumbMediaId);
	}
//	public static String makeVideoCustomMessage(String openId, Video video) {
//		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":%s}";
//		// 将jsonMsg中的thumbmediaid替换为thumb_media_id 、mediaId替换为media_id
//		jsonMsg = String.format(jsonMsg, openId,JSONObject.fromObject(video).toString());
//		jsonMsg = jsonMsg.replace("thumbMediaId", "thumb_media_id").replace("mediaId", "media_id");
//		return jsonMsg;
//	}

	/**
	 * 组装音乐客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param music
	 *            音乐对象
	 * @return
	 */
//	public static String makeMusicCustomMessage(String openId, Music music) {
//		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
//		jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
//		// 将jsonMsg中的thumbMediaId替换为thumb_media_id、HQMusicUrl替换为hqmusicurl、musicUrl替换为musicurl
//		jsonMsg = jsonMsg.replace("thumbMediaId", "thumb_media_id").replace("HQMusicUrl", "hqmusicurl").replace("musicUrl","musicurl");
//		return jsonMsg;
//	}

	/**
	 * 组装图文客服消息
	 *
	 * @param openId
	 *            消息发送对象
	 * @param articleList
	 *            图文消息列表
	 * @return
	 */
//	public static String makeNewsCustomMessage(String openId,List<Article> articleList) {
//		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
//		jsonMsg = String.format(jsonMsg,openId,JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
//		// 将jsonMsg中的picUrl替换为picurl
//		jsonMsg = jsonMsg.replace("picUrl", "picurl");
//		return jsonMsg;
//	}
	//------------------------HTTP-------------------------------------
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
//	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
//		JSONObject jsonObject = null;
//		try {
//			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm = { new MyX509TrustManager() };
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//			URL url = new URL(requestUrl);
//			HttpsURLConnection conn;
//			
//			// 是否启用代理 (1:启用)
//			/*if(false){
//				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80)); 
//				conn = (HttpsURLConnection) url.openConnection(proxy); 
//			}else{
//				conn = (HttpsURLConnection) url.openConnection();
//			}*/
//			conn = (HttpsURLConnection) url.openConnection();
//			conn.setSSLSocketFactory(ssf);
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			conn.setRequestMethod(requestMethod);
//
//			// 当outputStr不为null时向输出流写数据
//			if (null != outputStr) {
//				OutputStream outputStream = conn.getOutputStream();
//				// 注意编码格式
//				outputStream.write(outputStr.getBytes("UTF-8"));
//				outputStream.close();
//			}
//
//			// 从输入流读取返回内容
//			InputStream inputStream = conn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			String str = null;
//			StringBuffer buffer = new StringBuffer();
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//
//			// 释放资源
//			bufferedReader.close();
//			inputStreamReader.close();
//			inputStream.close();
//			inputStream = null;
//			conn.disconnect();
//			jsonObject = JSONObject.fromObject(buffer.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			jsonObject = null;
//		}
//		return jsonObject;
//	}
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return String
	 */
//	public static String httpsStrRequest(String requestUrl, String requestMethod, String outputStr) {
//		StringBuffer buffer = new StringBuffer();
//		try {
//			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm = { new MyX509TrustManager() };
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//			
//			URL url = new URL(requestUrl);
//			HttpsURLConnection conn;
//			
//			// 是否启用代理 (1:启用)
//			/*if(false){
//				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80)); 
//				conn = (HttpsURLConnection) url.openConnection(proxy); 
//			}else{
//				conn = (HttpsURLConnection) url.openConnection();
//			}*/
//			conn = (HttpsURLConnection) url.openConnection();
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			conn.setRequestMethod(requestMethod);
//
//			// 当outputStr不为null时向输出流写数据
//			if (null != outputStr) {
//				OutputStream outputStream = conn.getOutputStream();
//				// 注意编码格式
//				outputStream.write(outputStr.getBytes("UTF-8"));
//				outputStream.close();
//			}
//
//			// 从输入流读取返回内容
//			InputStream inputStream = conn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//			// 释放资源
//			bufferedReader.close();
//			inputStreamReader.close();
//			inputStream.close();
//			inputStream = null;
//			conn.disconnect();
//			buffer.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			buffer = null;
//		}
//		return buffer.toString();
//	}
	/**
	 * 发送http请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			
			URL url = new URL(requestUrl);
			HttpURLConnection conn;
			
			// 是否启用代理 (1:启用)
			/*if(false){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80)); 
				conn = (HttpURLConnection) url.openConnection(proxy); 
			}else{
				conn = (HttpURLConnection) url.openConnection();
			}*/
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestProperty("Content-Type", "text/plain:charset=utf-8");
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			System.out.println("buffer.toString()===="+buffer.toString());
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			jsonObject = null;
		}
		return jsonObject;
	}
	/**
	 * 发送http请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return String 
	 */
	public static String httpStrRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn;
			
			// 是否启用代理 
			//if(false){
			//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80));  
			//	conn = (HttpURLConnection) url.openConnection(proxy); 
			//}else{
				conn = (HttpURLConnection) url.openConnection();
			//}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestProperty("Content-Type", "text/plain:charset=utf-8");
			conn.setRequestMethod(requestMethod);
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			buffer.toString();
		} catch (Exception e) {
			e.getMessage();
			//buffer = null;
		}
		return buffer.toString();
	}
	public static String httpStrRequestSmsPost(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {	
			URL url = new URL(requestUrl);
			HttpURLConnection conn;
			
			// 是否启用代理 
			//if(false){
			//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80));  
			//	conn = (HttpURLConnection) url.openConnection(proxy); 
			//}else{
				conn = (HttpURLConnection) url.openConnection();
			//}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod(requestMethod);
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			System.out.println("code:"+conn.getResponseCode());
			System.out.println("responseMsg:"+conn.getResponseMessage());
			//System.out.println("getRequestProperties:"+conn.getRequestProperties());
			System.out.println("timeout:"+conn.getReadTimeout());
			System.out.println("result:"+inputStream);
			
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			buffer.toString();
		} catch (Exception e) {
			e.getMessage();
			//buffer = null;
		}
		return buffer.toString();
	}
	/**
	 * 微信消息接口  参数组装
	 * @param smsSetId
	 * @param telPhone
	 * @param sendContent
	 * @return
	 */
	public static String makeSmsTextJsonParam(String smsSetId, String telPhone,String sendContent) {
		StringBuffer jsonMsg = new StringBuffer("");
//		// 对消息内容中的双引号进行转义
//		content = content.replace("\"", "\\\"");
		jsonMsg.append("{\"smsSetId\":\"");
		jsonMsg.append(smsSetId);
		jsonMsg.append("\",\"telPhone\":\"");
		jsonMsg.append(telPhone);
		jsonMsg.append("\",\"sendContent\":\"");
		jsonMsg.append(sendContent);
		jsonMsg.append("\",\"sendLev\":\"4\"}");
		return jsonMsg.toString();
	}
}
