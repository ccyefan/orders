package com.bonc.result.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.result.domain.AcctInfo;
import com.bonc.result.domain.ActivityInfo;
import com.bonc.result.domain.ElementInfo;
import com.bonc.result.domain.Flow;
import com.bonc.result.domain.PackageElement;
import com.bonc.result.domain.ProductInfo;
import com.bonc.result.domain.Request;
import com.bonc.result.domain.Response;
import com.bonc.result.domain.ResultJson;
import com.bonc.result.domain.SvcInfo;
import com.bonc.result.domain.UserInfo;

@RestController
@RequestMapping("/hjl")
public class FlowEntityController {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/flow")
	public Flow getFlow() {
		/*List list2 = new ArrayList<Request>();
		Request req = new Request();
		req.setOperatorId("ha-huaw6");
		req.setCity("760");
		req.setDistrict("766480");
		req.setChannelId("76z0010");
		req.setChannelType("4000000");
		req.setOrdersId("201609130002");
		req.setEmode("D");
		req.setSerialNumber("11122223333");
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("90046546");
		productInfo.setOptType("00");
		productInfo.setEnableTag("1");
		List<PackageElement> packageElementList = new ArrayList<PackageElement>();
		PackageElement packageElement = new PackageElement();
		packageElement.setPackageId("51768291");
		packageElement.setElementId("8107971");
		packageElement.setElementType("D");
		packageElementList.add(packageElement);
		productInfo.setPackageElement(packageElementList);
		productInfoList.add(productInfo);
		req.setProductInfo(productInfoList);
		List list = new ArrayList<Flow>();
		Flow flow = new Flow();
		flow.setMsgFlag("0");
		flow.setApptxSap("7602016090770743");
		List<ResultJson> result = new ArrayList<ResultJson>();
		ResultJson resultJson = new ResultJson();
		resultJson.setProvOrderId("7616090657126669");
		result.add(resultJson);
		flow.setResultJson(resultJson);
		list.add(flow);
		list2.add(req);
		list.add(list2);
		return list;*/
		Flow flow = new Flow();
		flow.setMsgFlag("0");
		flow.setApptxSap("7602016090770743");
		ResultJson resultJson = new ResultJson();
		resultJson.setProvOrderId("78786sfds987");
		flow.setResultJson(resultJson);
//		flow.setMsgFlag("1");
		return flow;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user")
	public String getUser() {
		/*List list = new ArrayList<Flow>();
		Request req = new Request();
		req.setOperatorId("miaoyu11");
		req.setCity("760");
		req.setDistrict("766480");
		req.setChannelId("76a3160");
		req.setChannelType("1010300");
		req.setServiceClassCode("50");
		req.setSerialNumber("18638787085");
		list.add(req);
		List list2 = new ArrayList<Response>();
		Response response = new Response();
		Flow flow=new Flow();
		flow.setMsgFlag("0");
		UserInfo userInfo = new UserInfo();
		SvcInfo svcInfo = new SvcInfo();
		svcInfo.setMainTag("1");
		svcInfo.setStartDate("20160301000000");
		svcInfo.setServiceId("50000");
		svcInfo.setPackageId("59999793");
		svcInfo.setEndDate("20501230000000");
		userInfo.setSvcInfo(svcInfo);
		ActivityInfo activityInfo = new ActivityInfo();
		activityInfo.setActivityId("89000100");
		activityInfo.setActivityName("存费送费合约存120元送240元(12个月)");
		activityInfo.setActivityActiveTime("20140901000000");
		activityInfo.setActivityInactiveTime("20150831235959");
		userInfo.setActivityInfo(activityInfo);
		ElementInfo elementInfo = new ElementInfo();
		elementInfo.setId("5573010");
		elementInfo.setStartDate("20160301000000");
		elementInfo.setIdType("1");
		elementInfo.setPackageId("59999766");
		elementInfo.setEndDate("20501230235959");
		elementInfo.setProductId("99999829");
		userInfo.setElementInfo(elementInfo);
		userInfo.setServiceClassCode("0050");
		userInfo.setBrandCode("4G00");
		userInfo.setUserId("7614082224760897");
		userInfo.setUserState("00");
		userInfo.setOpenDate("20140822095932");
		userInfo.setProductId("99999829");
		userInfo.setProductName("4G全国套餐-106元套餐");
		userInfo.setNextProductId("99999829");
		userInfo.setNextProductName("4G全国套餐-106元套餐");
		AcctInfo acctInfo = new AcctInfo();
		acctInfo.setPayName("张三");
		acctInfo.setAcctId("7614082222198072");
		acctInfo.setPayModeCode("0");
		response.setUserInfo(userInfo);
		response.setAcctInfo(acctInfo);
		list2.add(userInfo);
		list.add(list2);
		return list;*/
		return "{\"msgFlag\":\"0\"}";
	}
}
