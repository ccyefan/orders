package com.bonc.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.supermy.domain.BaseObj;

@Entity
@Table(name = "t_ouser")
@Deprecated //作废的代码标签
public class OUser extends BaseObj{
	@Column(length=20)
	private String telNumber; //手机号
	@Column(length=50)
	private String OwnerPlace; //归属地
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ouser")
//	private Set<OUser> ousers;
//	
	public OUser() {
	}
	
	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getOwnerPlace() {
		return OwnerPlace;
	}

	public void setOwnerPlace(String ownerPlace) {
		OwnerPlace = ownerPlace;
	}
}
