package com.song7749.dbclient.web.config;

import static com.song7749.util.LogMessageFormatter.format;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dbclient.drs.domain.Database;
import com.song7749.dbclient.drs.domain.Member;
import com.song7749.dbclient.drs.repository.DatabaseRepository;
import com.song7749.dbclient.drs.repository.MemberRepository;
import com.song7749.dbclient.drs.service.DBclienManager;
import com.song7749.dbclient.drs.service.MemberManager;
import com.song7749.dbclient.drs.type.AuthType;
import com.song7749.dbclient.drs.value.MemberVo;

/**
 * <pre>
 * Class Name : InitConfigBean.java
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

@Component
public class InitConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	DBclienManager dbClientManager;

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
		Member aleadyMember = memberRepository.findByLoginId("root@test.com");
		if(null==aleadyMember) {
			memberRepository.saveAndFlush(member);
			MemberVo memberVo = memberManager.renewApikeyByAdmin(member.getLoginId());
			logger.trace(format("{}", "first Start Application with root user create"),memberVo);
		} else {
			logger.trace(format("{}", "root user info"),aleadyMember);
		}

		// Database connection 을 미리 생성 한다.
		List<Database> list = databaseRepository.findAll();
		if(null!=list && list.size() !=0) {
			for(Database database : list) {
				try {
					logger.info(format("{}", "Database Try Connection "),database);
					dbClientManager.getConnection(database);
				} catch (SQLException e) {
					logger.error(format("{}", "Database Connection Fail log"),e.getMessage());
				}
			}
		}
    }
}
