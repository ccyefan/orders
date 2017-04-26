package com.bonc.order.web;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.image.ImageCaptchaService;
/**
 * 注入beng方式的验证码码
 * @author alec
 *
 */
@Controller
@RequestMapping("/captcha")
public class JcaptchaImageCreaterController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@RequestMapping
	  public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		try {
		      ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		      String captchaId = request.getSession().getId();
		      BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, request.getLocale());

		      response.setHeader("Cache-Control", "no-store");
		      response.setHeader("Pragma", "no-cache");
		      response.setDateHeader("Expires", 0L);
		      response.setContentType("image/jpeg");
		      
		      java.util.Date today=new java.util.Date();
		      long t1 = today.getTime();
		      request.getSession().setAttribute("t1", t1);
		      
		      ImageIO.write(challenge, "jpeg", jpegOutputStream);
		      byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		      ServletOutputStream respOs = response.getOutputStream();
		      respOs.write(captchaChallengeAsJpeg);
		      respOs.flush();
		      respOs.close();
		 } catch (IOException e) {
		      logger.error("generate captcha image error: {}", e.getMessage());
		 }
		
	}
}
