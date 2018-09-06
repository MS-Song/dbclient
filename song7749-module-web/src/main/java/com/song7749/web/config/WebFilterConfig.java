package com.song7749.web.config;

import org.springframework.boot.web.servlet.filter.OrderedHttpPutFormContentFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;

@Configuration
public class WebFilterConfig {
	/**
	 * PUT Method 를  request param 으로 받기 위한 설정
	 * @return
	 */
	@Bean
	public HttpPutFormContentFilter httpPutFormFilter() {
		return new HttpPutFormContentFilter();
	}

	/**
	 * PUT Method 를  request param 으로 받기 위한 설정
	 * @return
	 */
	@Bean
	public OrderedHttpPutFormContentFilter orderedHttpPutFormContentFilter() {
		return new OrderedHttpPutFormContentFilter();
	}

}
