package com.bonc.wechat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.wechat.service.WechatService;

@RestController
public class WechatEntityController {
	@Autowired
	private WechatService wechatService;

	@ResponseBody
	@RequestMapping(value = "/iwechat", method = RequestMethod.GET)
	public Map<String, String> getInterfaceWechat(HttpServletRequest request,
			Long id, String openId, String phone, String sex, String userInfo) {
		 System.out.println(userInfo);
		/* System.out.println("~~~~~" + openId + "~~~~~"); */
		/* System.out.println("-----" + request.getQueryString() + "-----"); */
		JSONObject jsonObject = JSONObject.fromObject(userInfo);
		System.out.println(jsonObject);
		String name = jsonObject.getString("wNickname");
		String wPublicId = jsonObject.getString("wPhone");
		System.out.println("&&&&&用户的昵称" + ":" + name + "。" + "&&&&&"
				+ "*****微信公众号" + "：" + wPublicId + "。" + "*****");
		return wechatService.wechatinter(id, openId, phone, sex, userInfo);
	}
}
