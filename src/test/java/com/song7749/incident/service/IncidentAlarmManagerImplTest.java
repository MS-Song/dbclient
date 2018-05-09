package com.song7749.incident.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient","com.song7749.incident"})
public class IncidentAlarmManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

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

	@Before
	public void setup() {
		memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(database);
	}

	@Test
	public void testAddIncidentAlarm() throws Exception {
		// give
		List<Long> memberIds = new ArrayList<Long>();
		memberIds.add(member.getId());
		memberIds.add(member.getId());
		memberIds.add(member.getId());

		IncidentAlarmAddDto dto = new IncidentAlarmAddDto(
				"테스트 모니터링",
				"select 'Y' execute from dual",
				"select * from Database",
				SendMethod.EMAIL,
				YN.Y,
				"0/10 * * * * *",
				database.getId(),
				member.getId(),
				memberIds);
		// when
		IncidentAlarmVo vo = incidentAlarmManager.addIncidentAlarm(dto);

		// then
		assertThat(vo.getId(),notNullValue());

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

		// give
		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");

		IncidentAlarmFindDto findDto = mapper.map(vo,IncidentAlarmFindDto.class);
		findDto.setFromCreateDate(new Date(System.currentTimeMillis()-60*60*1000*24));
		findDto.setToCreateDate(new Date(System.currentTimeMillis()+60*60*1000*24));
		findDto.setFromConfirmDate(new Date(System.currentTimeMillis()-60*60*1000*24));
		findDto.setToConfirmDate(new Date(System.currentTimeMillis()+60*60*1000*24));
		findDto.setFromLastRunDate(new Date(System.currentTimeMillis()-60*60*1000*24));
		findDto.setToLastRunDate(new Date(System.currentTimeMillis()+60*60*1000*24));

		findDto.setDatabaseId(database.getId());
		findDto.setResistMemberId(member.getId());
		findDto.setConfirmMemberId(member.getId());
		findDto.setSendMemberIds(memberIds);

		// when
		Page<IncidentAlarmVo> pageVo = incidentAlarmManager.findIncidentAlarmList(findDto, page);
		// then
		logger.trace(format("{}", "IncidentAlarmVo Message"),pageVo.getContent());

		assertThat(pageVo.getContent().size(),equalTo(1));
	}
}
