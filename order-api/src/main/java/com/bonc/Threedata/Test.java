package com.bonc.Threedata;

public class Test {
	public static void main(String[] args) {
		RequestUser llbRequest = new RequestUser();
		llbRequest.setOperatorId("miaoyu11");
		llbRequest.setCity("760");
		llbRequest.setDistrict("766480");
		llbRequest.setChannelId("76a3160");
		llbRequest.setChannelType("1010300");
		llbRequest.setServiceClassCode("50");
		llbRequest.setSerialNumber("18638787085");
		Select.order(llbRequest);

	}
}
