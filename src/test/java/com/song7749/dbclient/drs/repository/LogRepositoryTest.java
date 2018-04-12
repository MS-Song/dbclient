package com.song7749.dbclient.drs.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.dbclient.drs.domain.LogLogin;
import com.song7749.dbclient.drs.domain.LogQuery;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient.drs"})
public class LogRepositoryTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LogRepository logRepository;

	@Test
	public void testSaveLogLogin() {
		// give
		LogLogin ll = new LogLogin();
		ll.setIp("111.111.111.111");
		ll.setLoginId("song7749");
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
