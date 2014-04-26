package com.song7749.app.dbclient.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.util.HashMap;
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
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.server.setup.MockMvcBuilders;
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
public class DatabaseControllerTest {

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
	public void setUp() throws ClassNotFoundException {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(wac)
				.addFilter(new XSSFilter(), "/*")
				.addFilter(new SiteMeshFilter(), "/*.html").build();
	}

	@Test
	public void testGetServerList() throws Exception {
		drb = get("/database/serverList.json")
//				.param("","")
				;
		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 			notNullValue());
		assertThat((Integer)responseObject.get("status"),	is(200));
		assertThat(responseObject.get("result"), 			notNullValue());

	}

	@Test
	public void testGetSchemaList() throws Exception {
		drb = get("/database/schemaList.json")
				.param("server","local-database")
				;
		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 			notNullValue());
		assertThat((Integer)responseObject.get("status"),	is(200));
		assertThat(responseObject.get("result"), 			notNullValue());
	}

	@Test
	public void testSaveDatabases() throws Exception {
		drb = post("/database/saveDatabases.json")
				.param("serverInfoSeq[]","1,")
				.param("host[]","local-database,local-database")
				.param("schemaName[]","dbBilling,eldanawa")
				.param("account[]","root,root")
				.param("password[]","1234,1234")
				.param("driver[]","mysql,mysql")
				.param("charset[]","utf-8,utf-8")
				.param("port[]","3306,3306")
				;
		// 로그인 cookie 정보 추가
		drb.cookie(cookies);

		result = mockMvc.perform(drb)
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn()
			;

		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

		assertThat(responseObject.get("status"), 			notNullValue());
		assertThat((Integer)responseObject.get("status"),	is(200));
		assertThat(responseObject.get("result"), 			notNullValue());
	}
}