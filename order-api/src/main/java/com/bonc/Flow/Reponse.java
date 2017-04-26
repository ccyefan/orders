package com.bonc.Flow;

public class Reponse {
	/* 处理表示 */
	private String msgFlag;
	/* AOP处理结果 */
	private String resultJson;
	/* 失败原因 */
	private String errmsg;

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
