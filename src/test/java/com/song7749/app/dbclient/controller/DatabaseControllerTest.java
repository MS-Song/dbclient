package com.song7749.app.dbclient.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
import java.util.Properties;

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
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
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

	/**
	 * 테스트 서버 관련 객체
	 */
	ServerInfo serverInfo;

	@Before
	public void setUp() throws ClassNotFoundException, InvalidPropertiesFormatException, IOException {
		mockMvc=MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(new XSSFilter(), "/*")
				.addFilter(new SiteMeshFilter(), "/*.html")
				.build();


		// 서버 인포 설정
		Properties prop = new Properties();
		prop.loadFromXML(ClassLoader.getSystemResource("properties/dbProperties.xml").openStream());
		serverInfo = new ServerInfo(prop.getProperty("dbClient.database.host")
				, prop.getProperty("dbClient.database.schemaName")
				, prop.getProperty("dbClient.database.username")
				, prop.getProperty("dbClient.database.password")
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");
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
				.param("server",serverInfo.getHost())
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
	public void testGetTableList() throws Exception {
		drb = get("/database/tableList.json")
				.param("server",serverInfo.getHost())
				.param("schema",serverInfo.getSchemaName())
				.param("account",serverInfo.getAccount())
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
				.param("serverInfoSeq[]","")
				.param("host[]",serverInfo.getHost())
				.param("schemaName[]",serverInfo.getSchemaName())
				.param("account[]",serverInfo.getAccount())
				.param("password[]",serverInfo.getPassword())
				.param("driver[]",serverInfo.getDriver().mysql.toString())
				.param("charset[]","UTF-8")
				.param("port[]","3306")
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


		drb = post("/database/saveDatabases.json")
				.param("serverInfoSeq[]","1,")
				.param("host[]",serverInfo.getHost(),serverInfo.getHost())
				.param("schemaName[]",serverInfo.getSchemaName(),serverInfo.getSchemaName())
				.param("account[]",serverInfo.getAccount(),serverInfo.getAccount())
				.param("password[]",serverInfo.getPassword(),serverInfo.getPassword())
				.param("driver[]",serverInfo.getDriver().mysql.toString(),serverInfo.getDriver().mysql.toString())
				.param("charset[]","UTF-8","UTF-8")
				.param("port[]","3306","3306")
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