package com.song7749.common.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TimeZoneConfig {
	/**
	 * 후 처리 정의
	 */
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}