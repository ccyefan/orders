package com.bonc.product.service;

import com.bonc.product.domain.Activity;
import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.repository.ActivityDetailRepository;
import com.bonc.product.repository.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/28.
 */


@Component
@Transactional
public class ActivityDetailService {

    @Autowired
    private ActivityDetailRepository activityDetailRepository;
    @Autowired
    private ActivityRepository activityRepository;

    public List<ActivityDetail> findActivityDetail(Long id){
        return activityDetailRepository.findActi(id);
    }
    
    public ActivityDetail updateActivity(ActivityDetail activityDetail1){
    	ActivityDetail one = activityDetailRepository.getOne(activityDetail1.getPkId());
    	one.setId(activityDetail1.getPkId());
    	one.setActivity(activityDetail1.getActivity());
    	one.setActivityStep(activityDetail1.getActivityStep());
    	one.setContractPeriod(activityDetail1.getContractPeriod());
    	one.setMealPrices(activityDetail1.getMealPrices());
    	return activityDetailRepository.save(one);
    }
    
    public ActivityDetail saveActivity(ActivityDetail activityDetail){
    	//Activity one = activityRepository.getOne(activityDetail.getActivity().getId());
    	//activityDetail.setActivity(one);
    	return activityDetailRepository.save(activityDetail);
    }
}
