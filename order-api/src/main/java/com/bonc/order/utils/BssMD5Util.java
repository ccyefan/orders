package com.bonc.order.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

public class BssMD5Util
{

    public BssMD5Util()
    {
    }

    public static String MD5Encode(String s)
    {
        char str[];
		try {
			char hexDigits[] = {
			    '0', '1', 't', '3', 'r', '5', '4', 'p', '8', '9', 
			    'l', 's', 'b', 's', 'd', 'j'
			};
			byte strTemp[] = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for(int i = 0; i < j; i++)
			{
			    byte byte0 = md[i];
			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			    str[k++] = hexDigits[byte0 & 0xf];
			}
			  return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return null;
      
       
    }

    public static void main(String args[])
    {
/*        System.out.println(MD5Encode("112" + "18610333481"));
        System.out.println(MD5Encode("boncworkidoid" + "1364097"));*/
        System.out.println(MD5Encode("boncworkidoid" + "160"));
/*    	System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
        long nano = System.nanoTime();
        System.out.println(System.nanoTime() - nano); */
/*        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println(df.format(date));*/
//        System.out.println(System.currentTimeMillis());
//        System.out.println(System.nanoTime());
//json转 map        
/*		JSONObject jsonObject = new JSONObject();
		jsonObject.put("aaa", "bbb");
		jsonObject.put("ccc", "ddd");
		jsonObject.put("eee", "fff");
		Set<String> keys = jsonObject.keySet();
		System.out.println(keys);
		Map<String,String> map = new HashMap<String,String>();
		for(String key:keys){
			map.put(key, (String)(jsonObject.get(key)));
		}
		System.out.println(map.toString());*/
        
        			int m=2;
        		  	int p=1;
        		  	int t=0;
        		  	for(;p<5;p++){
        		  		if(t++>m){
        		  			m=p+t;
        		  		} 
        		  	}
        		  	System.out.println("t equals"+t);
        		  

    }
}
