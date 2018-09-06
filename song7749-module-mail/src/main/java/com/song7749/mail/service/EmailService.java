package com.song7749.mail.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.song7749.mail.value.MailMessageVo;

/**
 * <pre>
 * Class Name : EmailService.java
 * Description : email 전송 서비스
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 28.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 5. 28.
 */
public interface EmailService {

	/**
	 * 메일 객체 설정 및 조회
	 * @param isReset
	 * @return
	 */
	JavaMailSenderImpl getMailSender(boolean isReset);
	/**
	 *  메일 메세지 전송
	 * @param vo
	 * @throws MessagingException
	 */
	void sendMessage(MailMessageVo vo) throws MessagingException;
}
