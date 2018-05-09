package com.song7749.incident.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.song7749.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.value.MemberVo;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.incident.repository.IncidentAlarmRepository;
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmVo;
import com.song7749.util.validate.Validate;

@Service
public class IncidentAlarmManagerImpl implements IncidentAlarmManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DatabaseRepository databaseRepository;

	@Autowired
	private IncidentAlarmRepository incidentAlarmRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	@Override
	public IncidentAlarmVo addIncidentAlarm(IncidentAlarmAddDto dto) {
		// 객체 형변환
		IncidentAlarm ia = mapper.map(dto, IncidentAlarm.class);

		Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
		if(oDatabase.isPresent()) {
			ia.setDatabase(oDatabase.get());
		} else {
			throw new IllegalArgumentException("Database 정보가 일치하지 않습니다. DatabaseId="+dto.getDatabaseId());
		}

		Optional<Member> oMember = memberRepository.findById(dto.getMemberId());
		if(oMember.isPresent()) {
			ia.setResistMember(oMember.get());
		} else {
			throw new IllegalArgumentException("등록회원 정보가 일치하지 않습니다. memberId="+dto.getMemberId());
		}

		List<Member> memberList = memberRepository.findAllById(dto.getSendMemberIds());

		if(!CollectionUtils.isEmpty(memberList)) {
			ia.setSendMembers(memberList);
		} else {
			throw new IllegalArgumentException("수신 대상자가 없습니다.");
		}

		incidentAlarmRepository.saveAndFlush(ia);
		return mapper.map(ia, IncidentAlarmVo.class);
	}

	@Validate
	@Transactional
	@Override
	public IncidentAlarmVo confirmIncidentAlarm(IncidentAlarmConfirmDto dto) {
		// 저장되어 있는 객체가 존재해야 한다.
		Optional<IncidentAlarm> oAlarm = incidentAlarmRepository.findById(dto.getId());
		IncidentAlarm ia = null;
		if(oAlarm.isPresent()) {
			ia = oAlarm.get();
		} else {
			throw new IllegalArgumentException("알람정보가 일치하지 않습니다. id="+dto.getId());
		}

		// 객체 형변환
		mapper.map(dto, ia);
		Optional<Member> oMember = memberRepository.findById(dto.getConfirmMemberId());
		if(oMember.isPresent()) {
			ia.setConfirmMember(oMember.get());
			ia.setConfirmYN(YN.Y);
			ia.setConfirmDate(new Date(System.currentTimeMillis()));
		} else {
			throw new IllegalArgumentException("등록회원 정보가 일치하지 않습니다. memberId="+dto.getConfirmMemberId());
		}

		incidentAlarmRepository.saveAndFlush(ia);
		return mapper.map(ia, IncidentAlarmVo.class);
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<IncidentAlarmVo> findIncidentAlarmList(IncidentAlarmFindDto dto, Pageable page) {
	    Page<IncidentAlarm> pages = incidentAlarmRepository.findAll(dto, page);
		return pages.map(
			new Function<IncidentAlarm,IncidentAlarmVo>(){
				@Override
				public IncidentAlarmVo apply(IncidentAlarm t) {
					IncidentAlarmVo vo = mapper.map(t,IncidentAlarmVo.class);
					vo.setResistMemberVo(mapper.map(t.getResistMember(),MemberVo.class));
					return vo;
				}
		});
	}
}