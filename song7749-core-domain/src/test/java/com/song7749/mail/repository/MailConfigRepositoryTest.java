package com.song7749.mail.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.mail.domain.MailConfig;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MailConfigRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MailConfigRepository mailConfigRepository;

	@Test
	public void testSaveAndRead() {
		// give
		MailConfig mailConfig = MailConfig.builder()
			.host("mail.test.co.kr")
			.port(465)// TLS 587 // SSL 465 // NO 25
			.username("song7749")
			.password("12312313")
			.build();

		// when
		mailConfigRepository.saveAndFlush(mailConfig);
		// then
		assertThat(mailConfig.getId(),notNullValue());
		// give
		// when
		List<MailConfig> list= mailConfigRepository.findAll();
		// then
		assertThat(list.size(),equalTo(1));
	}
}