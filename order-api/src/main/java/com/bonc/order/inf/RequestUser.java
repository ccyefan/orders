package com.bonc.order.inf;



public class RequestUser {
	private String operatorId; //操作员id
	private String city; //地市
	private String district; //区县
	private String channelId; //渠道编码
	private String channelType; //渠道类型
	private String serviceClassCode; //网别
	private String serialNumber; //服务号码

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

	public String getServiceClassCode() {
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
