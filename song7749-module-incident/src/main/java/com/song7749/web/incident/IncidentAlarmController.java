package com.song7749.web.incident;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.song7749.common.base.MessageVo;
import com.song7749.common.base.YN;
import com.song7749.common.exception.AuthorityUserException;
import com.song7749.common.exception.NotDataFoundException;
import com.song7749.incident.service.IncidentAlarmManager;
import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmDetailVo;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmModifyAfterConfirmDto;
import com.song7749.incident.value.IncidentAlarmModifyBeforeConfirmDto;
import com.song7749.incident.value.IncidentAlarmVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@PostMapping("/add")
	public MessageVo addIncidentAlarm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmAddDto dto) throws UnsupportedEncodingException{

		// 인증 정보 추가
		dto.setMemberId(session.getLogin().getId());

		dto.setBeforeSql(
				URLDecoder.decode(
					new String(
							Base64.getDecoder().decode(dto.getBeforeSql())
						,Charset.forName("UTF-8"))
				, "UTF-8"));
		logger.debug(format("{}", "DECODE URL BEFORE SQL"),dto.getBeforeSql());

		dto.setRunSql(
				URLDecoder.decode(
					new String(
							Base64.getDecoder().decode(dto.getRunSql())
						,Charset.forName("UTF-8"))
				, "UTF-8"));
		logger.debug(format("{}", "DECODE URL RUN SQL"),dto.getRunSql());

		return new MessageVo(HttpStatus.OK.value(), 1
				, incidentAlarmManager.addIncidentAlarm(dto), "알람 등록이 완료되었습니다.");
	}

	@ApiOperation(value = "알람 승인전 수정"
			,notes = "알람 승인 전에 수정을 수행 한다."
			,response=MessageVo.class)
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@PutMapping("/modifyBeforeConfirm")
	public MessageVo modifyBeforeConfirm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmModifyBeforeConfirmDto dto) throws UnsupportedEncodingException{

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
			dto.setBeforeSql(
					URLDecoder.decode(
						new String(
								Base64.getDecoder().decode(dto.getBeforeSql())
							,Charset.forName("UTF-8"))
					, "UTF-8"));
			logger.debug(format("{}", "DECODE URL BEFORE SQL"),dto.getBeforeSql());

			dto.setRunSql(
					URLDecoder.decode(
						new String(
								Base64.getDecoder().decode(dto.getRunSql())
							,Charset.forName("UTF-8"))
					, "UTF-8"));
			logger.debug(format("{}", "DECODE URL RUN SQL"),dto.getRunSql());

			return new MessageVo(HttpStatus.OK.value(), 1
					, incidentAlarmManager.modifyIncidentAlarm(dto), "알람 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인이 등록한 알람만 수정 가능 합니다.");
		}
	}

	@ApiOperation(value = "알람 승인 요청"
			,notes = "등록된 알람을 승인 요청 한다.<br/> 관리자로 등록된 모든 회원에게 승인 요청 메일이 발송된다."
			,response=MessageVo.class)
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@PutMapping("/confirmRequest")
	public MessageVo confirmRequest(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmConfirmDto dto){
			// 로그인 세션 ID
			dto.setConfirmMemberId(session.getLogin().getId());
			// 승인 요청 실행
			incidentAlarmManager.confirmRequest(dto);
			return new MessageVo(HttpStatus.OK.value(), 1, "승인이 요청 되었습니다.");
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
					, incidentAlarmManager.confirmIncidentAlarm(dto), dto.getConfirmYN().equals(YN.Y) ? "알람 승인이 완료되었습니다." : "알람 승인이 취소되었습니다.");
	}

	@ApiOperation(value = "알람 즉시 실행"
			,notes = "알람을 즉시 실행 한다."
			,response=MessageVo.class)
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@PutMapping("/runNow")
	public MessageVo runNow(
			HttpServletRequest request,
			HttpServletResponse response,
			@ApiParam("database id")
			@RequestParam(required=true, name="id") Long alarmId,
			@RequestParam(required=true, name="test") boolean test){

		// 관리자
		boolean modifyAble = AuthType.ADMIN.equals(session.getLogin().getAuthType());
		// 관리자 아니면 본인 확인 추가
		if(modifyAble==false) {
			IncidentAlarmFindDto findDto = new IncidentAlarmFindDto(alarmId);
			findDto.setResistMemberId(session.getLogin().getId());
			Optional<IncidentAlarmDetailVo> vo = incidentAlarmManager.findIncidentAlarm(findDto);
			modifyAble = modifyAble || vo.isPresent();
		}

		if(modifyAble) {
			incidentAlarmManager.runNow(alarmId,test);
			return new MessageVo(HttpStatus.OK.value(), 1, "실행 하였습니다.");
		} else {
			throw new AuthorityUserException("본인이 등록한 알람만 실행 가능합니다.");
		}
	}

	@ApiOperation(value = "알람 승인 후 수정"
			,notes = "알람 승인 후에 수정을 수행 한다."
			,response=MessageVo.class)
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@PutMapping("/modifyAfterConfirm")
	public MessageVo modifyAfterConfirm(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmModifyAfterConfirmDto dto) throws UnsupportedEncodingException{

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
			dto.setBeforeSql(
					URLDecoder.decode(
						new String(
								Base64.getDecoder().decode(dto.getBeforeSql())
							,Charset.forName("UTF-8"))
					, "UTF-8"));
			logger.debug(format("{}", "DECODE URL BEFORE SQL"),dto.getBeforeSql());

			return new MessageVo(HttpStatus.OK.value(), 1
					, incidentAlarmManager.modifyIncidentAlarm(dto), "알람 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인이 등록한 알람만 수정 가능 합니다.");
		}
	}


	@ApiOperation(value = "알람 등록자 수정"
			,notes = "알람 등록자를 수정 한다. - 담당자 변경 등으로 다른 사람이 수정할 경우 처리를 위함, 관리자만 가능함"
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PutMapping("/modifyResistMember")
	public MessageVo modifyResistMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Long incidentAlarmId,
			@RequestParam Long resistMemberId) {

		return new MessageVo(HttpStatus.OK.value(), 1
				, incidentAlarmManager.modifyIncidentAlarm(incidentAlarmId,resistMemberId), "알람 등록자 수정이 완료 되었습니다.");
	}


	@ApiOperation(value = "알람 리스트 조회"
			,notes = "등록된 알람 리스트를 조회 한다."
			,response=IncidentAlarmVo.class)
	@Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
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
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@GetMapping("/detail")
	public IncidentAlarmDetailVo detail(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute IncidentAlarmFindDto dto){

		if(dto.getId()==null) {
			throw new IllegalArgumentException("알람 상세조회에는 반드시 ID가 필요 합니다.");
		}

		Optional<IncidentAlarmDetailVo> o = incidentAlarmManager.findIncidentAlarm(dto);
		if(!o.isPresent()) {
			throw new NotDataFoundException();
		}
		return o.get();
	}

	@ApiOperation(value = "알람 다음 스케줄 조회"
			,notes = "입력된 crontab expression 을 확인하여 다음 스케줄 리스트를 반환한다."
			,response=List.class)
	@Login({AuthType.DEVELOPER,AuthType.ADMIN})
	@GetMapping("/nextSchedule")
	public List<Date> nextSchedule(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) String schedule){
		return incidentAlarmManager.crontabNextRunTimes(schedule);
	}
}