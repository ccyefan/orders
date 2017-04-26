/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bonc.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.Application;
import com.bonc.order.domain.Work;
import com.bonc.order.inf.RequestUser;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.utils.BssMD5Util;
import com.bonc.order.utils.HttpMethod;
import com.bonc.order.utils.HttpUtils;
import com.bonc.product.domain.Product;
import com.bonc.product.domain.WechatUser;
import com.bonc.product.repository.ProductRepository;
import com.bonc.product.repository.WechatUserRepository;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class WorkService{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
	private static final String SANHU_HOST = "sanhu.host";
	private static final String WEIXING_HOST = "weixing.host"; 
	private static final String WEIXING_URL = "weixing.url";
	private static final String TEMPLATE_ID = "template.id";
	private static final String PUBLIC_ID = "publicid";
	private static final String boncworkid = "boncworkidoid";  //url安全MD5颜值
	@Resource
	private Environment env;
	@Autowired
	private WorkRepository workRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private WechatUserRepository wechatuserRepository;
	//修改有意向msgReach字段和有意向时间msgReachTime
	public Map<String, String> upReachAndTime(Long workid , int errcode) {
		Work work = workRepository.getOne(workid);
		if(0 == errcode){
			work.setMsgReachResult("1103");  //成功  0
		}else{
			work.setMsgReachResult("1104");  //失败 
		}
		Date data = new Date();
		work.setMsgReachTime(data);
		workRepository.save(work);
		Map<String,String> map = new HashMap<String,String>();
		map.put("message", "update wechat_send_result success");
		return map;
	}
	//查寻任务工单通过workid(主键字段)
	public Map<String, String> findWorkById(String workid,String key){
		System.out.println("workid:"+workid);
		System.out.println("key:"+key);
		Map<String, String> map = new HashMap<String,String>();
		//核对加密部分
		String key2 = BssMD5Util.MD5Encode(boncworkid+workid);
		if(key2.equals(key)){
			System.out.println("key和key2相同。");
			Work work = workRepository.getOne(Long.parseLong(workid));
			//受理微信消息的回执处理：受理字段，受理时间(有意向)
			work.setMsgAcceptance("1102");
			Date date = new Date();
			work.setMsgAcptTime(date);
			workRepository.save(work);
			System.out.println("work.tel:"+work.getTelNu());
//			map.put("rel", work);
			map.put("result", work.getResult());
			map.put("tel", work.getTelNu());
			map.put("wxhsh", work.getWXhsh());
			String product1name = work.getProduct1().getProductName();
			System.out.println("product1name:"+product1name);
			map.put("product1name",product1name);
			String product1id = Long.toString(work.getProduct1().getId());
			map.put("product1id",product1id);
			map.put("number1", "1"); //产品序号（三个当中的哪一个）
			if(work.getProduct2()!=null){
				String product2name = work.getProduct2().getProductName();
				System.out.println("product2name:"+product2name);
				map.put("product2name",product2name);	
				map.put("product2id", Long.toString(work.getProduct2().getId()));
				map.put("number2","2");  //产品序号
			}
			if(work.getProduct3()!=null){
				String product3name = work.getProduct3().getProductName();
				System.out.println("product3name:"+product3name);
				map.put("product3name",product3name);				
				map.put("product3id", Long.toString(work.getProduct3().getId()));
				map.put("number3","3"); //产品序号
			}
			map.put("massage", "0");
			return map;
		}else{
			System.out.println("key和key2不同。");
			map.put("massage", "1");
			return map;
		}
	}
	//查询任务工单(工单合乎规范)
	public Map<String, Object> checkWorkMQ(long workid){
		//List<Work> works = workRepository.findByUserTelNumber(workid);
		Work work = workRepository.getOne(workid);
		Map<String,Object> map = new HashMap<String, Object>();
		
			if(checkWork(work)){
				if(work.getWXhsh()==null){
					map.put("wxhsh", "");
					map.put("keyword2", "");
				}else{
					map.put("wxhsh", work.getWXhsh());
					if(work.getWXhsh().contains("截止")&&work.getWXhsh().contains("您")){
						String keyword2 = work.getWXhsh().substring(work.getWXhsh().indexOf("截止")+2, work.getWXhsh().indexOf("您", 2)-1);
						map.put("keyword2",keyword2);
					}else{
						map.put("keyword2", "");
					}
				}
				map.put("template_id", work.getTemplate_id()==null?"":work.getTemplate_id());
				//openId
				/*
				//tel->openId  微信接口
				String weUserUrl = "http://172.16.16.26/wechat/message/user.do?json&publicId=gh_c1cbf2230f00&mobileNum="+work.getTelNu()+"&type=get";
				JSONObject userObject = HttpUtils.httpRequest(weUserUrl, "POST", null);
				if(userObject!=null&&!"".equals(userObject.get("CODE").toString())){
					JSONObject DATA = (JSONObject) userObject.get("DATA");
					String openid = ((JSONObject) DATA.get("openId")).toString();
				}
				*/
				if(work.getTelNu()==null){
					map.put("tel", "");
					map.put("opendId","");
				}else{
					map.put("tel", work.getTelNu());
					WechatUser wechatuser = wechatuserRepository.findByTelphone(work.getTelNu());
					map.put("opendId",wechatuser.getOpenId()==null?"":wechatuser.getOpenId());
				}
				Product product = productRepository.findByProductOrderId(work.getProduct_id1());
				String url = "";
				String key1 = BssMD5Util.MD5Encode(boncworkid+Long.toString(work.getPkId()));
				if("flow".equals(product.getProduct_type())){  //匹配对应的页面
					//流量
					url=env.getRequiredProperty(WEIXING_URL)+"/form/static/order-transact2.html?id="+work.getPkId()+"&"+"key="+key1;
				}else{
					//存 费送费
					//url=env.getRequiredProperty(WEIXING_URL)+"/form/static/order-transact.html?id="+workid;
				}
				map.put("url", url);
				map.put("id", workid);
				map.put("msg", "0");
			}else{
				map.put("msg", "1");
			}
			return map;
	}
	
	public Map<String, String> updateWorkTime(Long id){
		Work work=workRepository.getOne(id);
//		work.setId(id);
		Date date=new Date();
		work.setCompleTime(date);
		work.setResult("工单完成");
		workRepository.save(work);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("message","保存成功！");
		return map;
	}
	//添加一条工单，主键不是自增长
	public Map<String, String> saveOneWork(Work work) {
//		workRepository.save(work);
		//workRepository.saveOneWork(id, EndTime, StartTime, WXhsh, elementid, packageid, product_id, productOrderId, telNu);
		workRepository.saveOneWork(Long.toString((work.getId())), work.getEndTime(), work.getStartTime(), work.getWXhsh(), work.getElement_id1(), work.getPackage_id1(), work.getProduct_id1(), work.getTelNu(),false,false);
		Map<String, String> map = new HashMap<String, String>();
		map.put("message","success");
		return map;
	}
	//添加work的方法
	public Map<String, String> saveWork(Work work) {
		//这段可以不要
		//OUser OneOuser=ouserRespository.getOne(work.getOuser().getId());
		//work.setOuser(OneOuser);
		workRepository.save(work);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("message","保存成功！");
		return map;
	}
	//修改work的方法
		public Map<String, String> updataWork(Work work) {
			Work w=workRepository.getOne(work.getId());
			//w.setOuser(work.getOuser());
			w.setTelNu(work.getTelNu());
			w.setWXhsh(work.getWXhsh());
			w.setProduct1(work.getProduct1());
			w.setActivityDetail(work.getActivityDetail());
			w.setStartTime(work.getStartTime());
			w.setEndTime(work.getEndTime());
			w.setCompleTime(work.getCompleTime());
			w.setResult(work.getResult());
			workRepository.save(w);
			Map<String, String> map = new HashMap<String, String>();
			map.put("message","修改成功！");
			return map;
		}
		//整个流程   工单查询   三户查询  发送微信消息  任务工单开始处理
		public Map<String,String> WorkFlow(){
			Map<String, String> map = new HashMap<String, String>();
			List<Work> works = workRepository.findAll();
			System.out.println("workslenth:"+works.size());
//			List<Work> works = workRepository.findByUserTelNumber();
			for(int i=0;i<works.size();i++){
				Work work=works.get(i);
				if("".equals(work.getTelNu())||null == work.getTelNu()){
					continue;
				}
				//tel->openId  自己
				if(null == wechatuserRepository.findByTelphone(work.getTelNu())){//用户找不到对应的openId
					continue;
				}
				WechatUser wechatuser = wechatuserRepository.findByTelphone(work.getTelNu());
				String openid = wechatuser.getOpenId();
				/*
				//tel->openId  微信接口
				String weUserUrl = "http://172.16.16.26/wechat/message/user.do?json&publicId=gh_c1cbf2230f00&mobileNum="+work.getTelNu()+"&type=get";
				JSONObject userObject = HttpUtils.httpRequest(weUserUrl, "POST", null);
				if(userObject!=null&&!"".equals(userObject.get("CODE").toString())){
					JSONObject DATA = (JSONObject) userObject.get("DATA");
					String openid = ((JSONObject) DATA.get("openId")).toString();
				}
				*/
				if(!checkWork(work)){
					System.out.println("第"+i+"条任务单对应的活动未开始或已过期,或者已经推送过");
					map.put("message"+i, "活动未开始或已过期，或者已经推送过");
				//1.工单成功	
				}else if(!shquery(work.getTelNu(),work.getServiceClassCode())){
					map.put("message", "用户资料三户验证失败！");							
				//2.三户成功
				}else{
					weixinSend(work.getTelNu(),openid ,map, i, work); //发送微信
				}
				System.out.println("第"+i+"条任务处理完毕！");
			}  //循环结束
			return map;
	    }
		/**
		 * 合规任务校验
		 * @param work
		 * @return
		 */
		private boolean checkWork(Work work){
			Date date=new Date();
			Date startDate = work.getStartTime();
			Date endDate = work.getEndTime();
			//活动时间校验  推送过(不推)
			boolean isMsReach = work.isMsgReach();
			//合规任务校验
			boolean flag = isMsReach==false&&date.getTime()>startDate.getTime()&&date.getTime()<endDate.getTime();
			return flag;
		}
		/**
		 * 发送微信
		 * @param openId
		 * @param tel
		 * @param map
		 * @param i
		 * @param work
		 */
		private void weixinSend(String tel,String openid,
				Map<String, String> map, int i, Work work) {
			String url="";
			//String productType = productRepository.findByProductOrderId(work.getProduct_id1()).getType();
			String productType = work.getProduct1().getProduct_type();
			System.out.println(productType);
			String key1 = BssMD5Util.MD5Encode(boncworkid+Long.toString(work.getPkId()));
			if("3".equals(productType)){  //匹配对应的页面
				//流量
				url=env.getRequiredProperty(WEIXING_URL)+"/form/static/order-transact2.html?id="+work.getPkId()+"&"+"key="+key1;
			}else{
				//存费送费
				//url=env.getRequiredProperty(WEIXING_URL)+"/form/static/order-transact.html?id="+work.getPkId();
			}
			System.out.println("url:"+url);
//			String wxurl =env.getRequiredProperty(WEIXING_HOST)+"/wechat/message/send.do?json&publicId="+env.getRequiredProperty(PUBLIC_ID)+"&scope&type=temp";
			String wxurl =env.getRequiredProperty(WEIXING_HOST)+"/form/weixin?json&publicId="+env.getRequiredProperty(PUBLIC_ID)+"&scope&type=temp";
			System.out.println("wxurl:"+wxurl);
			//截取时间
			String keyword2 = work.getWXhsh().substring(work.getWXhsh().indexOf("截止")+2, work.getWXhsh().indexOf("您", 2)-1);
			String jsonStr = HttpUtils.makeTextCustomMessage2(openid, work.getTemplate_id(), url, tel,keyword2,work.getWXhsh());
			//调用微信接口
			System.out.println(jsonStr);
			String message  = HttpUtils.httpStrRequest(wxurl, "POST", jsonStr);
			System.out.println("微信接口返回信息:"+message);
			int errcode = (Integer) (JSONObject.fromObject(message)).get("errcode");
			if(!"".equals(message)&&errcode==0){
				System.out.println("第"+i+"条微信消息发送成功:"+message);
				map.put("message"+i, message);
				updateWorkStatus(work);
			}else{
				System.out.println("第"+i+"条微信消息发送失败:"+message);
				map.put("message"+i, message);
			}
		}
		/**
		 * 三户校验参数
		 * @param tel	手机号
		 * @param ServiceClassCode  网别
		 * @return 三户校验是否通过
		 * @return
		 */
		private boolean shquery(String tel,String ServiceClassCode) {
			RequestUser llbRequest = new RequestUser();
			llbRequest.setOperatorId("miaoyu11");
			llbRequest.setCity("760");
			llbRequest.setDistrict("766480");
			llbRequest.setChannelId("76a3160");
			llbRequest.setChannelType("1010300");
			llbRequest.setServiceClassCode(ServiceClassCode);
			System.out.println("ServiceClassCode:"+ServiceClassCode);
			llbRequest.setSerialNumber(tel);
			System.out.println("tel:"+tel);
			String  SANHU_URL= env.getRequiredProperty(SANHU_HOST)+"/hnsap/opensap/checkAllUserInfoByTradeW.do";
//			String  SANHU_URL= env.getRequiredProperty(SANHU_HOST)+"/form/hjl/user";
			return HttpMethod.sanhuCheck(llbRequest,SANHU_URL);
		}
		
		/**
		 * 发送完消息，更改任务的状态
		 * @param work
		 * @return
		 */
		public Work updateWorkStatus(Work work){
			Work workStatus = workRepository.getOne(work.getId());
			workStatus.setMsgReach(true);
			workStatus.setUpdateDate(new Date());
			return workRepository.save(workStatus);
		}
}
