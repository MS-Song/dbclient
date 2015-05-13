package com.song7749.app.dbclient.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.song7749.util.filter.XSSFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = WebContextLoader.class, locations = {
		"classpath*:spring/application-context.xml",
		"classpath*:spring/servlet-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberControllerTest {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	MockHttpServletRequestBuilder drb;

	MvcResult result;

	Map<String, Object> responseObject;

	List<Object> contentList;

	// login 을 위한 cookie 정보
	Cookie cookies;

	@Before
	public void setUp() throws ClassNotFoundException, InvalidPropertiesFormatException, IOException {
		mockMvc=MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(new XSSFilter(), "/*")
				.addFilter(new SiteMeshFilter(), "/*.html")
				.build();
	}

	@Test
	public void testAddMember() throws Exception {
		drb = post("/member/add.json")
				.param("id","song7749")
				.param("email","song7749@gmail.com")
				.param("password","12345678")
				.param("passwordQuestion","1234")
				.param("passwordAnswer","1234")
				.param("authType","ADMIN")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("회원 가입이 완료되었습니다."));

	}

	@Test
	public void testModifyMember() throws Exception {
		drb = put("/member/modify.json")
				.param("id","root")
				.param("email","song7749@gmail.com")
				.param("password","12345678")
				.param("passwordQuestion","5678")
				.param("passwordAnswer","5678")
				.param("authType","ADMIN")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("회원 수정이 완료되었습니다."));

	}

	@Test
	public void testListMember() throws Exception {
		drb = get("/member/list.json")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
	}

	@Test
	public void testRemoveMember() throws Exception {
		drb = delete("/member/remove.json")
				.param("id","root")
				;

		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 						notNullValue());
		assertThat((Integer)responseObject.get("status"),				is(200));
		assertThat(responseObject.get("result"), 						notNullValue());
		assertThat((String)((Map)responseObject.get("result")).get("message"),	is("회원 정보가 삭제되었습니다."));
	}
}
