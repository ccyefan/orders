package com.bonc.order.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermy.domain.BaseObj;

@Entity
@Table(name = "t_tmp")
public class ExcelTmp extends BaseObj{
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public OUser ouser;	//用户id
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	public Product product; //产品id  8档套餐
	
	//产品类型
	public String Type;//控制走 存费送费页面/流量包页面
	
	private String WXhsh;  //微信话术
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_detail_id")
	public ActivityDetail activityDetail;    //活动Id   120/240元存费送费
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date StartTime;  //任务开始时间
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date EndTime;  //任务结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date CompleTime;	//完成时间
	private String Result;  //完成结果
	private String Url;  //推送的url
	
	private boolean msgReach;  //消息链接是否推送到位
	private boolean msgAcceptance;  //消息链接是否受理
	public OUser getOuser() {
		return ouser;
	}
	public void setOuser(OUser ouser) {
		this.ouser = ouser;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getWXhsh() {
		return WXhsh;
	}
	public void setWXhsh(String wXhsh) {
		WXhsh = wXhsh;
	}
	public ActivityDetail getActivityDetail() {
		return activityDetail;
	}
	public void setActivityDetail(ActivityDetail activityDetail) {
		this.activityDetail = activityDetail;
	}
	public Date getStartTime() {
		return StartTime;
	}
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}
	public Date getEndTime() {
		return EndTime;
	}
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}
	public Date getCompleTime() {
		return CompleTime;
	}
	public void setCompleTime(Date compleTime) {
		CompleTime = compleTime;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public boolean isMsgReach() {
		return msgReach;
	}
	public void setMsgReach(boolean msgReach) {
		this.msgReach = msgReach;
	}
	public boolean isMsgAcceptance() {
		return msgAcceptance;
	}
	public void setMsgAcceptance(boolean msgAcceptance) {
		this.msgAcceptance = msgAcceptance;
	}

}
