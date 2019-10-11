package com.song7749.incident.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.common.SendMethod;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncidentAlarmRepositoryTest extends UnitTest {

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
	Member member = new Member("song7749@incident.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);

	Database database = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			,"");


	@Before
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
