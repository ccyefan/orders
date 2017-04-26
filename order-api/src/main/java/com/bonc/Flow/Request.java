package com.bonc.Flow;

import java.util.List;

public class Request {
	private String operatorId;
	private String province;
	private String city;
	private String district;
	private String channelId;
	private String channelType;
	private String ordersId;
	private String serialNumber;
	private String opeSysType;
	private String changeType;
	private List<ProductInfo> productInfo;
	private String provOrderId;

	public String getOperatorId() {
		return operatorId;
	}

	public String getProvOrderId() {
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOpeSysType() {
		return opeSysType;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

}