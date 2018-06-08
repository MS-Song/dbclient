package com.song7749.web.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.MemberManager;
import com.song7749.incident.repository.IncidentAlarmRepository;
import com.song7749.incident.service.IncidentAlarmManager;

@Component
public class initIncidentAlarmConfigBean {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	DBclientManager dbClientManager;

	@Autowired
	IncidentAlarmRepository incidentAlarmRepository;

	@Autowired
	IncidentAlarmManager incidentAlarmManager;

	@Autowired
	DataSource hikariH2;

	@Autowired
	ModelMapper mapper;

	@Transactional
	@PostConstruct
    public void init(){

//		// incident Alert 의 job 을 하나 등록 한다.(Log 삭제)
//		List<Long> memberIds = new ArrayList<Long>();
//		memberIds.add(member.getId());
//		memberIds.add(member.getId());
//		memberIds.add(member.getId());
	//
//		IncidentAlarmAddDto dto = new IncidentAlarmAddDto(
//				"테스트 모니터링",
//				"select 'Y' execute from dual",
//				"select * from database",
//				SendMethod.EMAIL,
//				YN.Y,
//				"*/30 * * * * *",
//				db.getId(),
//				member.getId(),
//				memberIds);
//		IncidentAlarmVo vo = incidentAlarmManager.addIncidentAlarm(dto);
	//
//		IncidentAlarmConfirmDto confirmDto = new IncidentAlarmConfirmDto(
//				vo.getId(),
//				YN.Y,
//				new Date(System.currentTimeMillis()),
//				member.getId());
	//
//		incidentAlarmManager.confirmIncidentAlarm(confirmDto);

//		// 테스트를 위한 대량 등록
//		for(int i=0;i<50;i++) {
//			dto.setSubject(dto.getSubject()+i);
//			incidentAlarmManager.addIncidentAlarm(dto);
//		}

	}
}
