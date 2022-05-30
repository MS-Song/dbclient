package com.song7749.log.service;

import com.song7749.log.value.LogIncidentAlaramAddDto;
import com.song7749.log.value.LogLoginAddDto;
import com.song7749.log.value.LogQueryAddDto;

/**
 * <pre>
 * Class Name : LogManager.java
 * Description : 로그를 기록하고 조회하는 매니저
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public interface LogManager {

	/**
	 * 회원 로그인 로그를 기록한다.
	 * @param dto
	 */
	public void addLogLogin(LogLoginAddDto dto);

	/**
	 * 실행한 쿼리 로그를 기록한다.
	 * @param dto
	 */
	public void addQueryExecuteLog(LogQueryAddDto dto);

	/**
	 * 알람 수정 내역을 저장 한다.
	 * @param dto
	 */
	public void addIncidentAlarmLog(LogIncidentAlaramAddDto dto);

}