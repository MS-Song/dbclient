package com.song7749.dbclient.web;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
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
import com.song7749.dbclient.drs.service.DatabaseManager;
import com.song7749.dbclient.drs.service.MemberManager;
import com.song7749.dbclient.drs.type.AuthType;
import com.song7749.dbclient.drs.type.Charset;
import com.song7749.dbclient.drs.type.DatabaseDriver;
import com.song7749.dbclient.drs.type.MemberModifyByAdminDto;
import com.song7749.dbclient.drs.value.DatabaseAddDto;
import com.song7749.dbclient.drs.value.DatabaseVo;
import com.song7749.dbclient.drs.value.MemberAddDto;
import com.song7749.dbclient.drs.value.MemberVo;

@SuppressWarnings("unchecked")
public class DatabaseControllerTest extends ControllerTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	MemberManager memberManager;

	// 테스트를 위한 회원 등록
	MemberAddDto dto = new MemberAddDto(
			"song1234@gmail.com"
			, "123456789"
			, "passwordQuestion"
			, "passwordAnswer"
			, "teamName"
			, "name");

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

	@Before
	public void setup() throws Exception{
		// 테스트를 위한 회원 등록
		vo = memberManager.addMemeber(dto);
		MemberModifyByAdminDto modifyDto = new MemberModifyByAdminDto();
		modifyDto.setId(vo.getId());
		modifyDto.setAuthType(AuthType.ADMIN);
		memberManager.modifyMember(modifyDto);

		// 테스트를 위한 databae 등록
		dv = databaseManager.addDatabase(dad);

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
	public void testDatabaseAdd_No_Login() throws Exception {

		// give
		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", dad.getHost())
				.param("hostAlias", dad.getHostAlias())
				.param("schemaName", dad.getSchemaName())
				.param("account", dad.getAccount())
				.param("password", dad.getPassword())
				.param("port", dad.getPort())
				.param("driver", dad.getDriver().toString())
				.param("charset", dad.getCharset().toString())
				;

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 logger.trace(format("{}", "response log"),responseObject);
		 assertThat(responseObject.get("httpStatus"), equalTo(405));
		 assertThat(responseObject.get("message"), equalTo("로그인이 필요한 서비스입니다. 로그인 해주시기 바랍니다."));
	}

	@Test
	public void testDatabaseAdd() throws Exception {

		// give
		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", dad.getHost())
				.param("hostAlias", dad.getHostAlias())
				.param("schemaName", dad.getSchemaName())
				.param("account", dad.getAccount())
				.param("password", dad.getPassword())
				.param("port", dad.getPort())
				.param("driver", dad.getDriver().toString())
				.param("charset", dad.getCharset().toString())
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
		 assertThat(responseObject.get("rowCount"), equalTo(1));
		 assertThat(responseObject.get("message"), equalTo("Database 정보가 저장 되었습니다."));

	}


	@Test
	public void testModifyDatabase() throws Exception {

		// give
		drb=put("/database/modify").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("id", dv.getId().toString())
				.param("host", "10.20.30.40")
				.param("hostAlias", "testHostAliaseModify")
				.param("schemaName", "testSchemaNameMoidfy")
				.param("account", dv.getAccount())
				.param("password", "1234asdfg")
				.param("port", dv.getPort())
				.param("driver", dv.getDriver().toString())
				.param("charset", dv.getCharset().toString())
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
		 assertThat(responseObject.get("rowCount"), equalTo(1));
		 assertThat(responseObject.get("message"), equalTo("Database 정보가 저장 되었습니다."));

	}


	@Test
	public void testDeleteDatabase() throws Exception {

		// give
		drb=put("/database/modify").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("id", dv.getId().toString())
				.param("host", "10.20.30.40")
				.param("hostAlias", "testHostAliaseModify")
				.param("schemaName", "testSchemaNameMoidfy")
				.param("account", dv.getAccount())
				.param("password", "1234asdfg")
				.param("port", dv.getPort())
				.param("driver", dv.getDriver().toString())
				.param("charset", dv.getCharset().toString())
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
		 assertThat(responseObject.get("rowCount"), equalTo(1));
		 assertThat(responseObject.get("message"), equalTo("Database 정보가 저장 되었습니다."));
	}


	@Test
	public void testGetList() throws Exception {

		// give
		drb=get("/database/list").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
//				.param("charset", dv.getCharset().toString())
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
		 assertThat(responseObject.get("rowCount"), equalTo(4));
	}
}