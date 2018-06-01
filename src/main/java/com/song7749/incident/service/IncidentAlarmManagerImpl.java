package com.song7749.incident.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.song7749.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.LoginSession;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberVo;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.incident.repository.IncidentAlarmRepository;
import com.song7749.incident.task.IncidentAlarmTask;
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmDetailVo;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmModifyAfterConfirmDto;
import com.song7749.incident.value.IncidentAlarmModifyBeforeConfirmDto;
import com.song7749.incident.value.IncidentAlarmVo;
import com.song7749.log.service.LogManager;
import com.song7749.log.value.LogIncidentAlaramAddDto;
import com.song7749.mail.service.EmailService;
import com.song7749.util.validate.Validate;

@Service
public class IncidentAlarmManagerImpl implements IncidentAlarmManager, SchedulingConfigurer{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DatabaseRepository databaseRepository;

	@Autowired
	private IncidentAlarmRepository incidentAlarmRepository;

	@Autowired
	private DBclientManager dbClientManager;

	@Autowired
	private LogManager logManager;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	private final Map<Long, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<Long, ScheduledFuture<?>>();

	@Autowired
	private EmailService emailService;

	@Autowired
	LoginSession loginSession;

	@Autowired
	private SimpMessagingTemplate template;

	@Validate
	@Transactional
	@Override
	public IncidentAlarmVo addIncidentAlarm(IncidentAlarmAddDto dto) {
		// crontab 검증
		validateCrontabExpression(dto.getSchedule());

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
	public IncidentAlarmVo modifyIncidentAlarm(IncidentAlarmModifyBeforeConfirmDto dto) {
		// 객체 형변환
		IncidentAlarm ia = mapper.map(dto, IncidentAlarm.class);

		Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
		if(oDatabase.isPresent()) {
			ia.setDatabase(oDatabase.get());
		} else {
			throw new IllegalArgumentException("Database 정보가 일치하지 않습니다. DatabaseId="+dto.getDatabaseId());
		}

		List<Member> memberList = memberRepository.findAllById(dto.getSendMemberIds());
		if(!CollectionUtils.isEmpty(memberList)) {
			ia.setSendMembers(memberList);
		} else {
			throw new IllegalArgumentException("수신 대상자가 없습니다.");
		}
		return modifyIncidentAlarm(ia);
	}

	@Validate
	@Transactional
	@Override
	public IncidentAlarmVo modifyIncidentAlarm(IncidentAlarmModifyAfterConfirmDto dto) {
		// 객체 형변환
		IncidentAlarm ia = mapper.map(dto, IncidentAlarm.class);

		Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
		if(oDatabase.isPresent()) {
			ia.setDatabase(oDatabase.get());
		} else {
			throw new IllegalArgumentException("Database 정보가 일치하지 않습니다. DatabaseId="+dto.getDatabaseId());
		}

		List<Member> memberList = memberRepository.findAllById(dto.getSendMemberIds());
		if(!CollectionUtils.isEmpty(memberList)) {
			ia.setSendMembers(memberList);
		} else {
			throw new IllegalArgumentException("수신 대상자가 없습니다.");
		}
		return modifyIncidentAlarm(ia);
	}

	private IncidentAlarmVo modifyIncidentAlarm(IncidentAlarm modifySource) {
		// crontab 검증
		validateCrontabExpression(modifySource.getSchedule());


		Optional<IncidentAlarm> oIncidentAlarm = incidentAlarmRepository.findById(modifySource.getId());
		IncidentAlarm ia = null;
		if(oIncidentAlarm.isPresent()) {
			ia = oIncidentAlarm.get();
			mapper.map(modifySource, ia);
		} else {
			throw new IllegalArgumentException("존재하지 않는 작업입니다");
		}

		incidentAlarmRepository.saveAndFlush(ia);

		try {
			// 로그를 기록 한다.
			StringBuffer before = new StringBuffer();
			StringBuffer after = new StringBuffer();

			before.append("subject : " +		modifySource.getSubject());
			before.append(", beforeSql : " + 	modifySource.getBeforeSql());
			before.append(", runSql : " +		modifySource.getRunSql());
			before.append(", databaseId : " + 	modifySource.getDatabase().getId());
			before.append(", schedule : " + 	modifySource.getSchedule());
			before.append(", enable : " +		modifySource.getEnableYN());

			after.append("subject : " + 		ia.getSubject());
			after.append(", beforeSql : " + 	ia.getBeforeSql());
			after.append(", runSql : " + 		ia.getRunSql());
			after.append(", databaseId : " + 	ia.getDatabase().getId());
			after.append(", schedule : " + 		ia.getSchedule());
			after.append(", enable : " + 		ia.getEnableYN());
			if(null!=loginSession && null!=loginSession.getLogin()) {
				after.append(", modifyId : " + 		loginSession.getLogin().getLoginId());
			}

			LogIncidentAlaramAddDto logDto = new LogIncidentAlaramAddDto(
					"Not Support",
					ia.getId(),
					before.toString(),
					after.toString());
			logManager.addIncidentAlarmLog(logDto);
		} catch (Exception e) {
			logger.error("알람 로그 기록 실패 ID:" + modifySource.getId());
		}
		// confirm 이후에 수정되면 스케줄러를 수정한다.
		if(YN.Y.equals(ia.getConfirmYN())) addOrModifyTasks(ia);
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
		// confirm 시에 스케줄러를 수정한다.
		addOrModifyTasks(ia);

		try {
			StringBuffer after = new StringBuffer();
			after.append("confirm:Y");
			if(null!=loginSession && null!=loginSession.getLogin()) {
				after.append(", modifyId : " + 	loginSession.getLogin().getLoginId());
			}

			// 로그를 기록 한다.
			LogIncidentAlaramAddDto logDto = new LogIncidentAlaramAddDto(
					"Not Support",
					ia.getId(),
					"{}",
					after.toString());
			logManager.addIncidentAlarmLog(logDto);
		} catch (Exception e) {
			logger.error("알람 로그 기록 실패 ID:" + ia.getId());
		}
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
					return mapper.map(t,IncidentAlarmVo.class);
				}
		});
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Optional<IncidentAlarmDetailVo> findIncidentAlarm(IncidentAlarmFindDto dto) {
		Optional<IncidentAlarm> oIncidentAlarm = incidentAlarmRepository.findOne(dto);
		if(oIncidentAlarm.isPresent()) {
			IncidentAlarmDetailVo vo = mapper.map(oIncidentAlarm.get(), IncidentAlarmDetailVo.class);
			// databaseVo
			if(null!=oIncidentAlarm.get().getDatabase()) {
				vo.setDatabaseVo(mapper.map(oIncidentAlarm.get().getDatabase(),DatabaseVo.class));
			}
			// resist member
			if(null!=oIncidentAlarm.get().getResistMember()) {
				vo.setResistMemberVo(mapper.map(oIncidentAlarm.get().getResistMember(), MemberVo.class));
			}
			// confirm member
			if(null!=oIncidentAlarm.get().getConfirmMember()) {
				vo.setConfirmMemberVo(mapper.map(oIncidentAlarm.get().getConfirmMember(), MemberVo.class));
			}
			// send memberVo
			List<MemberVo> sendMemberVos = new ArrayList<MemberVo>();
			for(Member m : oIncidentAlarm.get().getSendMembers()) {
				sendMemberVos.add(mapper.map(m, MemberVo.class));
			}
			vo.setSendMemberVos(sendMemberVos);

			return Optional.ofNullable(vo);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * 최초 기동 시에 configure 초기화
	 */
	@Override
	@Transactional
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// 실행 가능 상태이고, 컨펌이 완료된 task list를 가져온다.
		IncidentAlarmFindDto dto = new IncidentAlarmFindDto();
		dto.setEnableYN(YN.Y);
		dto.setConfirmYN(YN.Y);
		List<IncidentAlarm> alarmList = incidentAlarmRepository.findAll(dto);

		// taskRegistrar 를 이용하지 않고 스케줄러에 직접 집어 넣는다.
		for(IncidentAlarm incidentAlarm : alarmList) {
			addOrModifyTasks(incidentAlarm);
		}
		logger.trace(format("{}", "start task list"), taskMap);
	}

	/**
	 * 수정되거나 추가된 알람을 조회하여 스케줄을 변경 한다.
	 * @param incidentAlarm
	 */
	private synchronized void addOrModifyTasks(IncidentAlarm incidentAlarm) {

		// 새로 설정하기 위해 제거 한다.
		if(taskMap.containsKey(incidentAlarm.getId())) {
			logger.trace(format("{}", "already task "), incidentAlarm.getId());
			// 기존 스케줄을 제거 한다 -- 제거 해야 한다.
			taskMap.get(incidentAlarm.getId()).cancel(true);
			taskMap.remove(incidentAlarm.getId());
		}

		// 사용 중이 아니거나 , confirm 되지 않는 task 는 등록하지 않는다.
		// 보내야할 사용자 리스트도 포함 되어야 한다.
		if(YN.Y.equals(incidentAlarm.getEnableYN())
				&& YN.Y.equals(incidentAlarm.getConfirmYN())
				&& !incidentAlarm.getSendMembers().isEmpty()) {

			ScheduledFuture<?> future = taskScheduler.schedule(
					new IncidentAlarmTask(dbClientManager, incidentAlarm,
							incidentAlarmRepository, emailService,template)
					, new CronTrigger(incidentAlarm.getSchedule()));

			taskMap.put(incidentAlarm.getId(), future);

		}
		logger.trace(format("{}", "add or modify cron task"), taskMap);
	}

	@Override
	public List<Date> crontabNextRunTimes(String schedule) {
		// cron 표현식 검증
		if(!CronSequenceGenerator.isValidExpression(schedule)) {
			throw new IllegalArgumentException("스케줄의 Crontab 표현식이 올바르지 않습니다.");
		}
		// 스케줄러 생성
		CronSequenceGenerator cg = new CronSequenceGenerator(schedule);
		List<Date> list  = new ArrayList<Date>();
		// 향후 10회 정도의 스케줄을 생성해서 사용자에게 고지시켜 준다.
		for(int i=0;i<10;i++) {
			if(list.isEmpty()){ // 처음 실행
				list.add(cg.next(new Date()));
			} else {
				list.add(cg.next(list.get(i-1)));
			}
		}
		return list;
	}

	private void validateCrontabExpression(String schedule) {
		// cron 표현식 검증
		if(!CronSequenceGenerator.isValidExpression(schedule)) {
			throw new IllegalArgumentException("스케줄의 Crontab 표현식이 올바르지 않습니다.");
		}
		// 스케줄 간격이 너무 짧은 경우 입력을 실패 시킨다.
		List<Date> list = crontabNextRunTimes(schedule);
		Date before = null;
		for(Date now : list) {
			if(before != null) {
				if(now.getTime() - before.getTime() < 30000) {
					throw new IllegalArgumentException("스케줄 간격이 30초 이내입니다. 30초 이상 되도록 설정 하세요");
				}
			}
			before=now;
		}
	}

	@Override
	@Transactional
	public void runNow(Long alarmId) {
		Optional<IncidentAlarm> oAlarm = incidentAlarmRepository.findById(alarmId);
		IncidentAlarm incidentAlarm = null;
		if(oAlarm.isPresent()) {
			incidentAlarm = oAlarm.get();
		} else {
			throw new IllegalArgumentException("알람정보가 일치하지 않습니다. id="+alarmId);
		}

		taskScheduler.getScheduledExecutor().execute(new IncidentAlarmTask(dbClientManager, incidentAlarm,
				incidentAlarmRepository, emailService,template));
		;
	}
}