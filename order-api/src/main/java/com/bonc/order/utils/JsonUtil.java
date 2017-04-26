package com.bonc.order.utils;

import java.util.Map;

import net.sf.json.JSONObject;

public class JsonUtil {

	public static String getJsonResult(String STATE, String STATE_INFO,Object obj,Object extInfo){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", STATE);
		jsonObj.put("desc", STATE_INFO);
		jsonObj.put("data", obj);
		jsonObj.put("extInfo", obj);
		return jsonObj.toString();
	}

	public static String getJsonResult(String STATE, String STATE_INFO,Object obj){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", STATE);
		jsonObj.put("desc", STATE_INFO);
		jsonObj.put("data", obj);
		return jsonObj.toString();
	}

	public static String getJsonResult(String STATE, String STATE_INFO){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status", STATE);
		jsonObj.put("desc", STATE_INFO);
		return jsonObj.toString();
	}
	
	

}
