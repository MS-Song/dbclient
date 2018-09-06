package com.song7749.member.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.song7749.member.value.LoginAuthVo;

@RequestScope
@Component("loginSession")
public class LoginSession {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ModelMapper mapper;

	private LoginAuthVo lav;

	public LoginSession() {}

	public void setSesseion(LoginAuthVo lav) {
		this.lav=lav;
	}

	public boolean isLogin() {
		return null!=lav;
	}

	public LoginAuthVo getLogin() {
		return isLogin() ? lav : null;
	}
}