package com.bonc.result.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bonc.Threedata.RequestUser;

@RestController
public class ThreeFlowController {
    
	@ResponseBody
	@RequestMapping(value = "/threecode", method = RequestMethod.POST)
	public String Three(@RequestBody RequestUser requestUser){
		if(requestUser.getSerialNumber()!=null){
			System.out.println("SUCCESS!+tel:"+requestUser.getSerialNumber());
		}
		
		String resultMsg = "{\"resultJson\": {\"custInfo\": {\"certTypeCode\": \"08\",\"certCode\": \"7614082223048638\",\"certType\": \"护照\",\"custName\": \"张三\",\"custType\": \"0\"},\"sysType\": \"2\",\"state\": \"0\",\"userInfo\": {\"svcInfo\": [],\"serviceClassCode\": \"0050\",\"elementInfo\": [  ],\"productId\": \"99999829\",\"userState\": \"00\",\"nextProductName\": \"4G全国套餐-106元套餐\",\"nextProductId\": \"99999829\",\"subProductInfo\": [],\"brandCode\": \"4G00\",\"userId\": \"7614082224760897\",\"brand\": \"沃4G\",\"subscType\": \"1\",\"checkType\": \"2\",\"openDate\": \"20140822095932\",\"productName\": \"4G全国套餐-106元套餐\"},\"simCard\": \"8986011487602566579\"},\"msgFlag\": \"0\"}";
		
		return resultMsg;
	}
	
}
