package com.bonc.order.web;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * 注入beng方式的验证码码
 * @author alec
 *
 */
@RestController
@Configuration
public class returnDemoController{
	@ResponseBody
	@RequestMapping(value = "/weixin", method = RequestMethod.POST)
	public String weixin(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		return "{\"errcode\":0,\"returnflag\":\"0\"}";
	}
	@ResponseBody
	@RequestMapping(value = "/sms1", method = RequestMethod.GET)
	public String sms1(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		return "[{\"smssetid\":[\"3037145623\"],\"returnflag\":\"0\"}]";
	}
	@ResponseBody
	@RequestMapping(value = "/sms2", method = RequestMethod.POST)
	public String sms2(String tel,HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		return "[{\"returnflag\":\"0\"}]";
//		byte[] bytes = "hello".getBytes();
        //会引发数组下标越界异常
//        System.out.println(bytes[bytes.length]);
//        return bytes[bytes.length];
		
//		String url = "http://127.0.0.1:8899/smsinterface/smsSet/tenantid/uni076";
//		JSONObject message  = WchatUtils.httpRequest(url, "GET", null);
		
//		JSONObject jsonObject = JSONObject.fromObject(message);
//		JSONArray json = JSONArray.fromObject(message);
//		 List<smsResponse> mgs = (List<smsResponse>)JSONArray.toCollection(json, smsResponse.class);
//		 List smssetid = (List) mgs.get(0);
//		 String smssetid0 = (String) smssetid.get(0);
		 
		 
//		String smssetid = message.getString("smssetid");
//		int num = jsonObject.getInt("num");
		
		//第二次调用
		
	}
}
