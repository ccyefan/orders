package com.bonc.order.web;

/**
 * Created by moyong on 16/8/11.
 */


import com.bonc.order.domain.OUser;
import com.bonc.order.domain.ExcelTmp;
import com.bonc.order.domain.Work;
import com.bonc.order.repository.OUserRepository;
import com.bonc.order.repository.OrderRepository;
import com.bonc.order.repository.ExcelTmpRepository;
import com.bonc.order.repository.WorkRepository;
import com.bonc.order.utils.ReadExcel;
import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.domain.Product;
import com.bonc.product.repository.ActivityDetailRepository;
import com.bonc.product.repository.ProductRepository;
import com.supermy.security.AvatarRepository;
import com.supermy.security.domain.Avatar;
import com.supermy.utils.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ExcelFileUploadController {
	@Autowired
	private ActivityDetailRepository activityDetailRepository;
	@Autowired
	private OUserRepository ouserRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ExcelTmpRepository tmpRepository;
	
	@Autowired
	private WorkRepository workRepository;

	@Autowired
    private AvatarRepository avatarRepository;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelFileUploadController.class);

    public static final String ROOT = "upload-dir";

    @RequestMapping(value="/ExcelsingleUpload")
    public String singleUpload(){
        return "singleUpload";
    }

    /**
     * 单个文件上传，测试通过
     * @param file
     * @param desc
     * @return
     */
    @RequestMapping(value="/ExcelsingleSave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleSave(@RequestParam("upload") MultipartFile file){
//        System.out.println("File Description:"+desc);
        Map result = new HashMap();

        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(file.getOriginalFilename());

                Path target = Paths.get(ROOT, filename);
                Files.copy(file.getInputStream(), target);
                System.out.println(target);
                
                log.debug(target.getFileName().toString());
                log.debug(target.getParent().getFileName().toString());
                log.debug(target.toString());
                log.debug(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());

                log.debug("=============ContentType:"+file.getContentType());
//                FilenameUtils.getExtension()
                log.debug(Files.probeContentType(target));

                Avatar img=new Avatar();
                img.setFilename(file.getOriginalFilename());
                img.setFilesize(file.getSize());
                img.setWebpath(ROOT+"/"+filename);
                img.setSyspath(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
                img.setCreateDate(new Date());
                img.setCreateBy("user");
                avatarRepository.save(img);
                
                try{
                	List<List<Object>> list = ReadExcel.readExcel(new File(ROOT+"/"+filename));
                	 int i = 0,success=0,error=0;
                     for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 ExcelTmp tmp = new ExcelTmp();
                    	 System.out.println(list2);
//                    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    	 tmp.setCompleTime((Date)sdf.parse(list2.get(6).toString()));
                    	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    	 tmp.setEndTime((Date)sdf2.parse(list2.get(0).toString()));
//                    	 tmp.setResult(list2.get(1).toString().replace(".00", ""));
                    	 tmp.setStartTime((Date)sdf2.parse(list2.get(1).toString()));
                    	 tmp.setType((String) list2.get(2).toString().replace(".00", ""));
//                    	 tmp.setUrl(list2.get(4).toString());
                    	 tmp.setWXhsh((String) list2.get(3).toString().replace(".00", ""));
//                    	 ActivityDetail ac=  activityDetailRepository.getOne(Long.parseLong(list2.get(4).toString().replace(".00", "")));
                    	 ActivityDetail ac = activityDetailRepository.findByAcStep((list2.get(4).toString().replace(".00", "")));
                    	 tmp.setActivityDetail(ac==null?null:ac);
                    	 //OUser ouser = ouserRepository.findByTelNumber(list2.get(5).toString().replace(".00", ""));
//                    	 OUser ouser = ouserRepository.getOne(Long.parseLong(list2.get(5).toString().replace(".00", "")));
                    	 //tmp.setOuser(ouser==null?null:ouser);
//                    	 Product product = productRepository.getOne(Long.parseLong(list2.get(6).toString().replace(".00", "")));
                    	 Product product = productRepository.findByProName((list2.get(6).toString().replace(".00", "")));
                    	 tmp.setProduct(product==null?null:product);
                    	 
                    	 tmpRepository.save(tmp);
                     }
                     //临时表保存完成，数据保存到目标数据表
                     
                     i = 0;success=0;error=0;
                     for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 Work work = new Work();
                    	 System.out.println(list2);
//                    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    	 work.setCompleTime((Date)sdf.parse(list2.get(6).toString()));
                    	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    	 work.setEndTime((Date)sdf2.parse(list2.get(0).toString()));
//                    	 work.setResult(list2.get(1).toString().replace(".00", ""));
                    	 work.setStartTime((Date)sdf2.parse(list2.get(1).toString()));
//                    	 work.setUrl(list2.get(4).toString());
                    	 work.setWXhsh((String) list2.get(3).toString().replace(".00", ""));
//                    	 ActivityDetail ac=  activityDetailRepository.getOne(Long.parseLong(list2.get(4).toString().replace(".00", "")));
                    	 ActivityDetail ac = activityDetailRepository.findByAcStep((list2.get(4).toString().replace(".00", "")));
                    	 work.setActivityDetail(ac==null?null:ac);
//                    	 OUser ouser = ouserRepository.getOne(Long.parseLong(list2.get(5).toString().replace(".00", "")));
//                    	 OUser ouser = ouserRepository.findByTelNumber(list2.get(5).toString().replace(".00", ""));
//                    	 work.setOuser(ouser==null?null:ouser);
//                    	 Product product = productRepository.getOne(Long.parseLong(list2.get(6).toString().replace(".00", "")));
                    	 Product product = productRepository.findByProName((list2.get(6).toString().replace(".00", "")));
                    	 work.setProduct1(product==null?null:product);
                    	 
                    	 workRepository.save(work);
                     }
                     tmpRepository.deleteAll();
                }catch (Exception e) {
                	e.printStackTrace();
                	tmpRepository.deleteAll();
                	result.put("stat", "error");
				}

                result.put("data","");
                result.put("upload",img);
                Map m = new HashMap();

                List<Avatar> files = avatarRepository.findAll();
                Map mm=new HashMap();
                for (Avatar f:files
                     ) {
                    mm.put(f.getId(),f);
                }
                m.put("files", mm);

                result.put("files",m);
//                result.put("files",avatarRepository.findAll());

                result.put("desc","文件上传成功"+file.getOriginalFilename());

                return result;

            } catch (IOException  e) {
                result.put("desc","文件上传失败"+file.getOriginalFilename());
                return result;
            }
        } else {
            result.put("desc", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
            return result;
        }

    }

    @RequestMapping(value="/ExcelmultipleUpload")
    public String multiUpload(){
        return "multipleUpload";
    }

    /**
     * 返回数据格式需要完善。
     *
     * @param files
     * @return
     */
    @RequestMapping(value="/ExcelmultipleSave", method=RequestMethod.POST )
    public @ResponseBody
    Map multipleSave(@RequestParam("upload") MultipartFile[] files){
        String msg = "";
        Map result = new HashMap();
        List list = new ArrayList();
        if (files != null && files.length > 0) {

            for (int i = 0; i < files.length; i++) {
                try {
                    String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(files[i].getOriginalFilename());

                    Path target = Paths.get(ROOT, filename);
                    Files.copy(files[i].getInputStream(), target);


                    Avatar img = new Avatar();
                    img.setFilename(files[i].getOriginalFilename());
                    img.setFilesize(files[i].getSize());
                    img.setWebpath(ROOT + "/" + filename);
                    img.setSyspath(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
                    img.setCreateDate(new Date());
                    img.setCreateBy("user");
                    avatarRepository.save(img);

                    list.add(img);

                }

                catch(Exception e){

                    result.put("desc", "文件上传失败" + "You failed to upload " + files[i].getOriginalFilename() + ": " + e.getMessage() + "<br/>");
                }
            }

            result.put("data", list);

            return result;
            
            
        } else {
            result.put("desc", "Unable to upload. File is empty.");

            return result;
        }
    }
}
