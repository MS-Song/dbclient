package com.song7749.web.config;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

	private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("text/yaml");
	private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("text/yml");

	@Autowired
	@Qualifier("yamlObjectMapper")
	private ObjectMapper yamlObjectMapper;

	/**
	 * ContentNegotiation 에 yml, yaml 관련 설정을 추가 한다.
	 * *.yml 로 호출 시에 yml 을 return 한다.
	 */
	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
	    configurer
	      .favorParameter(false)
	      .ignoreAcceptHeader(true)
	      .defaultContentType(MediaType.APPLICATION_JSON)
	      .mediaType(MediaType.APPLICATION_JSON.getSubtype(), MediaType.APPLICATION_JSON)
	      .mediaType(MediaType.APPLICATION_XML.getSubtype(), MediaType.APPLICATION_XML)
	      .mediaType(MEDIA_TYPE_YML.getSubtype(), MEDIA_TYPE_YML)
	      .mediaType(MEDIA_TYPE_YAML.getSubtype(), MEDIA_TYPE_YAML);
	}

	/**
	 * 객체를 yml 로 변경 할 message convert 를 생성 한다.
	 */
	@Override
	public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		final MappingJackson2HttpMessageConverter yamlConverter = new MappingJackson2HttpMessageConverter(yamlObjectMapper);
		yamlConverter.setSupportedMediaTypes(
			Arrays.asList(MEDIA_TYPE_YML, MEDIA_TYPE_YAML)
		);
		converters.add(yamlConverter);
	}

	/**
	 * Interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logMessageInterceptorHandle)
			.addPathPatterns("/**")
			.excludePathPatterns("/webjars/**")
			.excludePathPatterns("/static/**")
			.excludePathPatterns("favicon.ico")
			.excludePathPatterns("index.html")
			.excludePathPatterns("swagger-ui.html")
			.excludePathPatterns("/swagger-resources/**")
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
		page.setMaxPageSize(200);							// 최대 200개 이상은 조회가 안되도록 제한 한다.
		page.setOneIndexedParameters(true);		// 1페이지가 첫번째 페이지가 되도록 처리 한다. page+1 을 자동으로 처리 함

		
		SortHandlerMethodArgumentResolver sort = new SortHandlerMethodArgumentResolver();
        page.setOneIndexedParameters(true);
        argumentResolvers.add(page);
        argumentResolvers.add(sort);
        super.addArgumentResolvers(argumentResolvers);
    }

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION");
	}
}