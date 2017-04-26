package com.supermy.web;


import com.bonc.product.domain.Activity;
import com.bonc.product.domain.ActivityDetail;
import com.bonc.product.domain.ActivityDetailTmp;
import com.bonc.product.domain.ActivityTmp;
import com.bonc.product.domain.Product;
import com.bonc.product.domain.ProductTmp;
import com.bonc.product.repository.ActivityDetailRepository;
import com.bonc.product.repository.ActivityDetailTmpRepository;
import com.bonc.product.repository.ActivityRepository;
import com.bonc.product.repository.ActivityTmpRepository;
import com.bonc.product.repository.ProductRepository;
import com.bonc.product.repository.ProductTmpRepository;
import com.supermy.security.AvatarRepository;
import com.supermy.security.domain.Avatar;
import com.supermy.utils.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Controller
public class ExcelController {

    @Autowired
    private AvatarRepository avatarRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private ActivityDetailRepository activityDetailRepository;
    
    @Autowired
    private ProductTmpRepository productTmpRepository;
    
    @Autowired
    private ActivityTmpRepository activityTmpRepository;
    
    @Autowired
    private ActivityDetailTmpRepository activityDetailTmpRepository;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelController.class);

    public static final String ROOT = "upload-dir";

    @RequestMapping(value="/excelsingleUpload")
    public String singleUpload(){
        return "singleUpload";
    }

    /**
     * 单个文件上传，并将产品数据保存到数据库
     * @param file
     * @param desc
     * @return
     */
    @RequestMapping(value="/excelSave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleSave(@RequestParam("upload") MultipartFile file, @RequestParam("action") String desc ){
        System.out.println("File Description:"+desc);
        Map result = new HashMap<String, Object>();

        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(file.getOriginalFilename());

                Path target = Paths.get(ROOT, filename);
                Files.copy(file.getInputStream(), target);

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

                String message = "";
                
                //将Excel中的数据保存到表中
                try{
                	List<List<Object>> list = ReadExcelController.readExcel(new File(ROOT+"/"+filename));
                	 int i = 0,success=0,error=0;
                	 
                	 //将数据保存到临时表中
                	 for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 ProductTmp productTmp=new ProductTmp();
                    	 //productTmp.setId(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                    	 productTmp.setPrice(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                    	 productTmp.setProductDetail(list2.get(1).toString());
                    	 productTmp.setProductName(list2.get(2).toString());
                    	 productTmp.setType(list2.get(3).toString());
                    	 productTmpRepository.save(productTmp);
                     }
                	 
                	 
                	 //如果数据没错，再保存到正式表
                     for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 Product product=new Product();
                    	 //product.setId(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                         product.setPrice(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                         product.setProductDetail(list2.get(1).toString());
                         product.setProductName(list2.get(2).toString());
                         product.setProduct_type(list2.get(3).toString());
                         productRepository.save(product);
                     }
                	
                }catch (Exception e) {
                	e.printStackTrace();//如果有异常就清空临时表
                	productTmpRepository.deleteAll();
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

    
    
    
    /**
     * 单个文件上传，并将活动数据保存到数据库
     * @param file
     * @param desc
     * @return
     */
    @RequestMapping(value="/excelActivitySave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleActivitySave(@RequestParam("upload") MultipartFile file, @RequestParam("action") String desc ){
        System.out.println("File Description:"+desc);
        Map result = new HashMap();

        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(file.getOriginalFilename());

                Path target = Paths.get(ROOT, filename);
                Files.copy(file.getInputStream(), target);

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

                String message = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
//                Date date = sdf.parse(dateString);             
                //将Excel中的数据保存到表中
                try{
                	List<List<Object>> list = ReadExcelController.readExcel(new File(ROOT+"/"+filename));
                	 int i = 0,success=0,error=0;
                	//将数据保存到临时表中
                	 for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 ActivityTmp activityTmp=new ActivityTmp();
                    	 //activity.setId(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                    	 activityTmp.setActivityName(list2.get(0).toString());
                    	 activityTmp.setActivityTime((Date)sdf.parse(list2.get(1).toString()));
                    	 activityTmp.setRemarks(list2.get(2).toString());
                    	 activityTmp.setStartTime((Date)sdf.parse(list2.get(3).toString()));
                    	 activityTmp.setStopTime((Date)sdf.parse(list2.get(4).toString()));
                    	 activityTmpRepository.save(activityTmp);
                     }
                	//如果数据没错，再保存到正式表
                     for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 Activity activity=new Activity();
//                    	 activity.setId(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                    	 activity.setActivityName(list2.get(0).toString());
                    	 activity.setActivityTime((Date)sdf.parse(list2.get(1).toString()));
                    	 activity.setRemarks(list2.get(2).toString());
                    	 activity.setStartTime((Date)sdf.parse(list2.get(3).toString()));
                    	 activity.setStopTime((Date)sdf.parse(list2.get(4).toString()));
                    	 activityRepository.save(activity);
                     }
                	
                }catch (Exception e) {
                	e.printStackTrace();//如果有异常就清空临时表
                	activityTmpRepository.deleteAll();
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
    
    
    /**
     * 单个文件上传，并将活动明细数据保存到数据库
     * @param file
     * @param desc
     * @return
     */
    @RequestMapping(value="/excelActivityDetailSave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleActivityDetailSave(@RequestParam("upload") MultipartFile file, @RequestParam("action") String desc ){
        System.out.println("File Description:"+desc);
        Map result = new HashMap();

        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(file.getOriginalFilename());

                Path target = Paths.get(ROOT, filename);
                Files.copy(file.getInputStream(), target);

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

                String message = "";
                //将Excel中的数据保存到表中
                try{
                	List<List<Object>> list = ReadExcelController.readExcel(new File(ROOT+"/"+filename));
                	 int i = 0,success=0,error=0;
                	//将数据保存到临时表中
                	 for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 ActivityTmp ac1=activityTmpRepository.findOne(Long.parseLong(list2.get(3).toString().replace(".00", "")));
                    	 
                    	 ActivityDetailTmp activityDetailTmp=new ActivityDetailTmp();
                    	 activityDetailTmp.setActivityStep(list2.get(0).toString());
                    	 activityDetailTmp.setContractPeriod(Integer.parseInt(list2.get(1).toString().replace(".00", "")));
                    	 activityDetailTmp.setMealPrices(Integer.parseInt(list2.get(2).toString().replace(".00", "")));
                    	 activityDetailTmp.setActivityTmp(ac1==null ? null : ac1);
                    	 
                    	 activityDetailTmpRepository.save(activityDetailTmp);
                     }
                	//如果数据没错，再保存到正式表
                     for (List<Object> list2 : list) {
                    	 if(i++==0) continue; //表头不读取
                    	 if(list2==null||list2.size()==0) break;  //读取到空结束
                    	 
                    	 Activity ac = activityRepository.findOne(Long.parseLong(list2.get(3).toString().replace(".00", "")));
                    	 
                    	 ActivityDetail activityDetail=new ActivityDetail();
                    	 //activityDetail.setId(Integer.parseInt(list2.get(0).toString().replace(".00", "")));
                    	 activityDetail.setActivityStep(list2.get(0).toString());
                    	 activityDetail.setContractPeriod(Integer.parseInt(list2.get(1).toString().replace(".00", "")));
                    	 activityDetail.setMealPrices(Integer.parseInt(list2.get(2).toString().replace(".00", "")));
                    	 activityDetail.setActivity(ac==null ? null : ac);
                    	 
                    	 activityDetailRepository.save(activityDetail);
                     }
                }catch (Exception e) {
                	e.printStackTrace();//如果有异常就清空临时表
                	activityDetailTmpRepository.deleteAll();
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
    
    
    @RequestMapping(value="/excelmultipleUpload")
    public String multiUpload(){
        return "multipleUpload";
    }

    /**
     * 返回数据格式需要完善。
     *
     * @param files
     * @return
     */
    @RequestMapping(value="/excelmultipleSave", method=RequestMethod.POST )
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

