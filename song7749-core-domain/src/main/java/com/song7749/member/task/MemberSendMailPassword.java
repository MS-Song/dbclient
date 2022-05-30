package com.song7749.member.task;

import com.song7749.mail.service.EmailService;
import com.song7749.mail.value.MailMessageVo;
import com.song7749.member.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.song7749.util.LogMessageFormatter.format;

/**
 * <pre>
 * Class Name : MemberSendMailPassword
 * Description : 사용자에게 새로운 패스워드를 메일로 전송해 준다.
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  28/01/2020		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 28/01/2020
 */
public class MemberSendMailPassword implements Runnable {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Member member;

    private EmailService emailService;

    private Environment environment;

    public MemberSendMailPassword(Member member, EmailService emailService, Environment environment) {
        this.member = member;
        this.emailService = emailService;
        this.environment = environment;
    }

    @Override
    public void run() {
        logger.trace(format("{}", "Start Send Mail Member Password"),member.getLoginId());

        // 서버 주소 확인
        logger.trace(format("{}:{}", "end Mail Member Password Server Info")
                ,environment.getProperty("java.rmi.server.hostname"),environment.getProperty("local.server.port"));

        // 서버 주소
        String serverAddress = environment.getProperty("java.rmi.server.hostname") +
                ":" + environment.getProperty("local.server.port");

        String subject = "SR DATA CENTER LOGIN INFO";

        StringBuffer sendMessageBuffer = new StringBuffer();
        sendMessageBuffer.append("등록되어 있는 패스워드는 아래와 같습니다. <br /><br />");
        sendMessageBuffer.append(member.getPassword());
        sendMessageBuffer.append("<br / >");
        sendMessageBuffer.append("<br / >");
        sendMessageBuffer.append("패스워드 변경 후 반드시 다시 로그인 해주시기 바랍니다.");
        sendMessageBuffer.append("링크 : <a href=\"http://" + serverAddress + "/static/service_reqeust.html\">이동하기</a> 를 클릭해 주세요");


        MailMessageVo vo = new MailMessageVo(
                "root@test.com"
                , Arrays.asList(new String[]{member.getLoginId()})
                , null
                , null
                , subject
                , sendMessageBuffer.toString()
                , null);

        // 메일 전송 시작
        try {
            emailService.sendMessage(vo);
            // 메일을 전송 한다.
            logger.trace(format("{}", "Member Password send mail end"), member.getLoginId());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }

    }
}
