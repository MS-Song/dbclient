package com.song7749.dbclient.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;

/**
 * <pre>
 * Class Name : DBClientMemberManager.java
 * Description :  DB Client 내의 회원 관련 기능 관리
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 26.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 26.
*/
public interface DBClientMemberManager {

	/**
	 * 회원의 Query 를 저장 한다.
	 * @param dto
	 * @return MemberSaveQueryVo
	 */
	MemberSaveQueryVo addMemberSaveQuery(MemberSaveQueryAddDto dto);

	/**
	 * 회원의 저장된 Query 를 수정 한다.
	 * @param dto
	 * @return MemberSaveQueryVo
	 */
	MemberSaveQueryVo modifyMemberSaveQuery(MemberSaveQueryModifyDto dto);


	/**
	 * 회원의 저장된 Query 를 삭제 한다.
	 * @param dto
	 */
	void removeMemberSaveQuery(MemberSaveQueryRemoveDto dto);

	/**
	 * 회원의 저장된 Query 를 조회 한다.
	 * @param dto
	 * @param page
	 * @return
	 */
	Page<MemberSaveQueryVo> findMemberSaveQueray(MemberSaveQueryFindDto dto, Pageable page);

	/**
	 * 회원의 Database 권한 추가
	 * @param dto
	 * @return
	 */
	MemberDatabaseVo addOrModifyMemberDatabase(MemberDatabaseAddOrModifyDto dto);

	/**
	 * 회원의 Database 리스트 조회
	 * @param dto
	 * @return
	 */
	Page<MemberDatabaseVo> findMemberDatabaseList(MemberDatabaseFindDto dto, Pageable page);

	/**
	 * 회원에게 허용된  Database 리스트를 조회
	 * DatabaseVo 객체로 변환하여 제공 함.
	 * @param dto
	 * @param page
	 * @return
	 */
	Page<DatabaseVo> findDatabaseListByMemberAllow(MemberDatabaseFindDto dto, Pageable page);

	/**
	 * 회원이 해당 Database 에 접근 가능한가 확인
	 * @param loginId
	 * @param DatabaseId
	 * @return 접근 가능하면 true 를  리턴함
	 */
	boolean isAccessPossibleDatabase(Long loginId, Long DatabaseId);

}