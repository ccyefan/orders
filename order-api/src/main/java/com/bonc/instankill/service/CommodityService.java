package com.bonc.instankill.service;

import com.bonc.instankill.domain.Commodity;
import com.bonc.instankill.repository.CommodityRepository;
import com.bonc.lottery.domain.Lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
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
public class CommodityService {
	@Autowired
	private CommodityRepository commodityRepository;

	public Map genInstankill(String period) {
		// TODO Auto-generated method stub
		List<Commodity> commodityList = commodityRepository.findByPeriod(period);
		return null;
	}

	

}
