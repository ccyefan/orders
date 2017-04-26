// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   MD5.java

package com.bonc.order.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{

    public MD5()
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
        System.out.print(MD5Encode("112" + "1364097"));
    }
}
