package com.bonc.product.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supermy.domain.BaseObj;

//活动临时表
@Entity
@Table(name = "t_activity_tmp")
public class ActivityTmp extends BaseObj{
	/**
     * 活动时间
     */
	@JsonFormat(timezone = "GMT+8")
	private Date activityTime=new Date();

	/**
	 * 活动名称
	 */
	@Column(length = 50)
	private String activityName;

	/**
	 * 开始时间
	 */
	@JsonFormat(timezone = "GMT+8")
	private Date startTime=new Date();
	
	/**
	 * 结束时间
	 */
	@JsonFormat(timezone = "GMT+8")
	private Date stopTime;
    
	/**
	 * 备注
	 */
	@Column(length = 100)
	private String remarks;

	public ActivityTmp(){
		
	}
	
	public ActivityTmp(Date activityTime, String activityName, Date startTime,
			Date stopTime, String remarks) {
		super();
		this.activityTime = activityTime;
		this.activityName = activityName;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.remarks = remarks;
	}

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
	
}
