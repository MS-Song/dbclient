package com.song7749.incident.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.base.SendMethod;
import com.song7749.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.incident.repository.IncidentAlarmRepository;
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmDetailVo;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmModifyAfterConfirmDto;
import com.song7749.incident.value.IncidentAlarmModifyBeforeConfirmDto;
import com.song7749.incident.value.IncidentAlarmVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient"
	,"com.song7749.incident"
	,"com.song7749.config"
	,"com.song7749.mail"
	,"com.song7749.log"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncidentAlarmManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	IncidentAlarmRepository incidentAlarmRepository;

	@Autowired
	IncidentAlarmManager incidentAlarmManager;

	@Autowired
	ModelMapper mapper;

	/**
	 * fixture
	 */
	Member member = new Member(
			"song7749@incident.com"
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
			, "");

	Database database2 = new Database("20.20.20.20"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			, "");


	List<Long> memberIds = new ArrayList<Long>();

	IncidentAlarmVo vo;

	@Before
	public void setup() {
		memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(database);
		databaseRepository.saveAndFlush(database2);
		memberIds.add(member.getId());
		memberIds.add(member.getId());
		memberIds.add(member.getId());
	}

//	@Test
	public void test1AddIncidentAlarm() throws Exception {
		// give
		IncidentAlarmAddDto dto = new IncidentAlarmAddDto(
				"테스트 모니터링",
				"select 'Y' execute from dual",
				"select * from Database",
				SendMethod.EMAIL,
				YN.Y,
				"*/10 * * * * *",
				database.getId(),
				member.getId(),
				memberIds);
		// when
		vo = incidentAlarmManager.addIncidentAlarm(dto);

		// then
		assertThat(vo.getId(),notNullValue());
	}

//	@Test
	public void test2ModifyIncidentAlarmIncidentAlarmModifyBeforeConfirmDto() throws Exception {
		test1AddIncidentAlarm();

		// give
		IncidentAlarmModifyBeforeConfirmDto beforeConfirmDto = new IncidentAlarmModifyBeforeConfirmDto(
				vo.getId(),
				"테스트 모니터링",
				"select 'Y' execute from dual",
				"select * from Database",
				SendMethod.EMAIL,
				YN.Y,
				"*/20 * * * * *",
				database2.getId(),
				memberIds);
		// when
		vo = incidentAlarmManager.modifyIncidentAlarm(beforeConfirmDto);
		// then
		assertThat(vo.getSchedule(),equalTo("*/20 * * * * *"));
	}

//	@Test
	public void test3ConfirmIncidentAlarm() throws Exception {
		test2ModifyIncidentAlarmIncidentAlarmModifyBeforeConfirmDto();

		// give
		IncidentAlarmConfirmDto confirmDto = new IncidentAlarmConfirmDto(
				vo.getId(),
				YN.Y,
				new Date(System.currentTimeMillis()),
				member.getId());
		// when
		vo = incidentAlarmManager.confirmIncidentAlarm(confirmDto);
		// then
		assertThat(vo.getId(),notNullValue());
		assertThat(vo.getConfirmYN(),equalTo(YN.Y));
	}

//	@Test
	public void testModifyIncidentAlarmIncidentAlarmModifyAfterConfirmDto() throws Exception {
		test3ConfirmIncidentAlarm();

		// give
		IncidentAlarmModifyAfterConfirmDto afterConfirmDto = new IncidentAlarmModifyAfterConfirmDto(
				vo.getId(),
				"테스트 모니터링",
				"select 'Y' execute from dual",
				YN.Y,
				"*/30 * * * * *",
				database.getId(),
				memberIds);
		// when
		vo =incidentAlarmManager.modifyIncidentAlarm(afterConfirmDto);
		// then
		assertThat(vo.getSchedule(),equalTo("*/30 * * * * *"));
	}

	@Test
	public void test4FindIncidentAlarmList() throws Exception {
		testModifyIncidentAlarmIncidentAlarmModifyAfterConfirmDto();

		// give
		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");

		IncidentAlarmFindDto findDto = mapper.map(vo,IncidentAlarmFindDto.class);
		findDto.setFromCreateDate(new Date(System.currentTimeMillis()-60*60*1000*24));
		findDto.setToCreateDate(new Date(System.currentTimeMillis()+60*60*1000*24));
		findDto.setFromConfirmDate(new Date(System.currentTimeMillis()-60*60*1000*24));
		findDto.setToConfirmDate(new Date(System.currentTimeMillis()+60*60*1000*24));

//		findDto.setFromLastRunDate(new Date(System.currentTimeMillis()-60*60*1000*24));
//		findDto.setToLastRunDate(new Date(System.currentTimeMillis()+60*60*1000*24));

		findDto.setDatabaseId(database.getId());
		findDto.setResistMemberId(member.getId());
		findDto.setConfirmMemberId(member.getId());
		findDto.setSendMemberIds(memberIds);

		// when
		Page<IncidentAlarmVo> pageVo = incidentAlarmManager.findIncidentAlarmList(findDto, page);
		// then
		logger.trace(format("{}", "IncidentAlarmVo Message"),pageVo.getContent());

		assertThat(pageVo.getContent().size(),equalTo(1));

		Optional<IncidentAlarmDetailVo> vo = incidentAlarmManager.findIncidentAlarm(findDto);
		assertThat(vo.get(),notNullValue());
		assertThat(vo.get().getSendMemberVos().size(),equalTo(1));
	}

	@Test
	public void test5RunScheduler() throws Exception {
		test1AddIncidentAlarm();
		test3ConfirmIncidentAlarm();
		Thread.sleep(10000);
	}
}