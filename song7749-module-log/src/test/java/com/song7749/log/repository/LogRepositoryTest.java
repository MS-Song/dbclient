package com.song7749.log.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.log.domain.LogLogin;
import com.song7749.log.domain.LogQuery;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LogRepository logRepository;

	@Test
	public void testSaveLogLogin() {
		// give
		LogLogin ll = new LogLogin();
		ll.setIp("111.111.111.111");
		ll.setLoginId("song7749@test.com");
		ll.setCipher("12314142341231");

		// when
		logRepository.saveAndFlush(ll);
		// then
		assertThat(ll.getId(), notNullValue());
	}

	@Test
	public void testSaveLogQuery() {
		//give
		LogQuery lq = new LogQuery();
		lq.setIp("111.111.111.111");
		lq.setDatabaseId(1L);
		lq.setAccount("song7749");
		lq.setHost("11.11.11.11");
		lq.setHostAlias("테스트 서버");
		lq.setLoginId("song7749");
		lq.setQuery("select * from dual");
		lq.setSchemaName("song7749");
		//when
		logRepository.saveAndFlush(lq);
		//then
		assertThat(lq.getId(), notNullValue());
	}

}
