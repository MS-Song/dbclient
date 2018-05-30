package com.song7749.mail.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.mail.domain.MailConfig;
import com.song7749.mail.repository.MailConfigRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.mail"})
public class MailConfigRepositoryTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MailConfigRepository mailConfigRepository;

	@Test
	public void testSaveAndRead() {
		// give
		MailConfig mailConfig = new MailConfig(
				"mail.gmail.co.kr",
				465, // TLS 587 // SSL 465 // NO 25
				"song7749",
				"12312313");
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