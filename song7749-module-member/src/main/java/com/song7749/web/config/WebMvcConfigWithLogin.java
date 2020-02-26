package com.song7749.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * <pre>
 * Class Name : LoginInterceptorConfig.java
 * Description : Spring MVC Interceptor Configure
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 14.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 14.
*/
@Configuration("webMvcConfigWithLogin")
public class WebMvcConfigWithLogin extends WebMvcConfig   {

	@Autowired
	@Qualifier("logInInterceptor")
	private HandlerInterceptor logInInterceptor;

	/**
	 * Log Message Interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 부모의 기능 호출 후 자식 기능도 호출하여 2개 구성
		super.addInterceptors(registry);

		registry.addInterceptor(logInInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/webjars/**")
			.excludePathPatterns("/static/**")
			.excludePathPatterns("index.html")
			.excludePathPatterns("swagger-ui.html")
			;
	}
}