package com.bonc.Threedata;

public class AcctInfo {
	private String acctId;
	private String payName;
	private String payModeCode;
	private String prepayTag;
	private Consign consign;

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getPrepayTag() {
		return prepayTag;
	}

	public void setPrepayTag(String prepayTag) {
		this.prepayTag = prepayTag;
	}

	public Consign getConsign() {
		return consign;
	}

	public void setConsign(Consign consign) {
		this.consign = consign;
	}

}
