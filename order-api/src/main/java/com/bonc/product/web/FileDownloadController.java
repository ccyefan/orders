package com.bonc.product.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@RestController
public class FileDownloadController {
	
//	@ResponseBody
//	@RequestMapping("/download")  
//	public void download(HttpServletResponse res ,HttpServletRequest req) throws IOException {
//	    OutputStream os = res.getOutputStream();  
//	    try {  
//	        res.reset();
//	        res.setHeader("Content-Disposition", "attachment; filename=Product.xlsx");
//	        res.setContentType("application/octet-stream; charset=utf-8");  
//	        os.write(FileUtils.readFileToByteArray(getDictionaryFile(req)));
//	        os.flush();
//	    } finally {  
//	        if (os != null) {  
//	            os.close();
//	        }  
//	    }  
//	}  
//	
//	 private File getDictionaryFile(HttpServletRequest req) {
//		File file =new File(req.getSession().getServletContext().getRealPath("/resources/Product.xlsx"));
//		return file;
//	}
	

	public String upload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
               // 这里我用到了jar包
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();

					String path1 = Thread.currentThread()
							.getContextClassLoader().getResource("").getPath()
							+ "download" + File.separator;

					//  下面的加的日期是为了防止上传的名字一样
					String path = path1
							+ new SimpleDateFormat("yyyyMMddHHmmss")
									.format(new Date()) + fileName;

					File localFile = new File(path);

					file.transferTo(localFile);
				}

			}

		}
		return "uploadSuccess";

	}
	@RequestMapping("/toUpload.do")
	public String toUpload() {
		return "upload";
	}
    
	//下载产品的模板
	@ResponseBody
	@RequestMapping("/download")
	public String download(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=Product.xlsx");
		try {
			InputStream inputStream = new FileInputStream(new File("src/test/resources/Product模板.xlsx"));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			 // 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//下载活动的模板
	@ResponseBody
	@RequestMapping("/downloadActivity")
	public String downloadActivity(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=Activity.xlsx");
		try {
			InputStream inputStream = new FileInputStream(new File("src/test/resources/Activity模板.xlsx"));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			 // 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/downloadActivityDetail")
	public String downloadActivityDetail(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=ActivityDetail.xlsx");
		try {
			InputStream inputStream = new FileInputStream(new File("src/test/resources/ActivityDetail模板.xlsx"));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			 // 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping("/downloadWork")
	public String downloadWork(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=work模板.xlsx");
		try {
			InputStream inputStream = new FileInputStream(new File("src/test/resources/work模板.xlsx"));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			 // 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
