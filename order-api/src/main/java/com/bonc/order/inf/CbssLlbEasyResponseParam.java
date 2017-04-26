package com.bonc.order.inf;

public class CbssLlbEasyResponseParam {

	private String msgFlag;// 处理表示，0为成功，1位失败，2程序异常
	private String errmsg;// 失败原因
	private String code;// 返回编码
	private String apptxSap;// SAP系统的标示
	private ResultJson resultJson;//
	private String detail;// 返回描述

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getApptxSap() {
		return apptxSap;
	}

	public void setApptxSap(String apptxSap) {
		this.apptxSap = apptxSap;
	}

	public ResultJson getResultJson() {
		return resultJson;
	}

	public void setResultJson(ResultJson resultJson) {
		this.resultJson = resultJson;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}