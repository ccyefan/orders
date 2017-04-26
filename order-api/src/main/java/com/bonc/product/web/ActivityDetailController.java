package com.bonc.product.web;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.service.ActivityDetailService;


/**
 * Created by Administrator on 2016/8/28.
 */

@RestController
public class ActivityDetailController {

	@Autowired
	private ActivityDetailService activityDetailService;

	@ResponseBody
	@RequestMapping(value = "/findActivity", method = RequestMethod.GET)
	public List<ActivityDetail> getActivity(Long id) {
		return activityDetailService.findActivityDetail(id);
	}
    
	//保存的方法
	@RequestMapping(value = "/saveActivity", method = RequestMethod.POST)
	public ActivityDetail getInfor(@RequestBody ActivityDetail activitydetal) {
		return activityDetailService.saveActivity(activitydetal);
	}
	
	//修改的方法
	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	public ActivityDetail getUpdate(@RequestBody ActivityDetail activitydetal1) {
//		JSONObject json = JSONObject.fromObject(activitydetal1);
//		String str = json.toString();
//		System.out.println(str);
		return activityDetailService.updateActivity(activitydetal1);
	}
	
}
