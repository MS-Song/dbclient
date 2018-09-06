package com.song7749.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberFindDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.member.value.RenewApikeyDto;

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