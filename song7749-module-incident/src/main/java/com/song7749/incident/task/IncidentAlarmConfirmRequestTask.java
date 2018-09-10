package com.song7749.incident.task;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.song7749.dbclient.service.DBclientManager;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.mail.service.EmailService;
import com.song7749.mail.value.MailMessageVo;
import com.song7749.member.domain.Member;

/**
 * <pre>
 * Class Name : IncidentAlarmConfirmRequestTask.java
 * Description : 신규 알람 등록 또는 기존 알람을 수정 한 후에 승인 요청 버튼을 클릭하면
 * 승인 요청 메일이 관리자 권한이 있는 사용자 전체에게 발송됨.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  2018. 9. 10.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 9. 10.
 */
public class IncidentAlarmConfirmRequestTask implements Runnable {

	Logger logger = LoggerFactory.getLogger(getClass());

	private DBclientManager dbClientManager;

	private IncidentAlarm incidentAlarm;

	private List<Member> sendMemberList;

	private EmailService emailService;

	private Environment environment;

	ModelMapper mapper;

	public IncidentAlarmConfirmRequestTask(Environment environment, DBclientManager dbClientManager, IncidentAlarm incidentAlarm,
			List<Member> sendMemberList, EmailService emailService, ModelMapper mapper) {

		this.environment = environment;
		this.dbClientManager = dbClientManager;
		this.incidentAlarm = incidentAlarm;
		this.emailService = emailService;
		this.sendMemberList = sendMemberList;
		this.mapper = mapper;
	}

	@Override
	public void run() {
		// Task Start
		logger.trace(format("{}", "Confirm Request TASK RUN START"),incidentAlarm.getId());

		// 서버 주소 확인
		logger.trace(format("{}:{}", "Confirm Request Server Info")
				,environment.getProperty("java.rmi.server.hostname"),environment.getProperty("local.server.port"));

		String serverAddress = environment.getProperty("java.rmi.server.hostname") +
							":" + environment.getProperty("local.server.port");

		// 메일 제목
		String subject = "[메일알람 승인요청] " + incidentAlarm.getSubject();

		// 메세지 생성
		StringBuffer sendMessageBuffer = new StringBuffer();
		sendMessageBuffer.append("메일 알람 승인 요청 메일 입니다.<br /><br />");
		sendMessageBuffer.append("메일 제목 : ");
		sendMessageBuffer.append(incidentAlarm.getSubject());
		sendMessageBuffer.append("<br/>");
		sendMessageBuffer.append("작성자 : [");
		sendMessageBuffer.append(incidentAlarm.getResistMember().getTeamName());
		sendMessageBuffer.append("] ");
		sendMessageBuffer.append(incidentAlarm.getResistMember().getName());
		sendMessageBuffer.append("<br/>");
		sendMessageBuffer.append("<br/>");
		sendMessageBuffer.append("승인을 하시려면 <a href=\"http://" + serverAddress + "/static/incident.html?id=" + incidentAlarm.getId() + "\" >여기</a> 를 클릭해 주세요");

		// 수신자
		List<String> to = new ArrayList<String>();
		for(Member m : sendMemberList) {
			// root 계정은 올바른 메일 주소가 아님으로 제외 시킨다.
			if(!"root@test.com".equals(m.getLoginId())) {
				to.add(m.getLoginId());
			}
		}

		MailMessageVo vo = new MailMessageVo(
				incidentAlarm.getResistMember().getLoginId()
				, to
				, null
				, null
				, subject
				, sendMessageBuffer.toString()
				, null);

		// 메일 전송 시작
		try {
			emailService.sendMessage(vo);
			// 메일을 전송 한다.
			logger.trace(format("{}", "Confirm Request TASK send mail end"),incidentAlarm.getId());
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
	}
}
