package com.bonc.order.web;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.order.domain.Work;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.utils.HttpUtils;
/**
 * 发送短信验证码
 * @author alec
 *
 */
@RestController
@Configuration
public class SmsSendController{
	private static final String SMS_HOST="sms.host";
	private static final String SMS_TENANT_ID="sms.tenant_id";
	@Resource
	private Environment env;
	@Resource
	private WorkRepository workRepository;
	
	@ResponseBody
	@RequestMapping(value = "/smsSend", method = RequestMethod.GET)
	public Map<String,String> handleRequest(String workid,HttpServletRequest request){
		System.out.println("workid:"+workid);
		Work work = workRepository.getOne(Long.parseLong(workid));
		System.out.println("租户id:"+work.getTenant_id());
		//验证码
		String captchaCode= getNumber(6);
		Map<String,String> map = new HashMap<String,String>();
		//查询网关Id
		String result=HttpUtils.httpStrRequest(env.getRequiredProperty(SMS_HOST)+"/smsinterface/smsSet/tenantid/"+work.getTenant_id(),"GET",null);
//		String result=HttpUtils.httpStrRequest(env.getRequiredProperty(SMS_HOST)+"/smsinterface/smsSet/tenantid/"+env.getRequiredProperty(SMS_TENANT_ID),"GET",null);
//		String result=HttpUtils.httpStrRequest(env.getRequiredProperty(SMS_HOST)+"/form/sms1","GET",null);
		System.out.println("smsresult:"+result);
		/**
		 * 测试
		 */
		/**
		System.out.println("短信验证码："+captchaCode);
		HttpSession session = request.getSession();
		session.setAttribute("captchaCode", captchaCode);
		//记录发送时间
		java.util.Date today=new java.util.Date();    
	    long smsSendTime = today.getTime();
	    session.setAttribute("smsSendTime", smsSendTime);
		*/
		if(!"".equals(result)){
			//解析返回结果
			JSONArray array = JSONArray.fromObject(result);
			JSONObject jsonObject = (JSONObject) array.get(0);
			String returnflag =(String) jsonObject.get("returnflag");
			
			if(returnflag!=null&&"0".equals(returnflag)){ //网关id存在 
				JSONArray smssetids = (JSONArray) jsonObject.get("smssetid");
				String smssetid = (String) smssetids.get(0);
				System.out.println("smssetid:"+smssetid);
				//发送单条短信
				String smsParam = HttpUtils.makeSmsTextJsonParam(smssetid,work.getTelNu(),"您的短信验证码为："+captchaCode+",有效时间60秒。");
				System.out.println("sms:"+captchaCode);
				System.out.println("smsParam:"+smsParam);
				String smsResult = HttpUtils.httpStrRequestSmsPost(env.getRequiredProperty(SMS_HOST)+"/smsinterface/sms/sendsingle", "POST", smsParam);
//				String smsResult = HttpUtils.httpStrRequestSmsPost(env.getRequiredProperty(SMS_HOST)+"/form/sms2", "POST", smsParam);
				if("".equals(smsResult)){
					System.out.println("请求失败");
				}
				System.out.println("smsResult:"+smsResult);
				JSONArray smsArray = JSONArray.fromObject(smsResult);
				JSONObject smsJson = (JSONObject) smsArray.get(0);
				String smsreturnflag = (String) smsJson.get("returnflag");
				if(smsreturnflag!=null&&"0".equals(smsreturnflag)){ //短信发送成功
					System.out.println("验证码发送成功");
					map.put("massage", "验证码发送成功");
					//验证码放入session
					HttpSession session = request.getSession();
					session.setAttribute("captchaCode", captchaCode);
					//记录发送时间
					java.util.Date today=new java.util.Date();    
				    long smsSendTime = today.getTime();
				    session.setAttribute("smsSendTime", smsSendTime);
				}else{ //短信发送失败
					System.out.println("业务异常");
					map.put("massage", "业务异常");
//					JSONObject errorObject=(JSONObject) smsJson.get("error");
//					int errorCode = Integer.parseInt((String) errorObject.get("errorCode"));
//					switch (errorCode) {
//					case 1:
//						map.put("massage", "发送失败，网关id有误，为空值或不存在");
//						break;
//					case 2:
//						map.put("massage", "发送失败，手机号码有误");
//						break;
//					case 3:
//						map.put("massage", "发送失败，网关id和手机号码有误");
//						break;
//					default:
//						map.put("massage", "发送失败，发送等级超出1-4");
//						break;
//					}
				}
			}else{ //网关id 不存在
				System.out.println("网关id查询失败");
				map.put("massage", "业务异常");
				
			}
		}else{//请求失败
			map.put("massage", "业务异常");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping(value = "/bdsms", method = RequestMethod.GET)
	public Map<String,String> bdsmshandleRequest(String tel,HttpServletRequest request){
		System.out.println("tel:"+tel);
		//验证码
		String captchaCode= getNumber(6);
		Map<String,String> map = new HashMap<String,String>();
		//查询网关Id
//		String result=HttpUtils.httpStrRequest(env.getRequiredProperty(SMS_HOST)+"/smsinterface/smsSet/tenantid/"+env.getRequiredProperty(SMS_TENANT_ID),"GET",null);
		String result=HttpUtils.httpStrRequest(env.getRequiredProperty(SMS_HOST)+"/form/sms1","GET",null);
		System.out.println("smsresult:"+result);
		if(!"".equals(result)){
			//解析返回结果
			JSONArray array = JSONArray.fromObject(result);
			JSONObject jsonObject = (JSONObject) array.get(0);
			String returnflag =(String) jsonObject.get("returnflag");
			
			if(returnflag!=null&&"0".equals(returnflag)){ //网关id存在 
				JSONArray smssetids = (JSONArray) jsonObject.get("smssetid");
				String smssetid = (String) smssetids.get(0);
				System.out.println("smssetid:"+smssetid);
				//发送单条短信
				String smsParam = HttpUtils.makeSmsTextJsonParam(smssetid,tel,"您的短信验证码为："+captchaCode+",有效时间60秒。");
				System.out.println("sms:"+captchaCode);
				System.out.println("smsParam:"+smsParam);
				String smsResult = HttpUtils.httpStrRequestSmsPost(env.getRequiredProperty(SMS_HOST)+"/smsinterface/sms/sendsingle", "POST", smsParam);
//				String smsResult = HttpUtils.httpStrRequestSmsPost(env.getRequiredProperty(SMS_HOST)+"/form/sms2", "POST", smsParam);
				if("".equals(smsResult)){
					System.out.println("请求失败");
				}
				System.out.println("smsResult:"+smsResult.toString());
				JSONArray smsArray = JSONArray.fromObject(smsResult);
				JSONObject smsJson = (JSONObject) smsArray.get(0);
				String smsreturnflag = (String) smsJson.get("returnflag");
				if(smsreturnflag!=null&&"0".equals(smsreturnflag)){ //短信发送成功
					System.out.println("验证码发送成功");
					map.put("massage", "验证码发送成功");
					//验证码放入session
					HttpSession session = request.getSession();
					session.setAttribute("bdcaptchaCode", captchaCode);
					//记录发送时间
					java.util.Date today=new java.util.Date();    
				    long smsSendTime = today.getTime();
				    session.setAttribute("bdsmsSendTime", smsSendTime);
				}else{ //短信发送失败
					System.out.println("业务异常");
					map.put("massage", "业务异常");
//					JSONObject errorObject=(JSONObject) smsJson.get("error");
//					int errorCode = Integer.parseInt((String) errorObject.get("errorCode"));
//					switch (errorCode) {
//					case 1:
//						map.put("massage", "发送失败，网关id有误，为空值或不存在");
//						break;
//					case 2:
//						map.put("massage", "发送失败，手机号码有误");
//						break;
//					case 3:
//						map.put("massage", "发送失败，网关id和手机号码有误");
//						break;
//					default:
//						map.put("massage", "发送失败，发送等级超出1-4");
//						break;
//					}
				}
			}else{ //网关id 不存在
				System.out.println("网关id查询失败");
				map.put("massage", "业务异常");
				
			}
		}else{//请求失败
			map.put("massage", "业务异常");
		}
		return map;
	}
	//验证码随机数
	private static String getNumber(int size){
	    String str = "0123456789";
	    String number = "";
	    Random r = new Random();
        for(int i=0;i<size;i++){
            number+=str.charAt(r.nextInt(str.length()));
        }
        return number;
	}

//	public static void main(String[] args){
////		System.out.println(makeSmsTextJsonParam("sss", "bbb", getNumber(6)));
////		System.out.println(WchatUtils.makeTextCustomMessage("ssss", "bbb", ""));
//		
////		String smsParam = makeSmsTextJsonParam("3037145623","13621920001","fdsfsd");
////		System.out.println("smsParam:"+smsParam);
////		String smsResult = httpStrRequestSmsPost("http://127.0.0.1:8080/smsinterface/sms/sendsingle", "POST", smsParam);
//		String smsResult = httpStrRequestSmsPost("http://localhost:9006/form/returndemo", "GET", null);
//		System.out.println("smsResult:"+smsResult);
//		
//		
////		try {
////			String result = executeGet("http://localhost:9006/form/returndemo");
////			System.out.println(result);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
////        BufferedReader in = null;  
////        String content = null;  
////        HttpClient client = new DefaultHttpClient();  
////        // 实例化HTTP方法  
////        HttpGet request = new HttpGet();  
////        try {
////			request.setURI(new URI("http://localhost:9006/form/returndemo"));
////	        HttpResponse response = client.execute(request); 
////	        
////	        System.out.println(response.getStatusLine().getStatusCode());
//	        
////	        System.out.println("response:"+response);
////	        
////	        System.out.println("getEntity:"+response.getEntity());
////	        System.out.println("getContent:"+response.getEntity().getContent());
////	        in = new BufferedReader(new InputStreamReader(response.getEntity()  
////	                .getContent()));  
////	        System.out.println("in:"+in);
////	        
////	        System.out.println("in.readLine:"+in.readLine());
////	        StringBuffer sb = new StringBuffer("");  
////	        System.out.println(sb.append(in.readLine()));
////	        String line = "";  
////	        String NL = System.getProperty("line.separator");  
////	        
////	        
////	        while ((line = in.readLine()) != null) {  
////	            sb.append(line + NL);  
////	            System.out.println("line:"+line);
////	            System.out.println("NL:"+NL);
////	        }  
////	        in.close();  
////	        content = sb.toString();
////	        System.out.println("content:"+content);
////        } catch (Exception e) {
////			e.printStackTrace();
////		}  
//	}	
//	public static String executeGet(String url) throws Exception {
//        BufferedReader in = null;  
//  
//        String content = null;  
//        try {  
//            // 定义HttpClient  
//            HttpClient client = new DefaultHttpClient();  
//            // 实例化HTTP方法  
//            HttpGet request = new HttpGet();  
//            request.setURI(new URI(url));  
//            HttpResponse response = client.execute(request);  
//  
//            in = new BufferedReader(new InputStreamReader(response.getEntity()  
//                    .getContent()));  
//            StringBuffer sb = new StringBuffer("");  
//            String line = "";  
//            String NL = System.getProperty("line.separator");  
//            while ((line = in.readLine()) != null) {  
//                sb.append(line + NL);  
//            }  
//            in.close();  
//            content = sb.toString();  
//        } finally {  
//            if (in != null) {  
//                try {  
//                    in.close();// 最后要关闭BufferedReader  
//                } catch (Exception e) {  
//                    e.printStackTrace();  
//                }  
//            }  
//            return content;  
//        }  
//    }  
}
