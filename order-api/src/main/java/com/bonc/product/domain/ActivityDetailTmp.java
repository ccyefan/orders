package com.bonc.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.rest.core.annotation.RestResource;
import com.supermy.domain.BaseObj;

//活动明细临时表
@Entity
@Table(name = "t_activity_detail_tmp")
public class ActivityDetailTmp extends BaseObj{
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_tmp_id")
	private ActivityTmp activityTmp;
	
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

	public ActivityDetailTmp(){
		
	}
	
	public ActivityDetailTmp(ActivityTmp activityTmp, String activityStep,
			Integer mealPrices, Integer contractPeriod) {
		super();
		this.activityTmp = activityTmp;
		this.activityStep = activityStep;
		this.mealPrices = mealPrices;
		this.contractPeriod = contractPeriod;
	}



	public ActivityTmp getActivityTmp() {
		return activityTmp;
	}

	public void setActivityTmp(ActivityTmp activityTmp) {
		this.activityTmp = activityTmp;
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
