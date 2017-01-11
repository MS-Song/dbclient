package com.song7749.dl.member.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.entities.Member;

/**
 * <pre>
 * Class Name : MemberRepository.java
 * Description : 회원관리 Repository
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 21.		song7749	신규생성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 21.
 */

public interface MemberRepository extends Repository<Member>{
	/**
	 * 회원 리스트 검색
	 * @param dto
	 * @return List<Member>
	 */
	List<Member> findMemberList(FindMemberListDTO dto);

	/**
	 * 회원 권한 일괄 삭제
	 * 서버 리스트가 삭제될 경우 해당하는 서버의 권한도 일괄 삭제가 필요함.
	 * 회원과 서버간의 관계가 없음으로 수동 처리가 필요하여 작성함.
	 * @param serverInfoSeq
	 * @return Integer
	 */
	Integer removeMemberDatabases(Integer serverInfoSeq);

}