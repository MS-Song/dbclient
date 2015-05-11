package com.song7749.dl.member.service;

import static com.song7749.dl.member.convert.MemberConvert.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberDTO;
import com.song7749.dl.member.dto.RemoveMemberDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.entities.MemberAuth;
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

		if(null != dto.getEmail()){
			member.setEmail(dto.getEmail());
		}
		if(null != dto.getAuthType()){
			List<MemberAuth> memberAuthList = new ArrayList<MemberAuth>();
			memberAuthList.add(new MemberAuth(dto.getAuthType()));
			member.setMemberAuthList(memberAuthList);
		}
		if(null != dto.getPassword()){
			member.setPassword(dto.getPassword());
		}
		if(null != dto.getPasswordAnswer()){
			member.setPasswordAnswer(dto.getPasswordAnswer());
		}

		if(null != dto.getPasswordQuestion()){
			member.setPasswordQuestion(dto.getPasswordQuestion());
		}

		memberRepository.update(member);
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
