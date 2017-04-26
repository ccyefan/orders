package com.bonc.confile.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.bonc.confile.domain.ConfItem;
import com.bonc.confile.domain.FileInfo;
import com.bonc.confile.repository.FileRepository;
import com.bonc.order.utils.FileUtil;

@Component
@Transactional
public class FileService {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private ConfItemService confItemService;
	public Map<String,String> createFileInfo(FileInfo fileInfo){
		Map<String,String> map = new HashMap<String,String>();
		String filePath = fileInfo.getPath();
		filePath.replace("/", File.separator);
		filePath.replace("\\", File.separator);
		System.out.println("filePath:"+filePath);
		//创建目录
		File path = new File(filePath);
		//创建文件
		File file = new File(filePath+""+fileInfo.getFileName());
		try {
			if(path.exists()){
				System.out.println("fileName:"+file.getPath());
				//若该文件不存在则创建
				if(!file.exists()){
					//创建新文件
					file.createNewFile();
					System.out.println("创建完毕!");
					map.put("msg", "createFileSuccess");
					fileRepository.save(fileInfo);
				}else{
					//文件已经存在
					map.put("msg","fileExists");
				}
			}else{//创建目录和文件
				path.mkdirs();
				file.createNewFile();
				map.put("msg", "createFileSuccessAndAddPath");
				fileRepository.save(fileInfo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String,String> delFileInfo(long FileInfoId){
		Map<String,String> map = new HashMap<String,String>();
		FileInfo fileInfo = fileRepository.getOne(FileInfoId);
		List<ConfItem> confItems = confItemService.findByFileId(fileInfo);
		if(confItems.isEmpty()){
			FileUtil fileUtil = new FileUtil();  //三个方法：空目录，文件，目录及其文件
			String filePath = fileInfo.getPath();
			filePath.replace("/", File.separator);
			filePath.replace("\\", File.separator);
			String pathFile = "" + filePath + fileInfo.getFileName();
			System.out.println("pathFile:"+pathFile);
			System.out.println(fileUtil.deleteFile(pathFile));
			
			//递归删除文件所在的目录
			FileUtil.delNullDir(filePath);
			//前端删除
			fileRepository.delete(FileInfoId);
			map.put("msg", "delsuccess");
		}else{
			map.put("msg","notnull"); //文件不为空
		}
		return map;
	}
	//前端分頁處理
	public List<FileInfo> findAllFile() {
		return fileRepository.findAll();
	}
}
