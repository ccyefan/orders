package com.bonc.order.domain;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supermy.domain.BaseObj;

@Entity
@Table(name = "t_order")
public class Order extends BaseObj{
//	@RestResource(exported = false)
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "user_id")
//	public OUser ouser;	//用户id
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	public Product product; //产品id  8档套餐
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_detail_id")
	public ActivityDetail activityDetail;    //活动Id   120/240元存费送费
	
	private Integer payAmount;   //付款金额 (价格) 存费金额
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	Date date=new Date();  //订购时间
	
	private String telNum;
	
	private long workId;
	
	public Order(){
		
	}

//	public OUser getOuser() {
//		return ouser;
//	}
//
//	public void setOuser(OUser ouser) {
//		this.ouser = ouser;
//	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ActivityDetail getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(ActivityDetail activityDetail) {
		this.activityDetail = activityDetail;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
}
