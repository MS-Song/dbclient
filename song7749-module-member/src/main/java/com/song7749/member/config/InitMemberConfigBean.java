package com.song7749.member.config;

import static com.song7749.util.LogMessageFormatter.format;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberVo;

/**
 * <pre>
 * Class Name : InitMemberConfigBean.java
 * Description : App 실행 시 사전에 처리해야 하는 작업들을 정의 함.
 *
 * 1. Root 유저 등록
 * 2. Local - H2 DB 에 대한 내용 확인
 * 3. 테스트 Data 입력 -- 서비스 배포시에는 삭제 필요
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 29.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 29.
*/

@Profile("!test")
@Component("InitMemberConfigBean")
public class InitMemberConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	ModelMapper mapper;

	@Transactional
	@PostConstruct
    public void init(){
		// root 회원에 대한 입력
		Member member = new Member(
				"root@test.com"
				, "12345678"
				, "Password Question?"
				, "Password Answer?"
				, "team name"
				, "your name"
				, AuthType.ADMIN);
		Member aleadyMember = memberRepository.findByLoginId(member.getLoginId());
		if(null==aleadyMember) {
			memberRepository.saveAndFlush(member);
			MemberVo memberVo = memberManager.renewApikeyByAdmin(member.getLoginId());
			logger.info(format("{}", "first Start Application with root user create"),memberVo);
		} else {
			logger.info(format("{}", "root user info"),aleadyMember.getMemberVo(mapper));
		}
    }
}