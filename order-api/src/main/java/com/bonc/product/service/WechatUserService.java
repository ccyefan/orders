package com.bonc.product.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.order.utils.HttpUtils;
import com.bonc.product.repository.WechatUserRepository;
import com.octo.captcha.service.image.ImageCaptchaService;

@Component
@Transactional
public class WechatUserService {
	private static final String BINDING_HOST = "binding.host";
	@Autowired
	WechatUserRepository wechatuserRepository;
	@Autowired
	ImageCaptchaService imageCaptchaService;
	@Autowired
	private Environment env;
	public Map<String, String> saveWechatUser2(HttpServletRequest request,
			String publicid,String openId,String tel,String captcha){
		/*System.out.println("传入到绑定页面的userInfo："+request.getParameter("userInfo"));
		System.out.println("nopenId:"+request.getParameter("openIdChouj"));
		System.out.println("param:"+request.getParameter("param"));
		System.out.println("publicid:"+request.getParameter("publicId"));*/
		Map<String,String> map = new HashMap<String,String>();
		String jcaptchaCode = captcha.trim().toUpperCase();//获取前台的验证码输入值
//			boolean b = imageCaptchaService.validateResponseForID(request.getSession().getId(),jcaptchaCode);
		java.util.Date today=new java.util.Date();
		long t2 = today.getTime();
		if(request.getSession().getAttribute("bdsmsSendTime")==null||(String) request.getSession().getAttribute("bdcaptchaCode")==null){
			System.out.println("发送时间为空或者无后台验证码");
			map.put("message", "请发送验证码");
			return map;
		}else{
			long t1 =(Long) request.getSession().getAttribute("bdsmsSendTime");
			String captchaCode = (String) request.getSession().getAttribute("bdcaptchaCode");
			System.out.println("Session保存的验证码："+captchaCode);
			if(null == tel || tel.equals("")){
				map.put("message", "手机号不能为空");
				return map;
			}else if(null == jcaptchaCode || jcaptchaCode.equals("")){
				map.put("message", "验证码不能为空");
				return map;
			}else if(!captchaCode.equals(jcaptchaCode)){
				map.put("message", "验证码输入错误");
				return map;
			}else if((t2-t1)/1000>60){
	//			throw new BadCredentialsException("验证码超时");
				map.put("message", "验证码输入超时");
				return map;
			}else{
				String url =env.getProperty(BINDING_HOST) + "/wechat/message/user.do?json" +
						"&publicId=" +publicid+
						"&openId="+openId+
						"&mobileNum="+tel+"&type=update";
				System.out.println("url:"+url);
				String result = HttpUtils.httpStrRequest(url,"POST", null);
				System.out.println("绑定openId接口返回结果result:"+result);
				if(!"".equals(result)&&((String) JSONObject.fromObject(result).get("CODE")).equals("0")){
					map.put("message", "保存成功");
				}else{ //前台显示用户已经绑定  或者其他错误
					map.put("message", "保存失败");
				}
				return map;
			}
		}
	}
	public Map<String, String> saveWechatUser(HttpServletRequest request,
			String publicid,String openId,String tel,String captcha){
		/*System.out.println("传入到绑定页面的userInfo："+request.getParameter("userInfo"));
		System.out.println("nopenId:"+request.getParameter("openIdChouj"));
		System.out.println("param:"+request.getParameter("param"));
		System.out.println("publicid:"+request.getParameter("publicId"));*/
		Map<String,String> map = new HashMap<String,String>();
		String jcaptchaCode = captcha.trim().toUpperCase();//获取前台的验证码输入值
		try{
			boolean b = imageCaptchaService.validateResponseForID(request.getSession().getId(),jcaptchaCode);
			java.util.Date today=new java.util.Date();
			long t2 = today.getTime();
			long t1 =(Long) request.getSession().getAttribute("t1");
			if((t2-t1)/1000>60){
	//			throw new BadCredentialsException("验证码超时");
				map.put("message", "验证码输入超时");
				return map;
			}else if(!b){
				map.put("message", "验证码输入错误！");
				return map;
			}else{
				//1.
				//方法一：从session获取openid
				//String openid = (String) request.getSession().getAttribute("openidsession");
//				String openid = "ofC0CtxWoPAeGX4lOoGJkoS-W91s";
				//方法二：从前端获取openid
				//2.保存
				//一、测试：自己的数据库
				/*WechatUser wechatUser = new WechatUser();
				wechatUser.setOpenId(openId);
				wechatUser.setTelphone(tel);
				wechatuserRepository.save(wechatUser);*/
				//二、正式：微信平台
				String url =env.getProperty(BINDING_HOST) + "/wechat/message/user.do?json" +
						"&publicId=" +publicid+
						"&openId="+openId+
						"&mobileNum="+tel+"&type=update";
				System.out.println("url:"+url);
				String result = HttpUtils.httpStrRequest(url,"POST", null);
				System.out.println("绑定openId接口返回结果result:"+result);
				if(!"".equals(result)&&((String) JSONObject.fromObject(result).get("CODE")).equals("0")){
					map.put("message", "保存成功");
				}else{
					map.put("message", "保存失败");
				}
				return map;	
			}
		}catch (Exception e) {
			//e.printStackTrace();
			map.put("message", "请刷新验证码！");
			return map;	
		}
	}
}
