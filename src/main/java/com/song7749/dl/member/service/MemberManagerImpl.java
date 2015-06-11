package com.song7749.dl.member.service;

import static com.song7749.dl.member.convert.MemberConvert.convert;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberByAdminDTO;
import com.song7749.dl.member.dto.ModifyMemberDTO;
import com.song7749.dl.member.dto.RemoveMemberDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.exception.MemberNotFoundException;
import com.song7749.dl.member.repositories.MemberRepository;
import com.song7749.dl.member.vo.MemberVO;
import com.song7749.util.validate.annotation.Validate;


/**
 * <pre>
 * Class Name : MemberManagerImpl.java
 * Description : Member Manager 구현체
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
@Service("memberManager")
public class MemberManagerImpl implements MemberManager{

	@Autowired
	MemberRepository memberRepository;

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager")
	public void addMember(AddMemberDTO dto) {
		// 기존 ID를 조회 한뒤 변경 분에 대해서만 업데이트 한다.
		Member member = memberRepository.find(new Member(dto.getId()));
		if(null != member && null != member.getId()){
			throw new IllegalArgumentException("이미 가입된  회원 ID 입니다.");
		}
		memberRepository.save(convert(dto));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager")
	public void modifyMember(ModifyMemberDTO dto) {
		// 기존 ID를 조회 한뒤 변경 분에 대해서만 업데이트 한다.
		Member member = memberRepository.find(new Member(dto.getId()));

		if(null == member){
			throw new MemberNotFoundException();
		}

		if(!StringUtils.isBlank(dto.getEmail())){
			member.setEmail(dto.getEmail());
		}
		if(!StringUtils.isBlank(dto.getPassword())){
			member.setPassword(dto.getPassword());
		}
		if(!StringUtils.isBlank(dto.getPasswordAnswer())){
			member.setPasswordAnswer(dto.getPasswordAnswer());
		}

		if(!StringUtils.isBlank(dto.getPasswordQuestion())){
			member.setPasswordQuestion(dto.getPasswordQuestion());
		}

		// 상속을 사용하여, 역참조가 되지 않기 때문에 어쩔 수 없이 처리
		if(dto instanceof ModifyMemberByAdminDTO){
			if(null != ((ModifyMemberByAdminDTO) dto).getAuthType()){
				member.setAuthType(((ModifyMemberByAdminDTO) dto).getAuthType());
			}
		}

		memberRepository.update(member);
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager")
	public void modifyMember(ModifyMemberByAdminDTO dto) {
		modifyMember((ModifyMemberDTO) dto);
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager")
	public void removeMember(RemoveMemberDTO dto) {
		memberRepository.delete(new Member(dto.getId()));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager",readOnly=true)
	public List<MemberVO> findMemberList(FindMemberListDTO dto) {
		return convert(memberRepository.findMemberList(dto));
	}
}
