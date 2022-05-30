package com.song7749.srcenter.task;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.song7749.common.base.YN;
import com.song7749.mail.service.EmailService;
import com.song7749.mail.value.MailMessageVo;
import com.song7749.member.domain.Member;
import com.song7749.srcenter.domain.SrDataRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * <pre>
 * Class Name : SrDataRequestConfirmRequestTask
 * Description : SR 데이터 Confirm 요청을 메일로 전송 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  15/01/2020		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 15/01/2020
 */
public class SrDataRequestConfirmRequestTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(getClass());

    private SrDataRequest srDataRequest;

    private List<Member> sendMemberList;

    private EmailService emailService;

    private Environment environment;

    private YN confirm;

    public SrDataRequestConfirmRequestTask(SrDataRequest srDataRequest, List<Member> sendMemberList, EmailService emailService, Environment environment, YN confirm) {
        this.srDataRequest = srDataRequest;
        this.sendMemberList = sendMemberList;
        this.emailService = emailService;
        this.environment = environment;
        this.confirm = confirm;
    }

    @Override
    public void run() {
        logger.trace(format("{}", "Confirm Request TASK RUN START"),srDataRequest.getId());

        // 서버 주소 확인
        logger.trace(format("{}:{}", "Confirm Request Server Info")
                ,environment.getProperty("java.rmi.server.hostname"),environment.getProperty("local.server.port"));

        // 서버 주소
        String serverAddress = environment.getProperty("java.rmi.server.hostname") +
                ":" + environment.getProperty("local.server.port");
        // 메일 제목
        String title = confirm.equals(YN.Y) ? "SR Data Request 승인 요청 " : "SR Data Request 승인 취소 요청 ";
        String subject = title + srDataRequest.getSubject();
        // 메세지 생성
        StringBuffer sendMessageBuffer = new StringBuffer();
        sendMessageBuffer.append(title + " 메일 입니다.<br /><br />");
        sendMessageBuffer.append("제목 : ");
        sendMessageBuffer.append(srDataRequest.getSubject());
        sendMessageBuffer.append("<br/>");
        sendMessageBuffer.append("작성자 : [");
        sendMessageBuffer.append(srDataRequest.getResistMember().getTeamName());
        sendMessageBuffer.append("] ");
        sendMessageBuffer.append(srDataRequest.getResistMember().getName());
        sendMessageBuffer.append("<br/>");
        sendMessageBuffer.append("<br/>");
        sendMessageBuffer.append("링크 : <a href=\"http://" + serverAddress + "/static/service_reqeust.html?id=" + srDataRequest.getId() + "\" >여기</a> 를 클릭해 주세요");

        // 수신자
        List<String> to = new ArrayList<String>();
        for(Member m : sendMemberList) {
            // root 계정은 올바른 메일 주소가 아님으로 제외 시킨다.
            if(!"root@test.com".equals(m.getLoginId())) {
                to.add(m.getLoginId());
            }
        }


        MailMessageVo vo = new MailMessageVo(
                srDataRequest.getResistMember().getLoginId()
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
            logger.trace(format("{}", "Confirm Request TASK send mail end"),srDataRequest.getId());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }
}
