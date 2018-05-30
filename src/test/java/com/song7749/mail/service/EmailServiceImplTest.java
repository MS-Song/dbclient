package com.song7749.mail.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
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
import com.song7749.mail.value.MailMessageVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.mail"})
public class EmailServiceImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EmailService emailService;

	@Autowired
	MailConfigRepository mailConfigRepository;

	/**
	 * fixture
	 */
	MailConfig mailConfig = new MailConfig(
			"smtp.homeplusnet.co.kr",
			25, // TLS 587 // SSL 465
			null,
			null);
	@Before
	public void setup() {

		mailConfig.setAuth(false);
		mailConfig.setEnableSSL(false);
		mailConfig.setStarttls(false);
		mailConfig.setDebug(true);
		mailConfigRepository.saveAndFlush(mailConfig);
	}

	@Test
	public void testSendMessage() throws Exception {
		// give
		MailMessageVo vo = new MailMessageVo(
				"song7749@homeplus.co.kr",
				new ArrayList<String>(Arrays.asList("song7749@homeplus.co.kr")),
				new ArrayList<String>(Arrays.asList("song7749@homeplus.co.kr")),
				new ArrayList<String>(Arrays.asList("song7749@homeplus.co.kr")),
				"이메일 테스트 코드",
				"<html><span>테스트 이메일 입니다.</span></html>",
				null);

//		logger.trace(format("{}", "host"),emailService.getMailSender().getHost());
//		logger.trace(format("{}", "port"),emailService.getMailSender().getPort());
//		logger.trace(format("{}", "Protocol"),emailService.getMailSender().getProtocol());
//		logger.trace(format("{}", "JavaMailProperties"),emailService.getMailSender().getJavaMailProperties());

		emailService.getMailSender(true).testConnection();

		// when
//		emailService.sendMessage(vo);
		// then
//		Thread.sleep(10000);
	}
}
