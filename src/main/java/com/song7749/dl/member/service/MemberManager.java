package com.song7749.dl.member.service;

import java.util.List;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberByAdminDTO;
import com.song7749.dl.member.dto.ModifyMemberDTO;
import com.song7749.dl.member.dto.ModifyMemberDatabaseDTO;
import com.song7749.dl.member.dto.RemoveMemberDTO;
import com.song7749.dl.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MemberManager.java
 * Description : 회원 매니저
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 29.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 29.
*/
public interface MemberManager {

	/**
	 * 회원 가입
	 * @param dto
	 */
	public void addMember(AddMemberDTO dto);

	/**
	 * 회원 정보 수정
	 * @param dto
	 */
	public void modifyMember(ModifyMemberDTO dto);

	/**
	 * 관리자 회원정보 수정
	 * @param dto
	 */
	public void modifyMember(ModifyMemberByAdminDTO dto);

	/**
	 * 회원과 데이터베이스 간의 연결 처리
	 * @param dto
	 */
	public void modifyMemberDatabase(ModifyMemberDatabaseDTO dto);

	/**
	 * 회원 제거
	 * @param dto
	 */
	public void removeMember(RemoveMemberDTO dto);

	/**
	 * 회원리스트 조회
	 * @param dto
	 * @return List<MemberVO>
	 */
	public List<MemberVO> findMemberList(FindMemberListDTO dto);
}
