package com.song7749.log.service;

import java.util.List;

import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.dto.SaveMemberLoginLogDTO;
import com.song7749.log.dto.SaveQueryExecuteLogDTO;
import com.song7749.log.vo.MemberLoginLogVO;
import com.song7749.log.vo.QueryExecuteLogVO;

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
	public void saveMemberLoginLog(SaveMemberLoginLogDTO dto);

	/**
	 * 회원 로그인 로그를 조회한다.
	 * @param dto
	 * @return List<MemberLoginLogVO>
	 */
	public List<MemberLoginLogVO> findMemberLoginLogList(FindMemberLoginLogListDTO dto);


	/**
	 * 실행한 쿼리 로그를 기록한다.
	 * @param dto
	 */
	public void saveQueryExecuteLog(SaveQueryExecuteLogDTO dto);

	/**
	 * 실행한 쿼리 로그를 조회한다.
	 * @param dto
	 * @return
	 */
	public List<QueryExecuteLogVO> findQueryExecuteLog(FindQueryExecuteLogListDTO dto);
}
