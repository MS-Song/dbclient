package com.song7749.incident.task;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.incident.repository.IncidentAlarmRepository;
import com.song7749.mail.domain.MailConfig;
import com.song7749.mail.repository.MailConfigRepository;
import com.song7749.mail.service.EmailService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient"
	,"com.song7749.incident.repository"
	,"com.song7749.incident.task"
	,"com.song7749.config"
	,"com.song7749.mail"
	,"com.song7749.log"})
public class IncidentAlarmTaskTest {

	Logger logger = LoggerFactory.getLogger(getClass());

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

	@Autowired
	private EmailService emailService;

	@Autowired
	MailConfigRepository mailConfigRepository;

	@Mock
	private SimpMessagingTemplate template;

	@Autowired
	ModelMapper mapper;

	/**
	 * fixture
	 */
	Member member = new Member(
			"song7749@homeplus.co.kr"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);


//	Database database = new Database(
//			"jdbc:h2:file:~/incidentAlertTest",
//			"DB Client Local TEST H2 Database",
//			"PUBLIC",
//			"sa",
//			"",
//			DatabaseDriver.H2,
//			Charset.UTF8,
//			"");

	Database database = new Database(
		"local-dev"
		, "oracle-local"
		, "XE"
		, "SONG7749"
		, "12345678"
		, DatabaseDriver.ORACLE
		, Charset.UTF8
		, "1521"
		, null);

	List<Member> members = new ArrayList<Member>();

	MailConfig mailConfig = new MailConfig(
			"smtp.homeplusnet.co.kr",
			25, // TLS 587 // SSL 465
			null,
			null);

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

		mailConfig.setAuth(false);
		mailConfig.setEnableSSL(false);
		mailConfig.setStarttls(false);
		mailConfig.setDebug(true);
		mailConfigRepository.saveAndFlush(mailConfig);
	}

	@Test
	public void testRun() throws Exception {
		logger.trace(format("{}", "Log Message"),database);
		logger.trace(format("{}", "Log Message"),((DBclientManagerImpl)dbClientManager).getDataSourceMap());
		logger.trace(format("{}", "Log Message"),dbClientManager.getConnection(database).getSchema());


		ExecuteQueryDto dto = new ExecuteQueryDto();
		dto.setId(database.getId());
		dto.setUsePLSQL(false);
		dto.setUseLimit(false);
		dto.setHtmlAllow(false);
		dto.setLoginId(member.getLoginId());
		dto.setIp("0.0.0.0");
		dto.setQuery("select * from tab");
		dbClientManager.executeQuery(dto);


		IncidentAlarm incidentAlarm = new IncidentAlarm(
				"테스트 모니터링",
				"select 'Y' execute from dual",
				//"첫번째 SQL 입니다 \r\n <sql>select * from database_info </sql>\r\n 두번째 SQL 입니다 \r\n <sql>select * from member</sql>\r\n 세번째 SQL 입니다 \r\n  <sql>select * from log</sql>",
				//"메일 내용은 이러하다 \r\n <sql> select * from database_info </sql>  \r\n 메일 내용은 이러하다 \r\n <sql> select * from database_info </sql>",
				"<sql>  SELECT A.MENU_ID, SUBSTR(MAX(SYS_CONNECT_BY_PATH(A.MENU_NM, '>')), 2) MENU_NM FROM SYS_MENUINFO A  WHERE USE_YN = 'Y' AND MENUURL_TXT IS NOT NULL AND UPMENU_ID <> '00000' CONNECT BY PRIOR A.MENU_ID = A.UPMENU_ID START WITH A.UPMENU_ID = '00000' GROUP BY A.MENU_ID </sql>",
				//"SELECT A.MENU_ID, SUBSTR(MAX(SYS_CONNECT_BY_PATH(A.MENU_NM, '>')), 2) MENU_NM FROM SYS_MENUINFO A  WHERE USE_YN = 'Y' AND MENUURL_TXT IS NOT NULL AND UPMENU_ID <> '00000' CONNECT BY PRIOR A.MENU_ID = A.UPMENU_ID START WITH A.UPMENU_ID = '00000' GROUP BY A.MENU_ID",
				//"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM ALL_TABLES T1, ALL_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME and T1.OWNER=T2.OWNER and T1.OWNER=upper('SONG7749') order by TABLE_NAME asc",
				SendMethod.EMAIL,
				YN.Y,
				"*/10 * * * * *",
				database,
				member,
				members);

		incidentAlarm.setSendMessage("감사합니다.\r\n감사합니다.\r\n감사합니다.\r\n감사합니다.\r\n감사합니다.\r\n");
		incidentAlarm.setConfirmYN(YN.Y);
		incidentAlarm.setConfirmMember(member);
		incidentAlarm.setConfirmDate(new Date(System.currentTimeMillis()));

		incidentAlarmRepository.saveAndFlush(incidentAlarm);

		IncidentAlarmTask task = new IncidentAlarmTask(
				dbClientManager,
				incidentAlarm,
				incidentAlarmRepository,
				emailService,
				template,
				mapper);

		task.run();

		// thread 처리 종료 시간을 벌어 준다.
		Thread.sleep(30000);
	}
}