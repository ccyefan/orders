package com.bonc.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermy.domain.BaseObj;

@Entity
@Table(name = "t_activity_detail")
public class ActivityDetail extends BaseObj{
    
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	/**
	 * 活动分档
	 */
	@Column(length = 20)
	private String activityStep;
	
	/**
	 * 套餐资费
	 */
	private Integer mealPrices;
    
	/**
	 * 合约期限
	 */
	private Integer contractPeriod;

	public ActivityDetail(){
		
	}
	
	
	public ActivityDetail(String activityStep,
			Integer mealPrices, Integer contractPeriod, boolean enabled) {
		this.activityStep = activityStep;
		this.mealPrices = mealPrices;
		this.contractPeriod = contractPeriod;
		super.setEnabled(enabled);
	}

	
	public Activity getActivity() {
		return activity;
	}


	public void setActivity(Activity activity) {
		this.activity = activity;
	}


	public String getActivityStep() {
		return activityStep;
	}

	public void setActivityStep(String activityStep) {
		this.activityStep = activityStep;
	}

	public Integer getMealPrices() {
		return mealPrices;
	}

	public void setMealPrices(Integer mealPrices) {
		this.mealPrices = mealPrices;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	
}
