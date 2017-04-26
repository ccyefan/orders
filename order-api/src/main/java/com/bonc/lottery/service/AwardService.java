/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bonc.lottery.service;

import com.bonc.lottery.domain.Cmd;
import com.bonc.lottery.domain.JoinUser;
import com.bonc.lottery.domain.Lottery;
import com.bonc.lottery.domain.Award;
import com.bonc.lottery.repository.JoinUserRepository;
import com.bonc.lottery.repository.LotteryRepository;
import com.bonc.lottery.repository.AwardRepository;
import com.supermy.domain.Channel;
import com.supermy.repository.ChannelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import scala.collection.mutable.ListMap;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class AwardService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private AwardRepository awardRepository;
	@Autowired
	private JoinUserRepository joinUserRepository;
	//private RedisTemplate<String, String> redisClient;
	//private List<String> params;
/*	public Map redisAward(String telNumber) {
		// TODO Auto-generated method stub
		String[] param = new String[] {telNumber,Cmd.LOTTERY_LIST, Cmd.AWARD_LIST , Cmd.JOIN_USER_LIST,Cmd.AWARD_USER_LIST};
		RedisScript<String> script = new DefaultRedisScript<String>(Cmd.tryGetOrderScript, String.class);
		List<String> params = Arrays.asList(param);
		Object object = redisClient.execute(script, params,new Object[] {});
		System.out.println(object);
        // logger.debug("++++++++++spring redis"+object);
		return null;
				
	}*/
	
/*
	@SuppressWarnings("unchecked")
	public Map redisAward(String period) {
		// TODO Auto-generated method stub
		List<Award> awList= awardRepository.findAll();//
		for (Award d :awList) {
			//循环奖品队列表  根据期数同步队列
			for (int i = 0; i < d.getPkId(); i++) {
				stringRedisTemplate.opsForList().leftPush(Cmd.AWARD_LIST+period, d.getName(), d.getDesc());
			}
		}
		return null;
	}
	*/
}
