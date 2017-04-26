package com.bonc.order.inf;

import java.io.Serializable;

public class WUsers implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 用户的微信标识，对当前公众号唯一 */
	private String openId;
	/** 用户的昵称 */
	private String nickName;
	/** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。 */
	private String unionId;
	/** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。1:已关注 */
	private String follow;
	/** 0：否1：是 */
	private String isBind;
	/** 手机号 */
	private String phone;
	/** 用户真实姓名 */
	private String name;
	/** 关注者角色： 1-公众客户 2-维系经理（含多级） */
	private String role;
	/** 用户所在省份 */
	private String province;
	/** 用户所在城市 */
	private String city;
	/** 公众客户业务系统唯一标识 */
	private String bizUserId;
	/** 维系经理ID */
	private String bizManagerId;

	private String followTime;

	private String bindingTime;
	/** 营业厅 */
	private String unit;
	/** 0：普通用户1：3G用户2：4G用户 */
	private String userType;
	/** 用户的语言，简体中文为zh_CN */
	private String language;
	/** 用户所在国家 */
	private String country;
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private String sex;
	/** 0：普通 1：客服在线 2：留言状态 */
	private int status;
	/** 维系/客户经理归属城市 */
	private String areaNo;
	/** 身份证号*/
	private String cardId;
	/**
	 * 微信公众号id
	 */
	private String publicId;
	
	/**
	 * 流水号（Spreader中没有，数据库中有，补加）
	 */
	private String flowId;
	
	
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public WUsers() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBizUserId() {
		return bizUserId;
	}

	public void setBizUserId(String bizUserId) {
		this.bizUserId = bizUserId;
	}

	public String getBizManagerId() {
		return bizManagerId;
	}

	public void setBizManagerId(String bizManagerId) {
		this.bizManagerId = bizManagerId;
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}

	public String getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(String bindingTime) {
		this.bindingTime = bindingTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

}
