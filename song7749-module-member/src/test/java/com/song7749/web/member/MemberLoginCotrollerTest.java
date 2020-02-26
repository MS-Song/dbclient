package com.song7749.web.member;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
public class MemberLoginCotrollerTest { 

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

    @Autowired
    private WebApplicationContext ctx;
	
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
	
	@BeforeEach
	public void setup(){
		// Mock MVC 설정 추가
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
        		.addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

		// 테스트를 위한 회원 등록
        MemberVo vo = memberManager.findMember(dto.getLoginId());
		if(null==vo) {
			vo = memberManager.addMemeber(dto);			
		}

		// 관리자 권한 부여
		if(!AuthType.ADMIN.equals(vo.getAuthType())) {
			memberManager.modifyMember(new MemberModifyByAdminDto(vo.getId(), AuthType.ADMIN));	
		}
		
	}

	@Test
	@Order(1)
	@DisplayName("Member Login Controller 로그인 기능 이메일 주소 실패 테스트")
	public void testDoLogin_fail_login_email() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", "song7749")
				.param("password", dto.getPassword())
				;

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(400));
		 assertThat(responseObject.get("contents"), nullValue());
		 assertThat(responseObject.get("rowCount"), nullValue());
		 assertThat(responseObject.get("message"), equalTo("loginId 의 Email 은(는) 이메일 주소가 유효하지 않습니다."));

	}

	@Test
	@Order(2)
	@DisplayName("Member Login Controller 로그인 기능 틀린 패스워드 실패 테스트")
	public void testDoLogin_fail_wrong_password() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", "1234")
				;

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(400));
		 assertThat(responseObject.get("contents"), nullValue());
		 assertThat(responseObject.get("rowCount"), nullValue());
		 assertThat(responseObject.get("message"), equalTo("password 의 Size 은(는) 반드시 최소값 8과(와) 최대값 20 사이의 크기이어야 합니다."));

	}

	@Test
	@Order(3)
	@DisplayName("Member Login Controller 로그인 성공 테스트")
	public void testDoLogin() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String cipher = result.getResponse().getCookie("cipher").getValue();
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		logger.trace(format("{}", "Login info"),responseObject);

		// then
		assertThat(responseObject.get("httpStatus"), equalTo(200));
		assertThat(responseObject.get("message"), notNullValue());
		assertThat(cipher,notNullValue());

	}

	@Test
	@Order(4)
	@DisplayName("Member Login Controller 로그 아웃 성공")
	public void testDoLogout() throws Exception {
		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();

		// when
		drb=post("/member/doLogout").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA);

		// 로그인 cookie 정보 추가
		drb.cookie(new Cookie("cipher", result.getResponse().getCookie("cipher").getValue()));

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// then
		assertThat(result.getResponse().getCookie("cipher").getValue(), equalTo(""));
	}

	@Test
	@Order(5)
	@DisplayName("Member Login Controller 로그인 성공 후 정보 조회")
	public void testGetLogin() throws Exception {
		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();

		// when
		drb=get("/member/getLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA);

		// 로그인 cookie 정보 추가
		drb.cookie(new Cookie("cipher", result.getResponse().getCookie("cipher").getValue()));

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// then
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		logger.trace(format("{}", "Login Member Info"),responseObject);

		// then
		assertThat(responseObject.get("httpStatus"), equalTo(200));
		assertThat(responseObject.get("contents"), notNullValue());
	}
}