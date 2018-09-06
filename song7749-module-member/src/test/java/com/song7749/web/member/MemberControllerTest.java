package com.song7749.web.member;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.web.ControllerTest;

@SuppressWarnings("unchecked")
public class MemberControllerTest extends ControllerTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Autowired
	MemberManager memberManager;

	// 테스트를 위한 회원 등록
	MemberAddDto dto = new MemberAddDto(
			"song1234@gmail.com"
			, "123456789"
			, "passwordQuestion"
			, "passwordAnswer"
			, "teamName"
			, "name"
			, "123-123-1234");

	MemberVo vo;
	Cookie cookie;

	@Before
	public void setup() throws Exception{
		// 테스트를 위한 회원 등록
		vo = memberManager.addMemeber(dto);

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
	public void testAddMember() throws Exception {
		// give
		drb=post("/member/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", "song7749@gmail.com")
				.param("password", dto.getPassword())
				.param("passwordQuestion", dto.getPasswordQuestion())
				.param("passwordAnswer", dto.getPasswordAnswer())
				.param("teamName", dto.getTeamName())
				.param("name", dto.getName())
				;

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("contents"), notNullValue());
		 assertThat(responseObject.get("rowCount"), notNullValue());
		 assertThat(responseObject.get("message"), equalTo("회원 등록이 완료되었습니다."));

	}

	@Test
	public void testModifyMember() throws Exception {
		// give
		MemberModifyDto mmbaDto = new MemberModifyByAdminDto(
				vo.getId(),
				AuthType.NORMAL);
		memberManager.modifyMember(mmbaDto);

		drb=put("/member/modify").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("id", vo.getId().toString())
				.param("changeCertificationKey", "true")
				.param("passwordQuestion", dto.getPasswordQuestion())
				.param("passwordAnswer", dto.getPasswordAnswer())
				.param("password", "")
				.param("teamName", "")
				.param("name", "홍길동")
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
		 assertThat(responseObject.get("contents"), notNullValue());
		 assertThat(responseObject.get("rowCount"), notNullValue());
		 assertThat(responseObject.get("message"), equalTo("정보 수정이 완료되었습니다."));

	}


	@Test
	public void testModifyMemberByAdmin() throws Exception {
		// give

		//관리자만 변경 가능함으로 관라지로 업데이트 처리
		MemberModifyDto mmbaDto = new MemberModifyByAdminDto(
				vo.getId(),
				AuthType.ADMIN);
		memberManager.modifyMember(mmbaDto);

		drb=put("/member/modifyByAdmin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("id", vo.getId().toString())
				.param("changeCertificationKey", "true")
				.param("passwordQuestion", dto.getPasswordQuestion())
				.param("passwordAnswer", dto.getPasswordAnswer())
				.param("teamName", dto.getTeamName())
				.param("name", "홍길동")
				.param("authType", "NORMAL")
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
		 assertThat(responseObject.get("contents"), notNullValue());
		 assertThat(responseObject.get("rowCount"), notNullValue());
		 assertThat(responseObject.get("message"), equalTo("정보 수정이 완료 되었습니다."));
		 assertThat(((LinkedHashMap<String,String>)responseObject.get("contents")).get("authType")
				 , equalTo("NORMAL"));

	}

	@Test
	public void testRenewApiKeyHttpServletRequestHttpServletResponseRenewApikeyDto() throws Exception {
		// give
		// 권한 있어야  변경 가능함으로 관라지로 업데이트 처리
		MemberModifyDto mmbaDto = new MemberModifyByAdminDto(
				vo.getId(),
				AuthType.NORMAL);
		memberManager.modifyMember(mmbaDto);

		drb=put("/member/renewApiKey").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
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
		 assertThat(responseObject.get("message"), equalTo("apikey가 갱신 되었습니다."));
		 assertThat(responseObject.get("contents"), notNullValue());
	}
}