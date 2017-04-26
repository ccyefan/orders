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
@Table(name = "l_award")
public class Award extends BaseObj{

	private String name;//奖品名
	@Column(length = 20,name="desc_")
	private String desc;//奖品描述 
	private String period;//中奖期数
	
	public Award(String name, String period,String desc) {
		super();
		this.name = name;
		this.period = period;
		this.desc = desc;
	}
	public Award() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}


	
}


