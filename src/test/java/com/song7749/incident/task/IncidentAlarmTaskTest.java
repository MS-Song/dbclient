package com.song7749.incident.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.base.SendMethod;
import com.song7749.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.DBclientManagerImpl;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.incident.repository.IncidentAlarmRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient"
	,"com.song7749.incident"
	,"com.song7749.config"})
public class IncidentAlarmTaskTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	IncidentAlarmRepository incidentAlarmRepository;

	@Autowired
	private DBclientManager dbClientManager;

	@Autowired
	DataSource hikariH2;

	/**
	 * fixture
	 */
	Member member = new Member(
			"song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);


	Database database = new Database(
			"jdbc:h2:file:~/incidentAlertTest",
			"DB Client Local TEST H2 Database",
			"PUBLIC",
			"sa",
			"",
			DatabaseDriver.H2,
			Charset.UTF8,
			"");

	List<Member> members = new ArrayList<Member>();

	@Before
	public void setup() {
		memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(database);
		members.add(member);
		members.add(member);
		members.add(member);


		if(null!=hikariH2) {
			Map<Database, DataSource> map = ((DBclientManagerImpl)dbClientManager).getDataSourceMap();
			map.put(database, hikariH2);
		}
	}

	@Test
	public void testRun() throws Exception {
		IncidentAlarm incidentAlarm = new IncidentAlarm(
				"테스트 모니터링",
				"select 'Y' execute from dual",
				"select * from database",
				SendMethod.EMAIL,
				YN.Y,
				"*/10 * * * * *",
				database,
				member,
				members);

		incidentAlarm.setConfirmYN(YN.Y);
		incidentAlarm.setConfirmMember(member);
		incidentAlarm.setConfirmDate(new Date(System.currentTimeMillis()));

		incidentAlarmRepository.saveAndFlush(incidentAlarm);

		IncidentAlarmTask task = new IncidentAlarmTask(
				dbClientManager,
				incidentAlarm,
				incidentAlarmRepository);

		task.run();

		// thread 처리 종료 시간을 벌어 준다.
		Thread.sleep(1000);
	}
}
