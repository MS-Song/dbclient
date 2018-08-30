package com.song7749.dbclient.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberAddDto;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberFindDto;
import com.song7749.dbclient.value.MemberModifyByAdminDto;
import com.song7749.dbclient.value.MemberModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;
import com.song7749.dbclient.value.MemberVo;
import com.song7749.dbclient.value.RenewApikeyDto;

/**
 * <pre>
 * Class Name : MemberManager.java
 * Description : Member Manager
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
public interface MemberManager {

	/**
	 * Member 추가, loginId 에 대한 중복 방지 기능이 있음.
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo addMemeber(MemberAddDto dto);

	/**
	 * 회원 정보 수정
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo modifyMember(MemberModifyDto dto);

	/**
	 * 관리자 회원 수정 기능
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo modifyMember(MemberModifyByAdminDto dto);

	/**
	 * 회원 삭제
	 * @param id
	 */
	void removeMember(Long id);

	/**
	 * 회원 ID 로 조회
	 * @param id
	 * @return MemberVo
	 */
	MemberVo findMember(Long id);

	/**
	 * 로그인 ID 로 조회
	 * @param loginId
	 * @return MemberVo
	 */
	MemberVo findMember(String loginId);

	/**
	 * 로그인 ID와 패스워드로 조회
	 * @param loginId
	 * @param password
	 * @return MemberVo
	 */
	MemberVo findMember(String loginId, String password);


	/**
	 * 회원 정보를 조회 한다.
	 * @param dto
	 * @param page
	 * @return
	 */
	Page<MemberVo> findMemberList(MemberFindDto dto, Pageable page);

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
	 * apikey 를 생성하거나 갱신
	 * @param dto
	 * @return
	 */
	MemberVo renewApikey(RenewApikeyDto dto);

	/**
	 * 관리자 apikey 갱신
	 * @param loginId
	 * @return
	 */
	MemberVo renewApikeyByAdmin(String loginId);
}