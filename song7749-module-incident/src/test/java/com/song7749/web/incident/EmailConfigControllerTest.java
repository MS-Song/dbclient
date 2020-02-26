package com.song7749.web.incident;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.ModuleCommonApplicationTests;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberVo;


@SuppressWarnings("unchecked")
@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailConfigControllerTest {

	Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext ctx;
	
	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Autowired
	MemberManager memberManager;

	// 테스트를 위한 회원 등록
	MemberAddDto dto = new MemberAddDto(
			"song12345678@gmail.com",
			"123456789",
			"passwordQuestion",
			"passwordAnswer",
			"teamName",
			"name",
			"123-123-1234");

	MemberVo vo;
	Cookie cookie;

	@BeforeEach
	public void setup() throws Exception{
		// Mock MVC 설정 추가
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
        		.addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
		
		// 테스트를 위한 회원 등록
		MemberVo vo = memberManager.addMemeber(dto);
		// 관리자 권한 부여
		MemberModifyByAdminDto adminDto = new MemberModifyByAdminDto(vo.getId(), AuthType.ADMIN);
		memberManager.modifyMember(adminDto);

		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;
		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();
		// login cookie 생성
		cookie = new Cookie("cipher", result.getResponse().getCookie("cipher").getValue());

	}

	@Disabled
	@Test
	public void testMailTest() throws Exception {
		// give
		drb=post("/config/mail/test").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", "smtp.test.com")
				.param("port", "25")
				.param("auth", "false")
				.param("username", "")
				.param("password", "")
				.param("protocol", "smtp")
				.param("enableSSL", "false")
				.param("starttls", "false")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("contents"), nullValue());
		 assertThat(responseObject.get("rowCount"), nullValue());
		 assertThat(responseObject.get("message"), equalTo("메일 연결 테스트 완료!"));

	}

	@Test
	public void testMailSave() throws Exception {
		// give
		drb=post("/config/mail/save").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", "smtp.test.com")
				.param("port", "25")
				.param("auth", "false")
				.param("username", "")
				.param("password", "")
				.param("protocol", "smtp")
				.param("enableSSL", "false")
				.param("starttls", "false")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("rowCount"), equalTo(1));
		 assertThat(responseObject.get("message"), equalTo("메일 환경 정보가 저장되었습니다."));
	}

	@Test
	public void testMailFind() throws Exception {
		// give
		drb=get("/config/mail/find").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("rowCount"), equalTo(1));
	}

}
