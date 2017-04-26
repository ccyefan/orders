package com.bonc.result.domain;

public class Flow {
	private String msgFlag;
	private String apptxSap;
	private ResultJson resultJson;

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
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

}
