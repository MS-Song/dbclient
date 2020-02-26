package com.song7749.dbclient.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseRepository databaseRepository;

	@BeforeEach
	@DisplayName("기초 데이터 입력")
	public void setup() throws Exception {
		// 저장 테스트만 진행 함
	}
	
	@Test
	@Order(1)
	@DisplayName("데이터베이스 입력 실패 테스트")
	public void testSaveGivenNull() {
		assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			databaseRepository.saveAndFlush(null);
		});

	}

	@Test
	@Order(1)
	@DisplayName("데이터베이스 입력/수정 테스트")
	public void testSave() {
		//give
		Database ds = new Database("10.10.10.10"
				, "test server"
				, "dbTest"
				, "song7749"
				, "12345678"
				, DatabaseDriver.ORACLE
				, Charset.UTF8
				, "3333"
				,"");
		//when
		ds = databaseRepository.saveAndFlush(ds);
		//than
		assertThat(ds.getId(),notNullValue());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//give
		ds.setHost("10.200.200.200");
		ds.setCharset(Charset.EUCKR);
		//when
		Database dsi = databaseRepository.saveAndFlush(ds);
		//then
		assertThat(dsi.getId(),equalTo(ds.getId()));
		assertThat(dsi.getHost(),equalTo(ds.getHost()));
		assertThat(dsi.getCharset(),equalTo(ds.getCharset()));

		databaseRepository.deleteById(dsi.getId());
		databaseRepository.flush();

		assertFalse(databaseRepository.existsById(dsi.getId()));
	}
}