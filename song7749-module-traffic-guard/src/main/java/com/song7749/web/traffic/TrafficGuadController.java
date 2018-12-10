package com.song7749.web.traffic;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.common.MessageVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="트래픽 제어 환경설정 ")
@RestController
@RequestMapping("/traffice")
public class TrafficGuadController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginSession session;

	@ApiOperation(value = "트래픽 제어 환경 정보를 등록"
			,notes = "트래픽 제어 환경 정보를 등록하는 기능을 수행한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PostMapping("/add")
	public MessageVo addTrafficGuadConfig(
			HttpServletRequest request,
			HttpServletResponse response
			) throws UnsupportedEncodingException{

		return new MessageVo();
	}
}