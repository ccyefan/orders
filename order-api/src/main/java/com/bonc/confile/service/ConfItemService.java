package com.bonc.confile.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.confile.domain.ConfItem;
import com.bonc.confile.domain.FileInfo;
import com.bonc.confile.repository.ConfItemRepository;
import com.bonc.confile.repository.FileRepository;

@Component
@Transactional
public class ConfItemService {
	@Autowired
	private ConfItemRepository confItemRepository;
	@Autowired
	private FileRepository fileRepository;
	public List<ConfItem> findByFileId(FileInfo fileInfo){
		return confItemRepository.findByFileInfo(fileInfo);
	}
	public Map<String, String> addline(ConfItem confitem){
		//保存数据
		confItemRepository.save(confitem);
		//文件同步
		FileInfo fileInfo = fileRepository.getOne(confitem.getFileInfo().getId());
		List<ConfItem> list = confItemRepository.findByFileInfo(confitem.getFileInfo());
		updateProperties(""+fileInfo.getPath()+fileInfo.getFileName(),list);
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg","addConfItemSuccess");
		return map; 
	}
	public Map<String,String> delConfItem(long confItemId){
		ConfItem confitem = confItemRepository.getOne(confItemId);
		FileInfo fileInfo = fileRepository.getOne(confitem.getFileInfo().getId());
		//先删除列表数据
		confItemRepository.delete(confItemId);
		//再刷新文件
		List<ConfItem> list = confItemRepository.findByFileInfo(fileInfo);
		updateProperties(""+fileInfo.getPath()+fileInfo.getFileName(),list);
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg","delConfItemSuccess");
		return map; 
	}
	public Map<String, String> updateConfItem(ConfItem confItem) {
		ConfItem upConfItem = confItemRepository.getOne(confItem.getId());
		upConfItem.setConfKey(confItem.getConfKey());
		upConfItem.setConfValue(confItem.getConfValue());
		confItemRepository.save(upConfItem);
		//同步文件
		List<ConfItem> list = confItemRepository.findAll();
		updateProperties("./demo.conf", list);
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg","delConfItemSuccess");
		return map; 
	}
	public static void updateProperties(String fileName,List<ConfItem> list) {
        //getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，  
        //得到的往往不是我们想要的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径。  
        //String filePath = demo.class.getClassLoader().getResource(fileName).getFile();  
        Properties properties = new Properties();
        BufferedWriter bw = null;  
  
        try {  
 	       File file = new File(fileName);
 	      
 	       FileInputStream fis = new FileInputStream(file);
 	      
 	       properties.load(fis);
            // 写入属性文件  
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));  
              
            properties.clear();// 清空旧的文件  
              
            for (ConfItem confile: list){  
            	/*System.out.println(confile.getConf_value().toString());
            	String escape = StringEscapeUtils.escapeJava(confile.get);
            	System.out.println( StringEscapeUtils.unescapeJava(escape));*/
            	properties.setProperty(confile.getConfKey(), confile.getConfValue());
            }
            System.out.println("updateProperties new:"+properties);
            properties.store(bw, "");
            fis.close();
        } catch (IOException e) {  
        	System.out.println(e.getMessage());
        } finally {  
            try {  
                bw.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
}
