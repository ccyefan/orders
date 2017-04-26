package com.bonc.lottery.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.supermy.domain.BaseObj;
@Entity
@Table(name = "l_join_user")
public class JoinUser extends BaseObj{
	
	@Column(length = 20)
    private String telNumber;//	用户手机号 
	
	@Column(length = 1)
    private String time;//参与次数
	
	@Column(length = 20)
	private String period;//期数
	
	
	public JoinUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

}
