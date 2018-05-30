package com.song7749.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.common.service.CommonConfigService;
import com.song7749.common.value.MailConfigDto;
import com.song7749.common.value.MailConfigVo;
import com.song7749.dbclient.annotation.Login;
import com.song7749.dbclient.type.AuthType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "공통 환경 관리 컨트롤러")
@RestController
@RequestMapping("/common/config")
public class CommonConfigController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CommonConfigService commonConfigService;

	@PostMapping("/mail/test")
	@ApiOperation(value = "메일 환경 테스트", response = MessageVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo mailTest(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MailConfigDto dto) {
		return commonConfigService.testMailConfig(dto);
	}

	@PostMapping("/mail/save")
	@ApiOperation(value = "메일 환경 저장", response = MailConfigVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo mailSave(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MailConfigDto dto) {
		return new MessageVo(HttpServletResponse.SC_OK, 1, commonConfigService.saveMailConfig(dto), "메일 환경 정보가 저장되었습니다.");
	}

	@GetMapping("/mail/find")
	@ApiOperation(value = "메일 환경 조회", response = MailConfigVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo mailFind(HttpServletRequest request, HttpServletResponse response) {
		return new MessageVo(HttpServletResponse.SC_OK, 1, commonConfigService.findMailConfig());
	}
}