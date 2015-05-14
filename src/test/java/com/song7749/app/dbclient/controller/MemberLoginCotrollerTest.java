package com.song7749.app.dbclient.controller;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.song7749.app.dbclient.loader.WebContextLoader;
import com.song7749.util.crypto.CryptoAES;
import com.song7749.util.filter.XSSFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = WebContextLoader.class, locations = {
		"classpath*:spring/application-context.xml",
		"classpath*:spring/servlet-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberLoginCotrollerTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	MockHttpServletRequestBuilder drb;

	MvcResult result;

	Map<String, Object> responseObject;

	List<Object> contentList;

	// login 을 위한 cookie 정보
	Cookie cookie;

	@Before
	public void setUp() throws ClassNotFoundException, InvalidPropertiesFormatException, IOException {
		mockMvc=MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(new XSSFilter(), "/*")
				.addFilter(new SiteMeshFilter(), "/*.html")
				.build();
	}


	@Test
	public void testDoLogin_로그인실패() throws Exception {
		drb = post("/member/doLogin.json")
				.param("id","song7749")
				.param("password","5678")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;

		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("ID 또는 비밀번호가 맞지 않습니다."));
	}

	@Test
	public void testDoLogin_정상로그인() throws Exception {
		drb = post("/member/doLogin.json")
				.param("id","root")
				.param("password","12345678")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookie);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;

		//
		logger.trace(format("{}",""),result.getResponse().getCookie("cipher").getValue());
		assertThat(result.getResponse().getCookie("cipher").getValue(),is(CryptoAES.encrypt("root")));

		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("로그인 처리가 완료되었습니다."));

	}

	@Test
	public void testDoLogout() throws Exception {
		drb = post("/member/doLogout.json")
				;

		// 로그인 cookie 정보 추가
		// 로그인 cookie 정보 추가
		cookie = new Cookie("cipher", CryptoAES.encrypt("root"));
		drb.cookie(cookie);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;

		//
		logger.trace(format("{}",""),result.getResponse().getCookie("cipher"));
		assertThat(result.getResponse().getCookie("cipher"),nullValue());

	}
	@Test
	public void testGetLogin_비로그인상태() throws Exception {
		drb = get("/member/getLogin.json")
				;

		drb.cookie(cookie);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;

		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	nullValue());
	}

	@Ignore
	@Test
	public void testGetLogin_로그인상태() throws Exception {
		drb = get("/member/getLogin.json")
				;

		// 로그인 cookie 정보 추가
		cookie = new Cookie("cipher", CryptoAES.encrypt("root"));
		drb.cookie(cookie);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("root"));
	}
}