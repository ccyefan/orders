package com.bonc.wechat.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.wechat.domain.Wechat;
import com.bonc.wechat.repository.WechatRepository;

@Component
@Transactional
public class WechatService {
	@Autowired
	private WechatRepository wechatRepository;

	public Map<String, String> wechatinter(Long id, String openId,
			String phone, String sex, String userInfo) {
		JSONObject jsonObject = JSONObject.fromObject(userInfo);
		Wechat wechat = new Wechat();
		wechat.setOpenId(openId);
		wechat.setSex("男男女女");
		wechat.setPhone(jsonObject.getString("wPhone"));
		wechatRepository.save(wechat);

		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "保存成功！");
		return map;
	}
}
