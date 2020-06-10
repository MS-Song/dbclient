package com.song7749.web.dbclient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
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
import com.song7749.dbclient.service.DatabaseManager;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;

@SuppressWarnings("unchecked")
@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/application.yml")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseMemberControllerTest {

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

	@Autowired
	DatabaseManager databaseManager;

	// 테스트를 위한 회원 등록
	MemberAddDto dto = new MemberAddDto(
			"song1234@gmail.com"
			, "123456789"
			, "passwordQuestion"
			, "passwordAnswer"
			, "teamName"
			, "name"
			, "123-123-1234");

	// 테스트를 위한 DB 등록
	DatabaseAddDto dad = new DatabaseAddDto(
			"testHost",
			"testHostAlias",
			"testSchemaName",
			"testAccount",
			"testPassword",
			DatabaseDriver.MYSQL,
			Charset.UTF8,
			"3306");

	MemberVo vo;
	DatabaseVo dv;
	Cookie cookie;

	@BeforeEach
	public void setup() throws Exception{
		// Mock MVC 설정 추가
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
        		.addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        
		// 테스트를 위한 회원 등록
		vo = memberManager.addMemeber(dto);
		// 테스트를 위한 databae 등록
		dv = databaseManager.addDatabase(dad);

		//관리자만 변경 가능함으로 관라지로 업데이트 처리
		MemberModifyDto mmbaDto = new MemberModifyByAdminDto(
				vo.getId(),
				AuthType.ADMIN);
		memberManager.modifyMember(mmbaDto);

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


	@Test
	public void testAddOrModifyMemberDatabaseByAdmin() throws Exception {
		drb=post("/member/addOrModifyMemberDatabaseByAdmin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("id", "")
				.param("memberId", vo.getId().toString())
				.param("databaseId", dv.getId().toString())
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("rowCount"), notNullValue());
		 assertThat(responseObject.get("message"), equalTo("정보 수정이 완료 되었습니다."));
		 assertThat(responseObject.get("contents"), notNullValue());

		 // 삭제 기능
		 // give

		 //when

		//then
	}
}