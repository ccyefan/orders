/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/

package com.bonc.lottery.service;
import com.bonc.lottery.domain.Cmd;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
public class JoinUserService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private AwardRepository awardRepository;
	@Autowired
	private JoinUserRepository joinuserRepository;
	
	public Map redisAlreadyUser(String period) {
		// TODO Auto-generated method stub
		
				//stringRedisTemplate.opsForHash();
		
		return null;
	}
	
}
