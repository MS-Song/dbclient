package com.song7749.mail.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.common.base.MessageVo;
import com.song7749.mail.type.EmailProtocol;
import com.song7749.mail.value.MailConfigDto;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailConfigServiceImplTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	EmailConfigService emailConfigService;

	@Tag("exclude")
	@Disabled
	@Test
	public void testTestMailConfig() throws Exception {
		// give
		MailConfigDto dto = new MailConfigDto(
			"mail.test.com",
			25,
			false,
			null,
			null,
			EmailProtocol.smtp,
			false,
			false);

		// when
		MessageVo vo = emailConfigService.testMailConfig(dto);

		// then
	}

	@Tag("exclude")
	@Disabled
	@Test
	public void testSaveMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Tag("exclude")
	@Disabled
	@Test
	public void testFindMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}
}