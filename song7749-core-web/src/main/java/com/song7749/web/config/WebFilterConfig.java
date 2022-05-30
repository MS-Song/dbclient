package com.song7749.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter ;

@Configuration
public class WebFilterConfig {
	/**
	 * PUT Method 를  request param 으로 받기 위한 설정
	 * @return
	 */
	@Bean
	public FormContentFilter httpPutFormFilter() {
		return new FormContentFilter();
	}
}