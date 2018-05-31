package com.song7749.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.dbclient.annotation.Login;
import com.song7749.dbclient.service.LoginSession;
import com.song7749.dbclient.type.AuthType;
import com.song7749.exception.AuthorityUserException;
import com.song7749.exception.NotDataFoundException;
import com.song7749.incident.service.IncidentAlarmManager;
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmDetailVo;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmModifyAfterConfirmDto;
import com.song7749.incident.value.IncidentAlarmModifyBeforeConfirmDto;
import com.song7749.incident.value.IncidentAlarmVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * Class Name : IncidentAlarmController.java
 * Description : 사건 알람 컨트롤러
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 10.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 10.
*/

@Api(tags="알람 관리 기능")
@RestController
@RequestMapping("/alarm")
public class IncidentAlarmController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IncidentAlarmManager incidentAlarmManager;

	@Autowired
	LoginSession session;

	@ApiOperation(value = "알람 등록"
			,notes = "알람을 등록한다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@PostMapping("/add")
	public MessageVo addIncidentAlarm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmAddDto dto){

		// 인증 정보 추가
		dto.setMemberId(session.getLogin().getId());
		return new MessageVo(HttpStatus.OK.value(), 1
				, incidentAlarmManager.addIncidentAlarm(dto), "알람 등록이 완료되었습니다.");
	}

	@ApiOperation(value = "알람 승인전 수정"
			,notes = "알람 승인 전에 수정을 수행 한다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@PutMapping("/modifyBeforeConfirm")
	public MessageVo modifyBeforeConfirm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmModifyBeforeConfirmDto dto){

		// 관리자
		boolean modifyAble = AuthType.ADMIN.equals(session.getLogin().getAuthType());
		// 관리자 아니면
		if(modifyAble==false) {
			//본인 확인 추가
			IncidentAlarmFindDto findDto = new IncidentAlarmFindDto(dto.getId());
			findDto.setResistMemberId(session.getLogin().getId());
			Optional<IncidentAlarmDetailVo> vo = incidentAlarmManager.findIncidentAlarm(findDto);
			modifyAble = modifyAble || vo.isPresent();
		}
		if(modifyAble) {
			return new MessageVo(HttpStatus.OK.value(), 1
					, incidentAlarmManager.modifyIncidentAlarm(dto), "알람 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인이 등록한 알람만 수정 가능 합니다.");
		}
	}

	@ApiOperation(value = "알람 승인"
			,notes = "등록된 알람을 승인한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PutMapping("/confirm")
	public MessageVo confirm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmConfirmDto dto){
			// 로그인 세션 ID
			dto.setConfirmMemberId(session.getLogin().getId());
			return new MessageVo(HttpStatus.OK.value(), 1
					, incidentAlarmManager.confirmIncidentAlarm(dto), "알람 승인이 완료되었습니다.");
	}

	@ApiOperation(value = "알람 승인 후 수정"
			,notes = "알람 승인 후에 수정을 수행 한다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@PutMapping("/modifyAfterConfirm")
	public MessageVo modifyAfterConfirm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmModifyAfterConfirmDto dto){

		// 관리자
		boolean modifyAble = AuthType.ADMIN.equals(session.getLogin().getAuthType());
		// 관리자 아니면 본인 확인 추가
		if(modifyAble==false) {
			IncidentAlarmFindDto findDto = new IncidentAlarmFindDto(dto.getId());
			findDto.setResistMemberId(session.getLogin().getId());
			Optional<IncidentAlarmDetailVo> vo = incidentAlarmManager.findIncidentAlarm(findDto);
			modifyAble = modifyAble || vo.isPresent();
		}
		if(modifyAble) {
			return new MessageVo(HttpStatus.OK.value(), 1
					, incidentAlarmManager.modifyIncidentAlarm(dto), "알람 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인이 등록한 알람만 수정 가능 합니다.");
		}
	}

	@ApiOperation(value = "알람 리스트 조회"
			,notes = "등록된 알람 리스트를 조회 한다."
			,response=IncidentAlarmVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@GetMapping("/list")
	public Page<IncidentAlarmVo> list(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmFindDto dto,
			@PageableDefault(page=0, size=20, direction=Direction.DESC, sort="id") Pageable page){
		return incidentAlarmManager.findIncidentAlarmList(dto,page);
	}

	@ApiOperation(value = "알람 상세 조회"
			,notes = "등록된 알람 상세를 조회 한다."
			,response=IncidentAlarmDetailVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@GetMapping("/detail")
	public IncidentAlarmDetailVo detail(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmFindDto dto){

		Optional<IncidentAlarmDetailVo> o = incidentAlarmManager.findIncidentAlarm(dto);
		if(!o.isPresent()) {
			throw new NotDataFoundException();
		}
		return o.get();
	}

	@ApiOperation(value = "알람 다음 스케줄 조회"
			,notes = "입력된 crontab expression 을 확인하여 다음 스케줄 리스트를 반환한다."
			,response=List.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@GetMapping("/nextSchedule")
	public List<Date> nextSchedule(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) String schedule){
		return incidentAlarmManager.crontabNextRunTimes(schedule);
	}
}