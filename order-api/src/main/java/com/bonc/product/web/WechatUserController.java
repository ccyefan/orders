package com.bonc.product.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bonc.product.service.WechatUserService;

@RestController
@Configuration
public class WechatUserController {
	
	@Autowired
	private WechatUserService wechatUserService;
	@ResponseBody
	@RequestMapping(value="/createWechatUser2" , method=RequestMethod.GET)
	public Map<String , String> insertWecharUser2(String tel,String publicid,String openId,String captcha,HttpServletRequest request){
		return wechatUserService.saveWechatUser2( request,publicid,openId, tel, captcha);
	}
	@ResponseBody
	@RequestMapping(value="/createWechatUser" , method=RequestMethod.GET)
	public Map<String , String> insertWecharUser(String tel,String publicid,String openId,String captcha,HttpServletRequest request){
		return wechatUserService.saveWechatUser( request,publicid,openId, tel, captcha);
	}
}
