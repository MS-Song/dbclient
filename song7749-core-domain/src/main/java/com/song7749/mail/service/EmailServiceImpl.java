package com.song7749.mail.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.common.validate.Validate;
import com.song7749.mail.domain.MailConfig;
import com.song7749.mail.repository.MailConfigRepository;
import com.song7749.mail.value.MailMessageVo;

@Service
public class EmailServiceImpl implements EmailService {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 직접 호출 하지 않도록 주의 한다. Singleton 구현
	 */
	private JavaMailSenderImpl mailSender;

	@Autowired
	MailConfigRepository mailConfigRepository;

	@Override
	@Transactional(readOnly=true)
	public synchronized JavaMailSenderImpl getMailSender(boolean isReset) {
		if (null == mailSender || isReset) {
			logger.trace(format("{}", "Mail Config"),"new Mail Sender Config");
			// 환경 객체 로딩
			List<MailConfig> list = mailConfigRepository.findAll();
			// 환경 객체가 없을 경우
			if(list.size() == 0) {
				throw new IllegalArgumentException("메일 환경 설정이 없어 메일 전송이 불가능합니다.");
			} else {
				logger.trace(format("{}", "Mail Config Detect"),list.get(0).getHost());
			}
			// 환경 객체
			MailConfig config = list.get(0);
			// 메일 설정
			mailSender = new JavaMailSenderImpl();
		    mailSender.setHost(config.getHost());
		    mailSender.setPort(config.getPort());
		    mailSender.setProtocol(config.getProtocol().toString());
		    mailSender.setUsername(config.getUsername());
		    mailSender.setPassword(config.getPassword());
		    // 추가 설정
		    Properties props = mailSender.getJavaMailProperties();
		    props.put("mail.smtp.auth", config.getAuth());
	        props.put("mail.smtp.EnableSSL.enable",config.getEnableSSL());
		    props.put("mail.smtp.starttls.enable", config.getStarttls());
//		    props.put("mail.smtp.connectiontimeout", "5000");
//		    props.put("mail.smtp.timeout", "3000");
//		    props.put("mail.smtp.writetimeout", "5000");
		    props.put("mail.debug", config.getDebug());
		} else {
			logger.trace(format("{}", "Mail Config"),"return Mail Config");
		}
		return mailSender;
	}

	@Validate
	@Override
	public void sendMessage(MailMessageVo vo) throws MessagingException {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(vo.getFrom());
				helper.setTo(vo.getTo().toArray(new String[vo.getTo().size()]));
				// CC가 있을 경우
				if (null != vo.getCc() && vo.getCc().size() > 0) {
					helper.setCc(vo.getCc().toArray(new String[vo.getCc().size()]));
				}
				// BCC 가 있을 경우
				if (null != vo.getBcc() && vo.getBcc().size() > 0) {
					helper.setBcc(vo.getBcc().toArray(new String[vo.getBcc().size()]));
				}
				helper.setSubject(vo.getSubject());
				helper.setText(vo.getText(), true);

				if (null != vo.getFiles() && vo.getFiles().size() > 0) {
					for (String fileName : vo.getFiles()) {
						FileSystemResource file = new FileSystemResource(new File(fileName));
						helper.addAttachment(fileName, file);
					}
				}
			}
		};
		// 메일 전송
		getMailSender(false).send(preparator);
	}
}