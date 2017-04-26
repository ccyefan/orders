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
@Table(name="t_work_order")
public class Work extends BaseObj{
//	@RestResource(exported = false)
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "user_id")
	//public OUser ouser;	//用户id
	
	private int order_id;//外围系统订单ID
	private String telNu; //手机号码
	private String nettype;  //总部网别		4(4G)
	private String tenant_id;//租户id
	private String WXhsh;  //微信话术
	private String template_id; //模板id	
	private String publicid;//微信公众号
	
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
	
	private boolean msgReach;  //消息链接是否推送到位
	private String msgReachResult; //消息链接推送结果
	private Date msgReachTime; //消息推送到位时间（如果到位）
	private String msgAcceptance;  //消息链接是否受理 (有意向)
	private Date msgAcptTime;  //消息受理时间 (有意向时间)
	private String bOrderId;  //cBSS订单交易流水
	private int failCount; //办理失败次数
	private String payMode;  //付费模式
	private String cityId;  //地市编号
	
	/**
	 * 产品订购id
	 */
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_fk_id1")
	public Product product1; //产品id  8档套餐
	private String product_id1;  //
	private String element_id1;  //元素id  办理接口所需。
	private String package_id1;  //包，办理接口所需。
	private String orderproduct_id1; //推荐产品
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_fk_id2")
	public Product product2; //产品id  8档套餐
	private String product_id2; 
	private String element_id2; 
	private String package_id2; 
	private String orderproduct_id2;
	
	@RestResource(exported = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_fk_id3")
	public Product product3; //产品id  8档套餐
	private String product_id3; 
	private String element_id3; 
	private String package_id3; 
	private String orderproduct_id3;
	
	private String serviceClassCode; //三户   网别（手机号对应的）  50（4G）
		
	private String emode; //外围系统标识

	
	public Work() {
	}

	public String toString() {
		return "Work [Result=" + Result + "]";
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getTelNu() {
		return telNu;
	}

	public void setTelNu(String telNu) {
		this.telNu = telNu;
	}

	public String getNettype() {
		return nettype;
	}

	public void setNettype(String nettype) {
		this.nettype = nettype;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getWXhsh() {
		return WXhsh;
	}

	public void setWXhsh(String wXhsh) {
		WXhsh = wXhsh;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
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

	public String getMsgReachResult() {
		return msgReachResult;
	}

	public void setMsgReachResult(String msgReachResult) {
		this.msgReachResult = msgReachResult;
	}

	public boolean isMsgReach() {
		return msgReach;
	}

	public void setMsgReach(boolean msgReach) {
		this.msgReach = msgReach;
	}
	
	public String getMsgAcceptance() {
		return msgAcceptance;
	}

	public void setMsgAcceptance(String msgAcceptance) {
		this.msgAcceptance = msgAcceptance;
	}

	public Product getProduct1() {
		return product1;
	}

	public void setProduct1(Product product1) {
		this.product1 = product1;
	}

	public String getProduct_id1() {
		return product_id1;
	}

	public void setProduct_id1(String product_id1) {
		this.product_id1 = product_id1;
	}

	public String getElement_id1() {
		return element_id1;
	}

	public void setElement_id1(String element_id1) {
		this.element_id1 = element_id1;
	}

	public String getPackage_id1() {
		return package_id1;
	}

	public void setPackage_id1(String package_id1) {
		this.package_id1 = package_id1;
	}

	public String getOrderproduct_id1() {
		return orderproduct_id1;
	}

	public void setOrderproduct_id1(String orderproduct_id1) {
		this.orderproduct_id1 = orderproduct_id1;
	}

	public Product getProduct2() {
		return product2;
	}

	public void setProduct2(Product product2) {
		this.product2 = product2;
	}

	public String getProduct_id2() {
		return product_id2;
	}

	public void setProduct_id2(String product_id2) {
		this.product_id2 = product_id2;
	}

	public String getElement_id2() {
		return element_id2;
	}

	public void setElement_id2(String element_id2) {
		this.element_id2 = element_id2;
	}

	public String getPackage_id2() {
		return package_id2;
	}

	public void setPackage_id2(String package_id2) {
		this.package_id2 = package_id2;
	}

	public String getOrderproduct_id2() {
		return orderproduct_id2;
	}

	public void setOrderproduct_id2(String orderproduct_id2) {
		this.orderproduct_id2 = orderproduct_id2;
	}

	public Product getProduct3() {
		return product3;
	}

	public void setProduct3(Product product3) {
		this.product3 = product3;
	}

	public String getProduct_id3() {
		return product_id3;
	}

	public void setProduct_id3(String product_id3) {
		this.product_id3 = product_id3;
	}

	public String getElement_id3() {
		return element_id3;
	}

	public void setElement_id3(String element_id3) {
		this.element_id3 = element_id3;
	}

	public String getPackage_id3() {
		return package_id3;
	}

	public void setPackage_id3(String package_id3) {
		this.package_id3 = package_id3;
	}

	public String getOrderproduct_id3() {
		return orderproduct_id3;
	}

	public void setOrderproduct_id3(String orderproduct_id3) {
		this.orderproduct_id3 = orderproduct_id3;
	}

	public String getServiceClassCode() {
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getEmode() {
		return emode;
	}

	public void setEmode(String emode) {
		this.emode = emode;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public Date getMsgAcptTime() {
		return msgAcptTime;
	}

	public void setMsgAcptTime(Date msgAcptTime) {
		this.msgAcptTime = msgAcptTime;
	}

	public String getbOrderId() {
		return bOrderId;
	}

	public void setbOrderId(String bOrderId) {
		this.bOrderId = bOrderId;
	}

	public String getPublicid() {
		return publicid;
	}

	public void setPublicid(String publicid) {
		this.publicid = publicid;
	}

	public Date getMsgReachTime() {
		return msgReachTime;
	}

	public void setMsgReachTime(Date msgReachTime) {
		this.msgReachTime = msgReachTime;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}
