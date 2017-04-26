package com.bonc.Flow;

public class Commit {
	public static void main(String[] args) {
		Request param = new Request();
		/* 操作员ID */
		param.setOperatorId("miaoyu11");
		/* 省分 */
		param.setProvince("76");
		/* 地市 */
		param.setCity("760");
		/* 区县 */
		param.setDistrict("766480");
		/* 渠道编码 */
		param.setChannelId("76a3160");
		/* 渠道编码 */
		param.setChannelType("1010300");
		/* 外围系统订单ID */
		param.setOrdersId("201609060000");
		/* ESS订单交易流水 */
		param.setProvOrderId("7616090657126669");
		Order.order(param);
	}
}
