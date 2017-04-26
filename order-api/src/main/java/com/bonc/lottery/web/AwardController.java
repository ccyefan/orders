package com.bonc.lottery.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.lottery.domain.Lottery;
import com.bonc.lottery.repository.AwardRepository;
import com.bonc.lottery.service.AwardService;
import com.bonc.lottery.service.LotteryService;

@RestController
public class AwardController {
/*	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private AwardService awardService;
	@ResponseBody
	@RequestMapping(value = "/redis/award", method = RequestMethod.POST)
	public Map awardQueue(Long pkId)
	throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
		System.out.println("********************pkId:"+pkId);
		return awardService.redisAward(pkId);//同步参与用户队列列表
	}*/
}