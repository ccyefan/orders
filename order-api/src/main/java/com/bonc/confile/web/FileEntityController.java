package com.bonc.confile.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.confile.domain.FileInfo;
import com.bonc.confile.service.FileService;

@RestController
public class FileEntityController {
	@Autowired
	private FileService fileService;
	@ResponseBody
	@RequestMapping(value="/createfile",method= RequestMethod.POST)
	public Map<String,String> createFile(@RequestBody FileInfo fileInfo){
		return fileService.createFileInfo(fileInfo);
	}
	@ResponseBody
	@RequestMapping(value="delFile",method=RequestMethod.GET)
	public Map<String,String> delFile(long fileInfoId){
		return fileService.delFileInfo(fileInfoId);
	}
	//前端分頁,添加data 包裝處理
	@ResponseBody
	@RequestMapping(value="/findAllFile",method = RequestMethod.GET)
	public List<FileInfo> findAllFile(){
		return fileService.findAllFile();
	}
	
}