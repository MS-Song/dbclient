package com.song7749.mail.service;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.common.MessageVo;
import com.song7749.mail.type.EmailProtocol;
import com.song7749.mail.value.MailConfigDto;

public class EmailConfigServiceImplTest extends UnitTest {
	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	EmailConfigService emailConfigService;

	@Ignore
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

	@Ignore
	@Test
	public void testSaveMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Ignore
	@Test
	public void testFindMailConfig() throws Exception {
		throw new RuntimeException("not yet implemented");
	}
}