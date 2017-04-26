/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bonc.order.web;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.alibaba.fastjson.JSONObject;
import com.bonc.order.domain.Order;
import com.bonc.order.domain.Work;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.service.OrderService;
//import com.google.gson.JsonObject;

/**
 * 常用增删改查走Response 接口；
 * 复杂逻辑单独定义接口；
 *
 */
@RestController
class OrderEntityController {
	private static final String HOST = "flow.banli";
	@Resource
	private Environment env;
	@Autowired
	AmqpTemplate amqpTemplate;
	@Autowired
	RabbitTemplate rabbitTemplate;  
	@Autowired
    private OrderService orderService;
	@Autowired
	private WorkRepository workRepository;
	@ResponseBody
	@RequestMapping(value="/rpcapi",method=RequestMethod.POST)
	public Map<String, String> rpcApi(@RequestBody String param){
		Map<String,String> map = new  HashMap<String,String>();
		System.out.println("param:"+param);
		String response = null;
		Object o = rabbitTemplate.convertSendAndReceive("syn","channels-response", param);
		if(o!=null){  //默认等待5秒，超时依然按失败处理
			response= o.toString();
			System.out.println("RpcServerResultMsg:"+response);
			/**
			 * 后续:处理解析json字符串,成功或者失败
			 */
			if(response!=null){
				JSONObject jsonObject = JSONObject.fromObject(response);
				Set<String> keys = jsonObject.keySet();
				for(String key:keys){
					map.put(key, (String)(jsonObject.get(key)));
				}
			}else{
				System.out.println("rpc响应的字符串为空");
				map.put("msg","null");
			}
		}else{
			System.out.println("RpcServerResultMsg : 5秒超时");
			map.put("msg","5秒超时");
		}
		return map;
	}
    //新增订单，用户id 页面类型   (订单提交的办理页面使用)  验证码验证
	@ResponseBody
    @RequestMapping(value = "/sendBanli", method = RequestMethod.GET)
    public Map<String, String> sendBanli(Long workid,String type,String captcha,String productPkId,String number,HttpServletRequest request)
            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
    	Map<String, String> map = new HashMap<String, String>();
    		Work work = workRepository.getOne(workid);
    		int failCount = work.getFailCount();
        	if("flow".equals(type)){//流量的请求
    	    	String inputCode = captcha.trim().toUpperCase();//获取前台的验证码输入值  
    	    	System.out.println("前端提交的验证码："+inputCode);
    	    	java.util.Date today=new java.util.Date();
    			long t2 = today.getTime();
    			if(request.getSession().getAttribute("smsSendTime")==null||(String) request.getSession().getAttribute("captchaCode")==null){
    				System.out.println("发送时间为空或者无后台验证码");
    				map.put("message", "请发送验证码");
    				return map;
    			}else{
	    			long t1 =(Long) request.getSession().getAttribute("smsSendTime");
	//    			boolean b =  imageCaptchaService.validateResponseForID(request.getSession().getId(),  
	//    	                        jcaptchaCode);
	    			String captchaCode = (String) request.getSession().getAttribute("captchaCode");
	    			System.out.println("Session保存的验证码："+captchaCode);
	    			if(null == inputCode || inputCode.equals("")){
	    				map.put("message", "验证码不能为空");
	    				return map;
	    			}else if(!captchaCode.equals(inputCode)){
	    				map.put("message", "验证码输入错误");
	    				return map;
	    			}else if((t2-t1)/1000>60){
	    	//			throw new BadCredentialsException("验证码超时");
	    				map.put("message", "验证码输入超时");
	    				return map;
	    			}else if(failCount>=3){
	    				map.put("message", "已连续失败三次,不可再办理");
	    				return map;
	    			}else{
//	    				return orderService.saveOrderEasy(workid,productPkId,number);
	    				return orderService.testVue(workid,number);
	    				/*Map<String,String> maps = new HashMap<String,String>();
	    				maps.put("message", "ok");
	    				
//	    				Object o = amqpTemplate.convertSendAndReceive("spring-boot-exchange","spring-boot-routingKey", "test");
	    				Object o = amqpTemplate.convertSendAndReceive("spring-boot", "test");
	    				
	    				System.out.println("sendMQresult:"+(String)o);
	    				
	    				return maps;*/
	    				
	    				/*
	    				Binding binding = new Binding();
//	    				String javascode = "int result2 = 3+4;System.out.println(\"hello:\"+result2);";
	    				//变量
	    				int param1 = 3;
	    				int param2 = 2;
//	    				int add = param2+param2;
	    				
	    				String javacode2 = env.getRequiredProperty("javacode2");
						binding.setVariable("aaa",javacode2);
						binding.setVariable("param11", param1);
						binding.setVariable("param22", param2);
						
						
						
	    				Object result = OrderEntityController.getShell("test", javacode2, binding);
	    				System.out.println("sys:"+result);
	    				Map<String,String> rmap = (Map<String, String>) result;
	    				String msg = rmap.get("message");
	    				System.out.println("msg:"+msg);*/
	    			}
    			}
        	}else{
        		map.put("message", "存费送费未开发。");
        		return map;
        		//走费流量  方法
        		// return orderService.saveOrder(workid);
        	}
    }
    
    //图形验证码验证  废弃
    //新增订单，用户id 页面类型   (订单提交的办理页面使用)  
//    @ResponseBody
//    @RequestMapping(value = "/saveorder", method = RequestMethod.GET)
//    public Map<String, String> getCollectionResource(Long workid,String type,String captcha,HttpServletRequest request)
//            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
//    	System.out.println("验证码："+captcha);
//    	if("flow".equals(type)){//流量办理
//	    	String jcaptchaCode = captcha.trim().toUpperCase();//获取前台的验证码输入值  
//	    	java.util.Date today=new java.util.Date();
//			long t2 = today.getTime();
//			long t1 =(Long) request.getSession().getAttribute("t1");
//			boolean b =  imageCaptchaService.validateResponseForID(request.getSession().getId(),  
//	                        jcaptchaCode);
//			Map<String, String> map = new HashMap<String, String>();
//			if((t2-t1)/1000>60){
//	//			throw new BadCredentialsException("验证码超时");
//				map.put("message", "验证码输入超时");
//				return map;
//			}else if(null == jcaptchaCode || jcaptchaCode.equals("")){
//				map.put("message", "验证码不能为空");
//				return map;
//			}else if(!b){
//				map.put("message", "验证码输入错误！");
//				return map;
//			}else{
//				return orderService.saveOrder(workid);
//			}
//    	}else{  //非流量办理
//    		return orderService.saveOrder(workid);
//    	}
//    }
    //一条订单的全字段修改
    @ResponseBody
    @RequestMapping(value = "/updateorderone", method = RequestMethod.POST)
    public Map<String, String> updateOrder(@RequestBody Order order){
    	System.out.println("id:"+order.getId());
    	return orderService.updataOrder(order);
    }
    /**
     * 为Groovy Script增加缓存
     * 解决Binding的线程安全问题
     *
     * @return
     */
    public static Object getShell(String cacheKey, String script,Binding binding) {

        Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();


        Object scriptObject = null;
        try {


            Script shell = null;
            if (scriptCache.containsKey(cacheKey)) {
                shell = (Script) scriptCache.get(cacheKey);
            } else {
                shell = new GroovyShell().parse(script);
                scriptCache.put(cacheKey, shell);
//                shell = cache(cacheKey, script);
            }

            //shell.setBinding(binding);
            //scriptObject = (Object) shell.run();

            scriptObject = (Object) InvokerHelper.createScript(shell.getClass(), binding).run();


            // clear
            binding.getVariables().clear();
            binding = null;

            // Cache
            if (!scriptCache.containsKey(cacheKey)) {
                //shell.setBinding(null);
                scriptCache.put(cacheKey, shell);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            //System.out.println("groovy script eval error. script: " + script, t);
        }

        return scriptObject;
    }
    public int addnum(int a,int b){
    	return a+b;
    }

}
