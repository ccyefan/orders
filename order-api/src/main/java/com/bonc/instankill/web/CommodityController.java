package com.bonc.instankill.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bonc.instankill.service.CommodityService;


@RestController
public class CommodityController {
	@Autowired
	    private CommodityService commodityService;
	 @ResponseBody
	    @RequestMapping(value = "/gen/instankill", method = RequestMethod.GET)
	    public Map genInstankill(String period)
	            throws ResourceNotFoundException, HttpRequestMethodNotSupportedException {
	        System.out.println("********************period:"+period);
	        return commodityService.genInstankill(period);//生成秒杀商品表
	 }
	 

}