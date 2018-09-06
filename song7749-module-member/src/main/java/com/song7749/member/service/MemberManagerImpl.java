package com.song7749.member.service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.common.exception.MemberNotFoundException;
import com.song7749.common.validate.Validate;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberFindDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.member.value.RenewApikeyDto;
import com.song7749.util.ObjectJsonUtil;
import com.song7749.util.crypto.CryptoAES;

/**
 * <pre>
 * Class Name : MemberManager.java
 * Description : 회원 관련 기능을 구현한 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 2. 25.
 */
@Service
public class MemberManagerImpl implements MemberManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	LoginSession loginSession;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	@Override
	public MemberVo addMemeber(MemberAddDto dto) {
		return memberRepository.saveAndFlush(dto.getMember(mapper)).getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo modifyMember(MemberModifyDto dto) {
		// 조회 후 변경된 내용만 첨가 mapper config 참조
		Member member = memberRepository.findById(dto.getId()).get();
		if(null == member) {
			throw new MemberNotFoundException();
		}

		// 관리자 수정시에는 변경일을 기록하지 않는다.
		if(dto instanceof MemberModifyByAdminDto) {
			mapper.map(dto, member);
		} else {
			mapper.map(dto, member);
			// 회원 정보 변경 일자 기록
			member.setModifyDate(new Date(System.currentTimeMillis()));
		}

		// 변경에 대한 로그를 기록 해야 한다.
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo modifyMember(MemberModifyByAdminDto dto) {
		return modifyMember((MemberModifyDto)dto);
	}

	@Validate
	@Transactional
	@Override
	public void removeMember(Long id) {
		memberRepository.deleteById(id);
		memberRepository.flush();
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(Long id) {
		Optional<Member> om = memberRepository.findById(id);
		return om.isPresent() ? om.get().getMemberVo(mapper) : null;
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(String loginId) {
		Member m = memberRepository.findByLoginId(loginId);
		return m != null ? m.getMemberVo(mapper) : null ;
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(String loginId, String password) {
		Member m = memberRepository.findByLoginIdAndPassword(loginId, password);
		return m != null ? m.getMemberVo(mapper) : null ;
	}


	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<MemberVo> findMemberList(MemberFindDto dto, Pageable page) {
		Page<Member> pm = memberRepository.findAll(dto, page);
		return  pm.map(
			new Function<Member, MemberVo>() {
				@Override
				public MemberVo apply(Member t) {
					MemberVo vo = mapper.map(t, MemberVo.class);

					// 미인증 회원이나 일반 회원인 경우 VO 내의 개인정보 일부를 제거 한다.
					if(null==loginSession.getLogin()
							||  AuthType.NORMAL.equals(loginSession.getLogin().getAuthType())) {
						vo.setApikey(null);
						vo.setAuthType(null);
						vo.setPasswordQuestion(null);
						vo.setCreateDate(null);
						vo.setModifyDate(null);
						vo.setLastLoginDate(null);
					}
					return vo;
				}
			}
		);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo renewApikey(RenewApikeyDto dto) {
		Member member = memberRepository.findByLoginIdAndPassword(dto.getLoginId(), dto.getPassword());
		if(null==member) {
			throw new IllegalArgumentException("ID 또는 패스워드가 틀렸거나, 회원 정보가 존재하지 않습니다.");
		}

		LoginAuthVo lav = new LoginAuthVo(
				member.getId(),
				member.getLoginId(),
				member.getAuthType(),
				new Date(System.currentTimeMillis()));
		String cipherValue = null;
		try {
			cipherValue=CryptoAES.encrypt(ObjectJsonUtil.getJsonStringByObject(lav));
		} catch (Exception e) {
			throw new IllegalArgumentException("회원 인증 생성 실패. 관리자에게 문의 하세요");
		}
		member.setApikey(cipherValue);
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo renewApikeyByAdmin(String loginId) {
		Member member = memberRepository.findByLoginId(loginId);

		if(null==member) {
			throw new IllegalArgumentException("회원 정보가 없습니다.");
		}

		LoginAuthVo lav = new LoginAuthVo(
				member.getId(),
				member.getLoginId(),
				member.getAuthType(),
				new Date(System.currentTimeMillis()));

		String cipherValue = null;
		try {
			cipherValue=CryptoAES.encrypt(ObjectJsonUtil.getJsonStringByObject(lav));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		member.setApikey(cipherValue);
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}
}