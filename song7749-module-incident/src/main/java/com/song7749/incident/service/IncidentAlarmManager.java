package com.song7749.incident.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmDetailVo;
import com.song7749.incident.value.IncidentAlarmFindDto;
import com.song7749.incident.value.IncidentAlarmModifyAfterConfirmDto;
import com.song7749.incident.value.IncidentAlarmModifyBeforeConfirmDto;
import com.song7749.incident.value.IncidentAlarmVo;

/**
 * <pre>
 * Class Name : IncidentAlarmManager.java
 * Description : IncidentAlarm Manager
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 4.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 4.
*/

public interface IncidentAlarmManager {

	/**
	 * 사건 알람을 저장한다.
	 * @param dto
	 * @return
	 */
	IncidentAlarmVo addIncidentAlarm(IncidentAlarmAddDto dto);

	/**
	 * 사건 알람을 수정한다.
	 * 수정은 극히 일부분만 가능하며, 승인 이후에는 Query 등의 수정이 불가능하다.
	 * @param dto
	 * @return
	 */
	IncidentAlarmVo modifyIncidentAlarm(IncidentAlarmModifyBeforeConfirmDto dto);

	/**
	 * 사건 알람을 수정한다.
	 * 승인 이후에는 동작여부, 감지방법, 주기, 대상자추가 정도 수정 가능하다.
	 * @param dto
	 * @return
	 */
	IncidentAlarmVo modifyIncidentAlarm(IncidentAlarmModifyAfterConfirmDto dto);


	/**
	 * 사건 알람을 승인 요청 한다.
	 * @param dto
	 * @return
	 */
	void confirmRequest(IncidentAlarmConfirmDto dto);
	/**
	 * 사건 알람을 승인 한다.
	 * @param dto
	 * @return
	 */
	IncidentAlarmVo confirmIncidentAlarm(IncidentAlarmConfirmDto dto);

	/**
	 * 사건 알람 리스트를 조회한다.
	 * @param dto
	 * @return
	 */
	Page<IncidentAlarmVo> findIncidentAlarmList(IncidentAlarmFindDto dto, Pageable page);

	/**
	 * 사건 알람을 조회한다.
	 * @param dto
	 * @return
	 */
	Optional<IncidentAlarmDetailVo> findIncidentAlarm(IncidentAlarmFindDto dto);

	/**
	 * 입력된 crontab expression 의 다음 실행 시간을 출력해 준다.
	 * @param schedule
	 * @return
	 */
	List<Date> crontabNextRunTimes(String schedule);

	/**
	 * task 를 즉시 실행한다.
	 * @param alarmId
	 * @param test
	 */
	void runNow(Long alarmId, boolean test);
}