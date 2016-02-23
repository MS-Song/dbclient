package com.song7749.log.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.entities.MemberLoginLog;

/**
 * <pre>
 * Class Name : MemberLoginLogRepository.java
 * Description :  회원 로그인 로그 조회 repository
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
public interface MemberLoginLogRepository extends Repository<MemberLoginLog>{

	/**
	 * 회원 로그인 로그 리스트 조회
	 * @param dto
	 * @return List<MemberLoginLog>
	 */
	List<MemberLoginLog> findMemberLoginLogList(FindMemberLoginLogListDTO dto);

}
