/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bonc.order.service;

import groovy.lang.Binding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Application;
import com.bonc.Flow.PackageElement;
import com.bonc.Flow.ProductInfo;
import com.bonc.order.domain.Order;
import com.bonc.order.domain.Work;
import com.bonc.order.inf.CbssLlbRequestParam;
import com.bonc.order.inf.CbssLlbResponseParam;
import com.bonc.order.repository.OrderRepository;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.utils.HttpMethod;
import com.bonc.order.utils.javademo;
import com.bonc.product.domain.Product;
import com.bonc.product.repository.ProductRepository;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
@Configuration
public class OrderService {
	private static final String HOST = "flow.banli";
	@Resource
	private Environment env;
	@Autowired
	RabbitTemplate amqpTemplate;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WorkRepository workRepository;
	@Autowired
	private ProductRepository productRepository;
	public Map<String, String> saveOrderEasy(Long workid,String productPkId,String number){
		Map<String, String> map = new HashMap<String, String>();
		Product product = productRepository.getOne(Long.parseLong(productPkId));
		Work work = workRepository.getOne(workid);
		String FlowId="";
		switch (Integer.parseInt(number)) {
		case 1:
			FlowId = work.getOrderproduct_id1();
			break;
		case 2:
			FlowId = work.getOrderproduct_id2();
			break;
		case 3:
			FlowId = work.getOrderproduct_id3();
			break;
		}
		String ordersId = Integer.toString(work.getOrder_id());
		String telNumber = work.getTelNu(); 
		
		//河南住户id 对应的编码 (需要一个码表) 废弃方案
//		String tenantId = "33";
		String tenantId = work.getTenant_id();
		String nettype =work.getNettype();
		/**
		 * 走代码
		 */
//		Map<String,String> result = javademo.codeall(tenantId, nettype, ordersId, telNumber, FlowId);
		/**
		 * 走rabbitMQ队列
		 */
		JSONObject param = new JSONObject();
		param.put("workId",workid.toString());
		param.put("ordersId", ordersId);
		param.put("phoneNumber", telNumber);
		param.put("serviceType",nettype);
		param.put("payMode", work.getPayMode()==null?" ":work.getPayMode());
		param.put("productCode", FlowId);
		param.put("cityId", work.getCityId()==null?" ":work.getCityId());
		param.put("tenantId", tenantId);
		
		String request = param.toString();
		System.out.println(work.getPayMode());
		System.out.println(work.getCityId());
		System.out.println("sendRPCrequest:"+request);
		//
		/*String quene = null;
		if("3".equals(work.getEnvironment_flag())){
			quene = "provinces-orders3";
		}else{
			quene = "provinces-orders"; 
		}*/
		String response = null;
		try {
//			response = javademo.sendMQ(request);
			
			System.out.println(">>发送到RPC 的 workid:"+workid);
			Date start = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			System.out.println("RPC_start:"+df.format(start));
//			Object o = amqpTemplate.convertSendAndReceive("provinces-orders", request);
			Object o = amqpTemplate.convertSendAndReceive("syn","provinces-orders", request);
			if(o!=null){  //默认等待5秒，超时依然按失败处理
				Date end = new Date();
				System.out.println("RPC_end:"+df.format(end));
				Double e_s_la = ((double)(end.getTime()-start.getTime()))/1000;
				System.out.println("end_star:"+e_s_la.toString());
//			response = new String((byte[]) o,"UTF-8");
				response= o.toString();
				System.out.println("RpcServerResultMsg:"+response);
				/**
				 * 后续:处理解析json字符串,成功或者失败
				 */
				JSONObject jsonObj = JSONObject.fromObject(response);
				String msg = (String)jsonObj.get("msg");
				//System.out.println("<<RPC 返回的 workid:"+(String)jsonObj.get("workId"));
				if(response!=null&&"success".equals(msg)){
					System.out.println(work.getId()+"办理成功");
					String provOrderId = (String)jsonObj.get("sequenceId");
					System.out.println("流水号："+provOrderId);
					successMethod(product, work, provOrderId);
					map.put("message","办理成功");
					map.put("workId",Long.toString(work.getId()));
				}else{
					System.out.println(work.getId()+"办理失败");
					failMethod(work);
					map.put("message", "办理失败");
					map.put("workId",Long.toString(work.getId()));
				}
			}else{
				System.out.println("RpcServerResultMsg : 20秒超时");
				failMethod(work);
				map.put("message", "办理失败");
				map.put("workId",Long.toString(work.getId()));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String, String> testVue(Long workid,	String number) {
		Map<String, String> map = new HashMap<String, String>();
		Product product = null;
		Work work = workRepository.getOne(workid);
		String FlowId="";
		String productPkId = "";
		switch (Integer.parseInt(number)) {
		case 1:
			product = work.getProduct1();
			FlowId = work.getOrderproduct_id1();
			break;
		case 2:
			product = work.getProduct2();
			FlowId = work.getOrderproduct_id2();
			break;
		case 3:
			product = work.getProduct3();
			FlowId = work.getOrderproduct_id3();
			break;
		}
		String ordersId = Integer.toString(work.getOrder_id());
		String telNumber = work.getTelNu(); 
		
		//河南住户id 对应的编码 (需要一个码表) 废弃方案
//		int tenantId = 33;
		String tenantId = work.getTenant_id();
		String nettype =work.getNettype();
		/**
		 * 走代码
		 */
//		String result = javademo.codeall(Long.toString(workid),tenantId, nettype, ordersId, telNumber, FlowId);
		/**
		 * 走rabbitMQ队列
		 */
		JSONObject param = new JSONObject();
		param.put("workId",workid.toString());
		param.put("ordersId", ordersId);
		param.put("phoneNumber", telNumber);
		param.put("serviceType",nettype);
		param.put("payMode", work.getPayMode()==null?" ":work.getPayMode());
		param.put("productCode", FlowId);
		param.put("cityId", work.getCityId()==null?" ":work.getCityId());
		param.put("tenantId", tenantId);
		
		String request = param.toString();
		System.out.println(work.getPayMode());
		System.out.println(work.getCityId());
		System.out.println("sendRPCrequest:"+request);
		//
		/*String quene = null;
		if("3".equals(work.getEnvironment_flag())){
			quene = "provinces-orders3";
		}else{
			quene = "provinces-orders"; 
		}*/
		String response = null;
		try {
//			response = javademo.sendMQ(request);
			
			System.out.println(">>发送到RPC 的 workid:"+workid);
			Date start = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			System.out.println("RPC_start:"+df.format(start));
			Object o = amqpTemplate.convertSendAndReceive("syn","provinces-orders", request);
			if(o!=null){  //默认等待5秒，超时依然按失败处理
				Date end = new Date();
				System.out.println("RPC_end:"+df.format(end));
				Double e_s_la = ((double)(end.getTime()-start.getTime()))/1000;
				System.out.println("end_star:"+e_s_la.toString());
//				response = new String((byte[]) o,"UTF-8");
				response= o.toString();
				System.out.println("RpcServerResultMsg:"+response);
				JSONObject jsonObj = JSONObject.fromObject(response);
				String msg = (String)jsonObj.get("msg");
				if(response!=null&&"success".equals(msg)){
					System.out.println(work.getId()+"办理成功");
					String provOrderId = (String)jsonObj.get("sequenceId");
					System.out.println("流水号："+provOrderId);
					successMethod(product, work, provOrderId);
					map.put("message","办理成功");
					map.put("workId",Long.toString(work.getId()));
				}else{
					System.out.println(work.getId()+"办理失败");
					failMethod(work);
					map.put("message", "办理失败");
					map.put("workId",Long.toString(work.getId()));
				}
			}else{
				System.out.println("RpcServerResultMsg : 20秒超时");
				failMethod(work);
				map.put("message", "办理失败");
				map.put("workId",Long.toString(work.getId()));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
/*		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "办理失败");
		map.put("message","办理成功");
		return map;*/
	}
	public void failMethod(Work work) {
		//失败三次则不可以再办理
		int failcount = work.getFailCount();
		failcount =failcount + 1;
		work.setFailCount(failcount);
		//失败时回写work的完成时间和完成结果
		Date date=new Date();
		work.setCompleTime(date);
		work.setResult("1100"); //订购失败
		//失败以后，有意向回写为null 0是初始值
		/*work.setMsgAcceptance(0); 
		work.setMsgAcptTime(null);*/
		workRepository.save(work);
	}
	public void successMethod(Product product, Work work, String provOrderId) {
		//生成订单
		Order order=new Order();
		order.setTelNum((work.getTelNu()));
		order.setProduct(product);
		order.setPayAmount(product.getPrice());
		order.setWorkId(work.getId());
		orderRepository.save(order);
		//回写work的完成时间和完成结果
		Date date=new Date();
		work.setCompleTime(date);
		work.setResult("1101");
		//成功以后，有意向回写为null 0是初始值
		/*work.setMsgAcceptance(0); 
		work.setMsgAcptTime(null);*/
		//保存B测的流水号
		work.setbOrderId(provOrderId);
		workRepository.save(work);
	}
	public Map<String, String> saveOrder(Long workid,String productPkId,String number){
		Map<String, String> map = new HashMap<String, String>();
		Work work = workRepository.getOne(workid);
		
		CbssLlbRequestParam llbRequest = new CbssLlbRequestParam();
		llbRequest.setOperatorId("ha-huaw6");/* 操作员ID */
		llbRequest.setCity("760");/* 地市 */
		llbRequest.setDistrict("766480");/* 区县 */
		llbRequest.setChannelId("76z0010");/* 渠道编码 */
		llbRequest.setChannelType("4000000");/* 渠道类型 */
		//llbRequest.setOrdersId("201609130002");/* 外围系统订单ID */
		llbRequest.setOrdersId(Integer.toString(work.getOrder_id()));
		llbRequest.setEmode("D");/* 外围系统标示，D */
//		llbRequest.setSerialNumber("***********");/* 手机号码 */
		llbRequest.setSerialNumber(work.getTelNu());
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		ProductInfo productInfo = new ProductInfo();
		Product product = productRepository.getOne(Long.parseLong(productPkId));
		String productOrderid = product.getProductOrderId();
		String productId ="";
		String elementid ="";
		String packageId =""; 
		switch (Integer.parseInt(number)) {
		case 1:
			System.out.println("数据区域1");
			if("10".equals(product.getFlow_type())){ //假日流量包  
				System.out.println("假日流量包 ");
				productId = productOrderid;
				elementid = work.getElement_id1();
				packageId = work.getPackage_id1();
			}else{ //日流量包
				System.out.println("日流量包");
				productId = work.getProduct_id1();
				elementid = productOrderid;
				packageId = work.getPackage_id1();
			}			
			break;
		case 2:
			System.out.println("数据区域2");
			if("10".equals(product.getFlow_type())){ //假日流量包
				System.out.println("假日流量包 ");
				productId = productOrderid;
				elementid = work.getElement_id2();
				packageId = work.getPackage_id2();
			}else{ //日流量包
				System.out.println("日流量包");
				productId = work.getProduct_id2();
				elementid = productOrderid;
				packageId = work.getPackage_id2();
			}
			break;
		case 3:
			System.out.println("数据区域3");
			if("10".equals(product.getFlow_type())){ //假日流量包  
				System.out.println("假日流量包 ");
				productId = productOrderid;
				elementid = work.getElement_id3();
				packageId = work.getPackage_id3();
			}else{ //日流量包
				System.out.println("日流量包");
				productId = work.getProduct_id3();
				elementid = productOrderid;
				packageId = work.getPackage_id3();
			}
			break;
		}
		System.out.println("productId:"+productId);
		System.out.println("elementid:"+elementid);
		System.out.println("packageId:"+packageId);
		//参数一 
//		productInfo.setProductId("90046546");/* 产品ID */
		productInfo.setProductId(productId);
		productInfo.setOptType("00");/* 00：订购；01：退订 */
		productInfo.setEnableTag("1");/* 生效方式 1：立即生效 2：次月生效，默认写个1吧 */
		List<PackageElement> packageElementList = new ArrayList<PackageElement>();
		PackageElement packageElement = new PackageElement();
//		packageElement.setPackageId("51768291");/* 包编号 */
		packageElement.setPackageId(packageId);
//		packageElement.setElementId("8107971");/* 元素编号 */
		packageElement.setElementId(elementid);
		packageElement.setElementType("D");/* 元素类型 D资费,S服务,A活动，X S服务 */
		packageElementList.add(packageElement);
		productInfo.setPackageElement(packageElementList);
		productInfoList.add(productInfo);
		llbRequest.setProductInfo(productInfoList);
//		private static String host = "http://133.160.98.46:8098/hnsap/opensap/changFlowElementW.do";
		String hosts = env.getRequiredProperty(HOST)+"/4g/hnsap/opensap/changFlowElementW.do";
		System.out.println("4G 流量包订购地址"+hosts);
//		String hosts = env.getRequiredProperty(HOST)+"/form/hjl/flow";
		//先写死
		CbssLlbResponseParam llbReponse = HttpMethod.order(llbRequest,hosts);
		if(llbReponse.getErrmsg()!=null){
			System.out.println("errmsg:"+llbReponse.getErrmsg());			
		}
		/**
		 * 测试  办理接口写死 默认成功。 MsgFlag 0成功  1失败  2程序异常 
		 */
/*		CbssLlbResponseParam llbReponse = new CbssLlbResponseParam();
		ResultJson resultJson = new ResultJson();
		resultJson.setProvOrderId("1323213");
		llbReponse.setResultJson(resultJson);
		llbReponse.setMsgFlag("1");*/
		if (llbReponse!=null&&"0".equals(llbReponse.getMsgFlag())) { //办理成功
			System.out.println("办理成功");
			//生成订单
			Order order=new Order();
			order.setTelNum((work.getTelNu()));
			//Product product = productRepository.findByProductOrderId(work.getProduct_id1());
			order.setProduct(product);
//			order.setActivityDetail(work.getActivityDetail());
			order.setPayAmount(product.getPrice());
			order.setWorkId(work.getId());
			orderRepository.save(order);
			//回写work的完成时间和完成结果
			Date date=new Date();
			work.setCompleTime(date);
			work.setResult("1101");
			//保存B测的流水号
			String provOrderId = llbReponse.getResultJson().getProvOrderId();
			work.setbOrderId(provOrderId);
			workRepository.save(work);
			map.put("message","办理成功");
		}else{  //办理失败
			System.out.println("办理失败");  //业务异常  网络异常  走新加的业务异常页面.
			System.out.println(llbReponse.getErrmsg());
			map.put("message", "办理失败");
			//失败三次则不可以再办理
			int failcount = work.getFailCount();
			failcount =failcount + 1;
			work.setFailCount(failcount);
			work.setResult("1100"); //订购失败
			workRepository.save(work);
		}
		return map;
	}
	//修改任务工单 通过order id
	public Map<String,String> updataOrder(Order order){
		Order o=orderRepository.getOne(order.getId());
//		o.setActivityDetail(order.getActivityDetail());
		//o.setOuser(order.getOuser());
		o.setTelNum(order.getTelNum());
		o.setProduct(order.getProduct());
		o.setPayAmount(order.getPayAmount());
		o.setDate(order.getDate());
		orderRepository.save(o);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("message","保存成功！");
		return map;
	}
	public Page<Order> getCollectionResource(Pageable pageable) {
		return orderRepository.findAll(null, pageable);
	}
	public String later(String t1,String t2) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date t1d = sf.parse(t1);
		Date t2d = sf.parse(t2);
		long mill = t1d.getTime()-t2d.getTime();
		Double later =  (double)mill/1000;
		return later.toString();
	}

}
