package com.bonc.lottery.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import scala.collection.mutable.ListMap;

import com.bonc.lottery.domain.Lottery;
import com.bonc.lottery.repository.AwardRepository;
import com.bonc.lottery.service.AwardService;
import com.bonc.lottery.service.LotteryService;
import com.bonc.order.inf.WUsers;

@RestController
public class LotteryController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private AwardService awardService;
	@ResponseBody
	@RequestMapping(value = "/gen/queue", method = RequestMethod.GET)
	public Map genQueue(String period)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************period:"+period);
		return lotteryService.genQueue(period);//生成奖品队列列表
	}
/*
 * http://127.0.0.1:9006/form/rest/redis/lottery?period=TheMayDay
 */
	@ResponseBody
	@RequestMapping(value = "/redis/lottery", method = RequestMethod.GET)
	public Map redisLottery(String period)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************period:"+period);
		return lotteryService.redisLottery(period);//同步奖品表
	}
	@ResponseBody
	@RequestMapping(value = "/redis/award", method = RequestMethod.GET)
	public Map redisAward(String period)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************period:"+period);
		return lotteryService.redisAward(period);//同步抽奖队列列表
	}
	@ResponseBody
	@RequestMapping(value = "/join/user", method = RequestMethod.GET)
	public Map joinUser(String period)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************period:"+period);
	    return lotteryService.joinUser(period);//初始化已参与用户队列列表
	}
	@ResponseBody
	@RequestMapping(value = "/award/user", method = RequestMethod.GET)
	public Map awardUser(String period)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************period:"+period);
		return lotteryService.awardUser(period);//初始化已中奖用户队列列表
	}
	@ResponseBody
	@RequestMapping(value = "/user/lottery", method = RequestMethod.GET)
	public Map userLottery(String telNumber,String period,HttpServletRequest request,HttpServletResponse response)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		//判断session
		HttpSession hs = request.getSession();
		String sessionId = hs.getId();
		System.out.println("session  id : "+sessionId);
		WUsers sessionUser =(WUsers) hs.getAttribute("usersession");
		if(sessionUser!=null){
			System.out.println("sessionUser有手机号");
//			 String url = "http://172.16.63.27:9006/form/rest/static/award.html?tel"+sessionUser.getPhone();
			//放行  直接到award.html页面,带入参数  
			telNumber =sessionUser.getPhone();
			System.out.println("********************telNumber:"+telNumber);
			System.out.println("********************period:"+period);
			return lotteryService.userLottery(telNumber,period);//传入用户数据
		}else{
			System.out.println("sessionUser为空");
			//本地测试添加openId 否则redirect接口不通，重定向需要配置到域名地址   openId=ofC0CtxWoPAeGX4lOoGJkoS-W91s  
//			String apiUrlopendId = "http://172.16.63.27:9006/form/rest/static/demo.html";
//			response.sendRedirect(apiUrlopendId);
			return lotteryService.getSession(request, response);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/getsession", method = RequestMethod.GET)
	public Map getSession(HttpServletRequest request,HttpServletResponse response)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		//System.out.println("********************period:"+period);
		return lotteryService.getSession(request, response);//获取用户数据
	}
}
/*public class RandomTest {
	public static void main(String[] args){ 
	　　Random r=new Random(); 
	for(int i=0;i<10;i++){
	　System.out.println(r.nextInt()); 
	　　}
	}
	}*/