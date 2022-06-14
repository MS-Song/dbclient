package com.song7749.incident.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.common.base.SendMethod;
import com.song7749.common.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class
	, properties = "spring.config.location=classpath:/application.yml")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IncidentAlarmRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IncidentAlarmRepository incidentAlarmRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@PersistenceContext
	private EntityManager em;

	/**
	 * fixture
	 */
	Member member = Member.builder()
		.loginId("song7749@test.com")
		.password("12345678")
		.passwordQuestion("패스워드질문은?")
		.passwordAnswer("패스워드답변은?")
		.teamName("제일잘나가는팀")
		.name("song7749")
		.authType(AuthType.ADMIN)
		.build();

	Database database = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			,"");


	@BeforeEach
	public void setup() {
		memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(database);
	}

	@Test
	public void testSave() {
		List<Member> members  = new ArrayList<Member>();
		members.add(member);

		// give
		IncidentAlarm incidentAlarm = new IncidentAlarm(
				"테스트 모니터링",
				"select 'Y' execute from dual",
				"select * from Database",
				SendMethod.EMAIL,
				YN.Y,
				"0/10 * * * * *",
				database,
				member,
				members);
		// when
		incidentAlarmRepository.saveAndFlush(incidentAlarm);
		// then
		assertThat(incidentAlarm.getId(),notNullValue());

		// give
		incidentAlarm.setConfirmYN(YN.Y);
		incidentAlarm.setConfirmMember(member);
		incidentAlarm.setConfirmDate(new Date(System.currentTimeMillis()));
		// when
		incidentAlarmRepository.saveAndFlush(incidentAlarm);
		// then
		assertThat(incidentAlarm.getConfirmYN(),equalTo(YN.Y));

	}
}
