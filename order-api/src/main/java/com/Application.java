package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.supermy.db.DataBaseConfig;
import com.supermy.db.UploadConfig;


//import javax.servlet.*;
//import java.util.Enumeration;

@Configuration
@EnableMongoRepositories
@EnableJpaRepositories
@Import({RepositoryRestMvcConfiguration.class,DataBaseConfig.class})
@PropertySource("classpath:application-4a.properties")
@EnableAutoConfiguration
@EnableWebMvc
//@ComponentScan({"**.service","hello","**.web"})  //有问题
@ComponentScan("com.**")
//@ServletComponentScan
public class Application  extends WebMvcConfigurerAdapter {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
//	@Bean
//	@ConditionalOnMissingBean(SingleSignOutHttpSessionListener.class)
//	public SingleSignOutHttpSessionListener requestContextListener() {
//		return new SingleSignOutHttpSessionListener();
//	}
//
//	/**
//	 * 配置单点登录的filter
//	 * @return
//     */
//	@Bean
//	public FilterRegistrationBean ssoFilterRegistration() {
//
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(ssoFilter());
//		registration.addInitParameter("serverName", "133.224.220.66:80");
//		registration.addInitParameter("casServerUrlPrefix", "http://133.224.220.66/cas");
//		registration.addInitParameter("casServerLoginUrl", "http://133.224.220.66/cas/login");
//		registration.addInitParameter("singleSignOut", "true");
//		registration.addInitParameter("skipUrls", "/out.jsp,.*\\.(css|js|jpg|jpeg|bmp|png|gif|ico)$");
//		//out.jsp,/index.jsp,/struts/.*,/resources/*,.*\.(css|js|jpg|jpeg|bmp|png|gif|ico|less|woff|tff|woff2)$
//		registration.addInitParameter("loginUserHandle", "com.xxxx.sso.client.impl.DemoAuthHandleImpl");
//		registration.addInitParameter("encoding", "UTF-8");
//
//		registration.addUrlPatterns("/*");
//
//		registration.setName("ssoFilter");
//		return registration;
//	}
//
//	@Bean(name = "ssoFilter")
//	public Filter ssoFilter() {
//		return new com.xxxx.sso.client.SSOFilter();
//	}

	/**
	 * 静态资源权限配置
	 */
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" ,"public","resources","static","html"};


	/**
	 * 特殊资源权限配置 WebMvcConfigurerAdapter
	 *
	 * @param registry
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(
					CLASSPATH_RESOURCE_LOCATIONS);
		}
	}



	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

		RepositoryRestConfiguration restConfiguration = ctx.getBean(RepositoryRestConfiguration.class);

		restConfiguration.setReturnBodyOnCreate(true);
		restConfiguration.setReturnBodyOnUpdate(true);

	}


	@Value("${bi.pool}")
	private int bipool;

	@Bean(name = "bipool")
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(bipool);
	}


	@Bean
	public CommandLineRunner UploadConfig(){
		return new UploadConfig();
	}
	/**
	 *配置tel验证拦截器，并设置拦截范围
	 * 
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截该范围的所有请求
//	    registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/rest/**");
	    //不包括该请求路径
//	    registry.addInterceptor(new UserSecurityInterceptor()).excludePathPatterns("/rest/**");

	}	
	
	/**
	 * 验证码
	 */

	@Bean
	public GenericManageableCaptchaService genericManageableCaptchaService(){
		int captchaStoreLoadBeforeGarbageCollection = 0;
		return new GenericManageableCaptchaService(genericCaptchaEngine(), 3, 100000, captchaStoreLoadBeforeGarbageCollection);
//		return  new GenericManageableCaptchaService(genericCaptchaEngine(), 180, 100000);
	}
	@Bean
	public GenericCaptchaEngine genericCaptchaEngine(){
		return new GenericCaptchaEngine(gimpyFactory());
	}
	@Bean
	public CaptchaFactory[] gimpyFactory(){
//		GimpyFactory gimpy =  new GimpyFactory(randomWordGenerator(),composedWordToImage());
		int minWordLength = 4;  
        int maxWordLength = 4;  
       int fontSize = 50;  
       int imageWidth = 180;  //180
        int imageHeight = 50;  //50
 
       // build filters  
       WaterFilter water = new WaterFilter();  
       water.setAmplitude(3d);//振幅    
       water.setAntialias(true);//锯齿或平滑    
       water.setPhase(20d);//相位    
       water.setWavelength(70d);//波长    
 
       //backgroundDeformation 背景图变形    
       ImageDeformation backDef = new ImageDeformationByFilters(  
               new ImageFilter[] {});  
        //textDeformation 字符图层转变形    
       ImageDeformation textDef = new ImageDeformationByFilters(  
               new ImageFilter[] {});  
       //finalDeformation  最终图片变形    
       ImageDeformation postDef = new ImageDeformationByFilters(  
               new ImageFilter[] { water });  
 
       //生成单词    
       WordGenerator dictionnaryWords = new RandomWordGenerator(  
               "0123456789");  
       // wordtoimage components  
       RandomRangeColorGenerator colors = new RandomRangeColorGenerator(  
               new int[] { 0, 150 }, new int[] { 0, 150 },  
               new int[] { 0, 150 }); 
 
       // 显示的字体设置，Arial,Tahoma,Verdana,Helvetica,宋体,黑体,幼圆  
       Font[] fonts = new Font[] { new Font("Arial", 0, fontSize),  
               new Font("Tahoma", 0, fontSize),  
               new Font("Verdana", 0, fontSize),  
               new Font("Helvetica", 0, fontSize),  
               new Font("宋体", 0, fontSize), new Font("黑体", 0, fontSize),  
               new Font("幼圆", 0, fontSize) };  
 
       // 设置字符以及干扰线  
       /* 
        * RandomRangeColorGenerator lineColors = new RandomRangeColorGenerator( 
        * new int[] { 150, 255 }, new int[] { 150, 255 }, new int[] { 150, 255 
        * }); 
        */  
 
       /* 
        * TextPaster randomPaster = new DecoratedRandomTextPaster(new 
        * Integer(4), new Integer(4), colors, true, new TextDecorator[] { new 
        * LineTextDecorator(new Integer(1), lineColors) }); 
        */  
 
       // 文字显示的个数  
       TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength,  
               maxWordLength, colors, true, new TextDecorator[] {});  
 
       //背景图生成    
       BackgroundGenerator back = new UniColorBackgroundGenerator(imageWidth,  
               imageHeight, Color.white);  
 
        //字体生成    
       FontGenerator shearedFont = new RandomFontGenerator(new Integer(30),  
               new Integer(0), fonts);
 
       // word2image 1  
       WordToImage word2image;  
       word2image = new DeformedComposedWordToImage(shearedFont, back,  
               randomPaster, backDef, textDef, postDef); 
		
		
		GimpyFactory gimpy =  new GimpyFactory(dictionnaryWords, word2image);
		GimpyFactory[] arr = new GimpyFactory[]{gimpy};
		return arr;
	}
}
