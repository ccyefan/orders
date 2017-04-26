/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bonc.lottery.service;

import com.bonc.lottery.domain.Award;
import com.bonc.lottery.domain.Cmd;

import com.bonc.lottery.domain.Lottery;

import com.bonc.lottery.repository.AwardRepository;
import com.bonc.lottery.repository.LotteryRepository;
import com.bonc.order.inf.WUsers;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class LotteryService {


	@Autowired
	private StringRedisTemplate stringRedisTemplate;	
	@Autowired
	private LotteryRepository lotteryRepository;	
	@Autowired
	private AwardRepository awardRepository;


	//生成奖品队列表
	public Map genQueue(String period) {
		// TODO Auto-generated method stub
		//查询奖品表
		List<Lottery> lotteryList = lotteryRepository.findByPeriod(period);
		//删除以往对应期数的奖品队列
		List<Award> awList= awardRepository.findByPeriod(period);
		awardRepository.delete(awList);
		for (Lottery d :lotteryList) {
			//循环奖品表  根据概率数量插入到队列表
			for (int i = 0; i < d.getProbability()*d.getAmount(); i++) {
				Award award =	new Award (d.getName(),d.getPeriod(),d.getDesc());
				awardRepository.save(award);
			}
		}
		//本期中奖列表总数
		List<Award> awList1= awardRepository.findByPeriod(period);
		Map result =new HashMap();
		result.put("amount", awList1.size());
		result.put("desc",period+"奖品队列数");
		return result;
	}
	//同步奖品表
	public Map redisLottery(String period) {
		// TODO Auto-generated method stub
		List<Lottery> lotteryList = lotteryRepository.findByPeriod(period);
		stringRedisTemplate.delete(Cmd.LOTTERY_LIST+period);
		for (Lottery d :lotteryList) {
			//循环奖品表  根据期数插入到队列
			stringRedisTemplate.opsForHash().put(Cmd.LOTTERY_LIST+period, d.getName(), new Integer (d.getAmount()).toString());

		}
		Map result =new HashMap();
		result.put("amount", lotteryList.size());
		result.put("desc",period+"奖品数,请查看对应的redis中的数量");
		return result;
	}

	//同步抽奖队列
	public Map redisAward(String period) {
		// TODO Auto-generated method stub
		List<Award> awList = awardRepository.findByPeriod(period);
		stringRedisTemplate.delete(Cmd.AWARD_LIST+period);
		for (Award d :awList) {
			//循环奖品队列表  根据期数插入到队列表
			stringRedisTemplate.opsForList().rightPush(Cmd.AWARD_LIST+period, d.getName());
		}
		//List<Award> awList2= awardRepository.findByPeriod(period);
		Map result =new HashMap();
		result.put("amount", awList.size());
		result.put("desc",period+"奖品队列,请查看对应的redis中的数据");
		return result;
	}
	public Map awardUser(String period) {
		// TODO Auto-generated method stub
		//初始化中奖用户队列
		stringRedisTemplate.delete(Cmd.AWARD_USER_LIST+period);
		stringRedisTemplate.delete(Cmd.AWARD_RESULT_LIST+period);
		Map result =new HashMap();
		result.put("rediskey",Cmd.AWARD_USER_LIST+period );
		result.put("desc",period+"中奖用户队列已清空!");
		return result;
	}
	public Map joinUser(String period) {
		//初始化参与用户队列
		stringRedisTemplate.delete(Cmd.JOIN_USER_LIST+period);
		Map result =new HashMap();
		result.put("rediskey",Cmd.JOIN_USER_LIST+period );
		result.put("desc",period+"已参与队列已清空!");
		return result;
	}
	public Map getSession(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		/*public Map<String, String> getsession(HttpServletRequest request,HttpServletResponse response) 
		throws IOException {*/
			Map<String,String> map = new HashMap<String, String>();
			//从session中获取openid
			HttpSession hs = request.getSession();
			String sessionId = hs.getId();
			System.out.println("session  id : "+sessionId);
//			map.put("sesionresult", "tel80798");
			//放行  直接到award.html页面,带入参数  
			WUsers sessionUser =(WUsers) hs.getAttribute("usersession");
//			WUsers sessionUser  = new WUsers();
//			sessionUser.setNickName("aa");
//			sessionUser.setPhone("123231");
			if(sessionUser!=null){
				System.out.println("sessionUser有手机号");
//				 String url = "http://172.16.63.27:9006/form/rest/static/award.html?tel"+sessionUser.getPhone();
				//放行  直接到award.html页面,带入参数  
				map.put("tel", sessionUser.getPhone());
				map.put("changeP", "no");  //不改变页面
				return map;
			}else{
				System.out.println("sessionUser为空");
				//本地测试添加openId 否则redirect接口不通，重定向需要配置到域名地址   openId=ofC0CtxWoPAeGX4lOoGJkoS-W91s  
//				String apiUrlopendId = "http://172.16.63.27:9006/form/rest/static/demo.html";
//				response.sendRedirect(apiUrlopendId);
				map.put("tel", "notel");
				return map;
			}
		//return null;
	}
	public Map userLottery(String telNumber,String period){
		// TODO Auto-generated method stub
		
	/*	List<Award> awList = awardRepository.findByPeriod(period);	
		System.out.println("********************period:"+period);
		System.out.println("********************award:"+awList.size());
		
		Integer a =(int) (Math.random()*awList.size());
		
		Integer b=(int) (Math.random()*1000);
		System.out.println("**********************a："+a);*/
		Map result = null;
		if(telNumber.equals("undefined")){
			return result;
			
		}
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		String[] random = new String[] {Cmd.AWARD_LIST+period};
		RedisScript<String> randomscript = new DefaultRedisScript<String>(Cmd.tryGetRandom, String.class);
		List<String> randomp = Arrays.asList(random);
		Object a = stringRedisTemplate.execute(randomscript, randomp, new Object[]{});
		System.out.println("***********random:"+a);
		System.out.println("***********Time:"+time);
		System.out.println("***********period:"+period);
		System.out.println("***********period:"+Cmd.LOTTERY_LIST+period);
		
		String[] param = new String[] {telNumber,Cmd.LOTTERY_LIST+period,Cmd.AWARD_LIST+period,
				Cmd.JOIN_USER_LIST+period,Cmd.AWARD_USER_LIST+period,a.toString(),Cmd.AWARD_RESULT_LIST+period,time};
		
		//String[] param = new String[] {telNumber,Cmd.LOTTERY_LIST+period,Cmd.AWARD_LIST+period,Cmd.JOIN_USER_LIST+period,Cmd.AWARD_USER_LIST+period,a.toString()};
		RedisScript<String> script = new DefaultRedisScript<String>(Cmd.tryGetOrderScript, String.class);
		
		List<String> params = Arrays.asList(param);
		Object object = stringRedisTemplate.execute(script, params, new Object[]{});
		System.out.println(object);
		result =new HashMap();
		if(object==null){
			
			result.put("lottery",object);
			
		}else{
			JSONObject jsStr = JSONObject.fromObject(object); //将字符串{“id”：1}
			result.put("lottery",jsStr);
		}
		//JSONObject jsStr = JSONObject.fromObject(object); //将字符串{“id”：1}
	    //int js = Integer.parseInt(jsStr.getString("lottery"));//获取id的值
	    //JSONArray array = JSONArray.fromObject(object);
	    //String jsonstr = array.toString();
		//logger.debug("++++++++++spring redis"+object);

		
		result.put("telNumber",telNumber );
		result.put("period",period );
		result.put("desc",period+"抽奖完成！");
		return result;
	}
	
	
	
	
	
}
