package com.bonc.result.domain;

import java.util.List;

public class Request {
	private String operatorId;// 操作员ID
	private String city;// 地市
	private String district;// 区县
	private String channelId;// 渠道编码
	private String channelType;// 渠道类型
	private String ordersId;// 外围系统订单ID
	private String emode;// 外围系统标示
	private String serialNumber;// 手机号码
	private List<ProductInfo> productInfo;// 产品
	private String serviceClassCode;
	

	public String getServiceClassCode() {
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getEmode() {
		return emode;
	}

	public void setEmode(String emode) {
		this.emode = emode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

}
