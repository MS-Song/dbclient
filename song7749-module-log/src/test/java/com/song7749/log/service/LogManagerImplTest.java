package com.song7749.log.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.log.domain.Log;
import com.song7749.log.repository.LogRepository;
import com.song7749.log.value.LogIncidentAlaramAddDto;
import com.song7749.log.value.LogLoginAddDto;
import com.song7749.log.value.LogQueryAddDto;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LogManager logManager;

	@Autowired
	LogRepository logRepository;

	@Autowired
	ModelMapper mapper;

	@Test
	public void testAddLogLogin() throws Exception {
		// give
		LogLoginAddDto dto = new LogLoginAddDto("10.10.10.10"
				, "song7749@gmail.com"
				, "asdfasfasdfasdf");

		// when
		logManager.addLogLogin(dto);
		// then
		Thread.sleep(1000);
		List<Log> logList = logRepository.findAll();

		assertTrue(logList.size()>0);

	}

	@Test
	public void testAddQueryExecuteLog() throws Exception {
		// give
		LogQueryAddDto dto = new LogQueryAddDto("10.10.10.10"
				, "song7749@gmail.com"
				, 1L
				, "host"
				, "hostAlias"
				, "schemaName"
				, "account"
				, "query");

		// when
		logManager.addQueryExecuteLog(dto);
		// then
		Thread.sleep(1000);
		List<Log> logList = logRepository.findAll();
		assertTrue(logList.size()>0);
	}

	@Test
	public void testAddIncidentAlarmLog() throws Exception {
		// give
		LogIncidentAlaramAddDto dto = new LogIncidentAlaramAddDto(
				"10.10.10.10",
				1L,
				"{이전내용}",
				"{변경내용}") ;
		// when
		logManager.addIncidentAlarmLog(dto);
		// then
		Thread.sleep(1000);
		List<Log> logList = logRepository.findAll();
		assertTrue(logList.size()>0);
	}
}