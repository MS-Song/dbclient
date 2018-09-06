package com.song7749.incident.domain;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Component
public class InitIncidentConfigBean {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Transactional
	@PostConstruct
    public void init(){

	}
}
