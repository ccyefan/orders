package com.bonc.order.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Application;
import com.bonc.order.domain.Work;
import com.bonc.order.inf.WUsers;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.service.WorkService;
import com.bonc.order.utils.HttpUtils;

/**
 * 常用增删改查走Response 接口； 复杂逻辑单独定义接口；
 */
@RestController
@Configuration
public class WorkEntityController {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
	private static final String SERVER_HOST = "server.host";
	@Autowired
	private WorkService workService;
	@Autowired
	private Environment env;
	@Autowired
	private WorkRepository workRepository;
	//修改有意向msgReach字段和有意向时间msgReachTime
	@ResponseBody
	@RequestMapping(value="/upreach",method = RequestMethod.POST)
	public Map<String,String> updateReach(@RequestBody String param){
		JSONObject jsonObj = JSONObject.fromObject(param);  
		String strworkid = (String) jsonObj.get("workid");
		Long workid = Long.parseLong(strworkid);
		Integer errcode = (Integer) jsonObj.get("errcode");
		return workService.upReachAndTime(workid,errcode);
	} 
	// 修改时间
	@ResponseBody
	@RequestMapping(value = "/updatework", method = RequestMethod.GET)
	public Map<String, String> getCollectionResource(Long id)
			throws ResourceNotFoundException,
			HttpRequestMethodNotSupportedException {
		return workService.updateWorkTime(id);
	}

	// 新增一条工单(带外键)
	@ResponseBody
	@RequestMapping(value = "/savework", method = RequestMethod.POST)
	public Map<String, String> saveWork(@RequestBody Work work) {
		return workService.saveWork(work);
	}

	// 新增一条工单(带外键)
	@ResponseBody
	@RequestMapping(value = "/saveOnework", method = RequestMethod.POST)
	public Map<String, String> saveOneWork(@RequestBody Work work) {
		return workService.saveOneWork(work);
	}

	// 修改一条工单，全字段修改
	@ResponseBody
	@RequestMapping(value = "/updateworkone", method = RequestMethod.POST)
	public Map<String, String> updateWork(@RequestBody Work work) {
		return workService.updataWork(work);
	}

	@ResponseBody
	@RequestMapping(value = "/findUrl", method = RequestMethod.GET)
	public Map<String, String> findUrl() {
		return workService.WorkFlow();
	}

	@ResponseBody
	@RequestMapping(value = "/findWorkById", method = RequestMethod.POST)
	public Map<String, Object> checkWork(@RequestBody Work work) {
		return workService.checkWorkMQ(work.getId());
	}

	@ResponseBody
	@RequestMapping(value = "/updateWorkStatu", method = RequestMethod.POST)
	public Work updateWorkStatu(@RequestBody Work work) {
		return workService.updateWorkStatus(work);
	}

	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Map<String, String> findWorkById(String workid, String key) {
		System.out.println("workid:"+workid+"key:"+key);
		return workService.findWorkById(workid, key);
	}
	@ResponseBody
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public void demo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("demo");
//		response.setHeader("Referer", "http://172.16.63.27:9006/form/static/binding.html?openId=ofC0CtxWoPAeGX4lOoGJkoS-W91s");
		response.sendRedirect("http://127.0.0.1:9006/form/static/binding.html?openId=ofC0CtxWoPAeGX4lOoGJkoS-W91s");
	}
	/*@ResponseBody
	@RequestMapping(value = "/getsession", method = RequestMethod.GET)
	public Map<String, String> getsession(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,String> map = new HashMap<String, String>();
		//从session中获取openid
		HttpSession hs = request.getSession();
		String sessionId = hs.getId();
		System.out.println("session  id : "+sessionId);
//		map.put("sesionresult", "tel80798");
		//放行  直接到chouj.html页面,带入参数  
		WUsers sessionUser =(WUsers) hs.getAttribute("usersession");
//		WUsers sessionUser  = new WUsers();
//		sessionUser.setNickName("aa");
//		sessionUser.setPhone("123231");
		if(sessionUser!=null){
			System.out.println("sessionUser有手机号");
			 if(log.isInfoEnabled()){
		         log.info("session info, user nick :"+sessionUser.getNickName());
				 log.info("session is  :"+sessionUser);
		     }
//			 String url = "http://172.16.63.27:9006/form/static/chouj.html?tel"+sessionUser.getPhone();
			//放行  直接到chouj.html页面,带入参数  
			map.put("tel", sessionUser.getPhone());
			map.put("changeP", "no");  //不改变页面
			return map;
		}else{
			System.out.println("sessionUser为空");
			//本地测试添加openId 否则redirect接口不通，重定向需要配置到域名地址   openId=ofC0CtxWoPAeGX4lOoGJkoS-W91s  
//			String apiUrlopendId = "http://172.16.63.27:9006/form/static/demo.html";
//			response.sendRedirect(apiUrlopendId);
			map.put("tel", "notel");
			return map;
		}
	}*/
	@ResponseBody
	@RequestMapping(value = "/openidtel", method = RequestMethod.GET)
	public void opendidTel(HttpServletRequest request,HttpServletResponse response
			,String param,String publicId,String userInfo,String openId) throws IOException, ServletException{
		System.out.println("进入到了接口方法");
		System.out.println("param:"+param);
		System.out.println("publicId:"+publicId);
		System.out.println("userInfo:"+userInfo);
//		System.out.println("openId:"+openId);  //显示的是publicid
		if("".equals(openId)||"".equals(userInfo)){	  //userInfo没有
			System.out.println("接口参数不合法");
		}else{
			//判断用户信息中的isbind是什么状态（绑定还是未绑定）
			HttpSession hs = request.getSession();	
			//获取session的Id
			String sessionId = hs.getId();
			log.debug("\n session  id : "+sessionId);			
			//对用户信息进行解码
			String user = URLDecoder.decode(userInfo,"UTF-8");
			log.info("\n user info : "+user);
			JSONObject jsonobject = JSONObject.fromObject(user);	  
			WUsers usersession = new WUsers();
			usersession.setCardId(jsonobject.getString("wCardId"));
			usersession.setPhone(jsonobject.getString("wPhone"));
			usersession.setIsBind(jsonobject.getString("wIsbind"));
			usersession.setOpenId(jsonobject.getString("wOpenid"));
//			usersession.setSex(jsonobject.getString("wSex"));
//			usersession.setCountry(jsonobject.getString("wCountry"));
//			usersession.setProvince(jsonobject.getString("wProvince"));
//			usersession.setName(jsonobject.getString("wName"));
			usersession.setName(jsonobject.getString("wNickname"));
//			usersession.setCity(jsonobject.getString("wCity"));
			usersession.setPublicId(jsonobject.getString("wPublicId"));
			//将用户信息的json字符串转化成user对象之后判断一下用户的绑定状态（是绑定还是未绑定）
			String isbind =  jsonobject.getString("wIsbind");
			//调试
			//isbind = "1";
			if(isbind != null && "1".equals(isbind)){
				System.out.println("绑定了，保存session，返回原来的抽奖页面index.html");
			    //如果绑定了则保存session并跳转到业务页面
			    hs.setAttribute("usersession",usersession);
			    hs.setAttribute("openidsession",jsonobject.getString("wOpenid"));
			    //只能内部转向，不然会丢失session
			    response.sendRedirect(param+"&openId="+jsonobject.getString("wOpenid")+"&init=yes");
			}else if("0".equals(isbind)){
				System.out.println("没绑定，去绑定页面，带上相关的参数。");
				//如果没有该用户的isbind=0(未绑定)则跳转到绑定页面(带上userinfo和openId、actionName)
				//重定向到抽奖页面 
				String period2= param.substring(param.indexOf("=")+1);
				System.out.println(period2); 
				System.out.println("openid:"+jsonobject.getString("wOpenid"));
			    //如果绑定了则保存session并跳转到业务页面
			    hs.setAttribute("usersession",usersession);
			    hs.setAttribute("openidsession",jsonobject.getString("wOpenid"));
				response.sendRedirect(env.getProperty(SERVER_HOST)+"/form/static/binding.html?" +"period="+period2+
						"&publicid="+jsonobject.getString("wPublicId")+"&openId="+jsonobject.getString("wOpenid"));  //
			}
		}
	}
	@ResponseBody
	@RequestMapping(value = "/finda", method = RequestMethod.GET)
	public List<Work> findAllWork(HttpServletRequest request){
		return workRepository.findAll();
	}
	@ResponseBody
	@RequestMapping(value = "/workdata", method = RequestMethod.GET)
	public Map findAllWorkdata(HttpServletRequest request){
		List<Work> works=workRepository.findAll();
		HashMap<String, List<Work>> map = new HashMap<String, List<Work>>();
		map.put("data", works);
		return map;
	}
	@ResponseBody
	@RequestMapping(value = "/sangapi", method = RequestMethod.GET)
	public String sangapi(){
			return "{\"content\":{\"returnCode\":\"60001\",\"operation\":\"hnzxpt\",\"seiviceType\":\"SSIMPLESERV\",\"businessNumber\":\"12345678910\",\"lastPackageMark\":\"1\",\"mark\":\"1\",\"busNumberType\":\"G\",\"sequenceId\":\"14840110615279278009\",\"operator\":\"hnzxpt001\",\"packageNumber\":\"1\",\"packageSize\":\"103\",\"version\":\"11\"},\"status\":\"99999\",\"msg\":\"简单产品变更失败\"}";
	}
	@RequestMapping(value = "/works", method = RequestMethod.GET)
	 public  Page<Work> findAllWorks(Model model, Pageable pageable,ServletRequest request,String search){
		//Page<Work> works = workRepository.findAll(new PageRequest(page, size));
		Page<Work> works = workRepository.findAll(pageable); //page=,size=,sort=telNu,desc
//		Page<Work> works = workRepository.findBytelNu(tel, pageable);
		System.out.println("search:"+search);
		/*if(!search.equals("")){
			 Page<Work> workss =  workRepository.findbykeyword(search,pageable);
		}*/
		return works;
	}
	/*@RequestMapping(value = "/fwork", method = RequestMethod.GET)
	 public  Page<Work> findAllWorksLike( Model model, Pageable pageable,String search){
		Page<Work> works = workRepository.findbykeyword(search,pageable);
		return works;
	}*/
}