package com.song7749.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <pre>
 * Class Name : WebMvcConfig.java
 * Description : Spring MVC Interceptor Configure
 * 
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 14.		song7749@gmail.com		NEW
*  2020. 2. 26.		song7749@gmail.com		SpringBoot 2.1 이후 부터는 2개의 support bean 등록이 불가능하여 member 에서 
*  											override 한 객체를 사용하고, 본 객체는 골격만 유지하게 변경 함
*  
*  
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 14.
*/
//@Configuration("webMvcConfig")
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Qualifier("logMessageInterceptorHandle")
	private HandlerInterceptor logMessageInterceptorHandle;
	/**
	 * Interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logMessageInterceptorHandle)
			.addPathPatterns("/**")
			.excludePathPatterns("/webjars/**")
			.excludePathPatterns("/static/**")
			.excludePathPatterns("index.html")
			.excludePathPatterns("swagger-ui.html")
			;
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		/**
		 * index page config
		 */
		registry.addResourceHandler("index.html")
	      	.addResourceLocations("classpath:/static/");

		registry.addResourceHandler("favicon.ico")
      	.addResourceLocations("classpath:/static/");

		registry.addResourceHandler("/static/**")
	      	.addResourceLocations("classpath:/static/").setCachePeriod(0);

	    /**
	     * Swagger UI config
	     */
		registry.addResourceHandler("swagger-ui.html")
	      	.addResourceLocations("classpath:/META-INF/resources/");

		/**
		 * Swagger UI data config
		 */
	    registry.addResourceHandler("/webjars/**")
	      	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Pageable and Sort resolver setting
	 */
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver page = new PageableHandlerMethodArgumentResolver();
        SortHandlerMethodArgumentResolver sort = new SortHandlerMethodArgumentResolver();
        page.setOneIndexedParameters(true);
        argumentResolvers.add(page);
        argumentResolvers.add(sort);
        super.addArgumentResolvers(argumentResolvers);
    }
}