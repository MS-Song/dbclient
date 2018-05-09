package com.song7749.incident.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.incident.value.IncidentAlarmAddDto;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.incident.value.IncidentAlarmFindDto;
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
}
