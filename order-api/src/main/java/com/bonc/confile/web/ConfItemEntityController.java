package com.bonc.confile.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.confile.domain.ConfItem;
import com.bonc.confile.domain.FileInfo;
import com.bonc.confile.repository.FileRepository;
import com.bonc.confile.service.ConfItemService;

@RestController
public class ConfItemEntityController {
	@Autowired
	private ConfItemService confItemService;
	@Autowired
	private FileRepository fileRepository;
	@ResponseBody
	@RequestMapping(value = "/addline", method = RequestMethod.POST)
	public Map<String, String> addConfItem(@RequestBody ConfItem confitem) {
		return confItemService.addline(confitem);
	}
	@ResponseBody
	@RequestMapping(value="/delline",method=RequestMethod.GET)
	public Map<String,String> delConfItem(long confItemId){
		return confItemService.delConfItem(confItemId);
	}
	@ResponseBody
	@RequestMapping(value="/upline",method=RequestMethod.POST)
	public Map<String,String> updateConfItem(@RequestBody ConfItem confItem){
		return confItemService.updateConfItem(confItem);
	}
	@ResponseBody
	@RequestMapping(value = "/findItemByFileId", method = RequestMethod.GET)
	public  List<ConfItem> findConfItemByFileId(long fileid) {
		FileInfo fileInfo = fileRepository.findOne(fileid);
		return confItemService.findByFileId(fileInfo);
	}
}
