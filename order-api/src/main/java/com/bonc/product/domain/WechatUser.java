package com.bonc.product.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.supermy.domain.BaseObj;

@Entity
@Table(name = "t_wechat_user")
public class WechatUser extends BaseObj{
    
	/*
	 * 账号
	 */
	private String openId;
	
	/*
	 * 电话号码
	 */
	private String telphone;
	
	public WechatUser(){
		
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
}
