package com.bonc.lottery.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.supermy.domain.BaseObj;
@Entity
@Table(name = "l_award_user")
public class AwardUser extends BaseObj{
	
	@Column(length = 20)
    private String telNumber;//	用户手机号 
	
	@Column(length = 20)
	private String period;//期数
	
    private String name;//奖品名称
	@Column(length = 3)
    private String time;//参与次数
	
	public AwardUser() {
		super();
		// TODO Auto-generated constructor stub
		this.telNumber = telNumber;
		this.period = period;
		this.name = name;
		this.time = time;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
