package com.song7749.mail.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import com.song7749.mail.domain.MailConfig;
import com.song7749.mail.repository.MailConfigRepository;
import com.song7749.mail.value.MailMessageVo;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@BeforeEach
	public void setup() {

		mailConfig.setAuth(false);
		mailConfig.setEnableSSL(false);
		mailConfig.setStarttls(false);
		mailConfig.setDebug(true);
		mailConfigRepository.saveAndFlush(mailConfig);
	}

	@Disabled
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

		emailService.getMailSender(true).testConnection();

		logger.trace(format("{}", "host"),emailService.getMailSender(false).getHost());
		logger.trace(format("{}", "port"),emailService.getMailSender(false).getPort());
		logger.trace(format("{}", "Protocol"),emailService.getMailSender(false).getProtocol());
		logger.trace(format("{}", "JavaMailProperties"),emailService.getMailSender(false).getJavaMailProperties());

		// when
		emailService.sendMessage(vo);
		// then
		Thread.sleep(5000);
	}
}
