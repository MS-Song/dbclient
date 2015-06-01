package com.song7749.dl.member.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberDTO;
import com.song7749.dl.member.dto.RemoveMemberDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.exception.MemberNotFoundException;
import com.song7749.dl.member.repositories.MemberRepository;
import com.song7749.dl.member.type.AuthType;
import com.song7749.util.ProxyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class MemberManagerImplTest {

	@Mock
	MemberRepository memberRepository;

	@Autowired
	MemberManager memberManager;

	@Before
	public void setupMock() throws Exception{
		MockitoAnnotations.initMocks(this);
		MemberManager o = (MemberManager)ProxyUtils.unwrapProxy(memberManager);
		ReflectionTestUtils.setField(o, "memberRepository", memberRepository);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddMember_validate_dto() throws Exception {
		// give
		AddMemberDTO dto=null;
		// when
		memberManager.addMember(dto);
		// then exception
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddMember_validate_dto_id() throws Exception {
		// give
		AddMemberDTO dto=new AddMemberDTO(null
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교");
		// when
		memberManager.addMember(dto);
		// then exception
	}

	@Test
	public void testAddMember() throws Exception {
		// give
		AddMemberDTO dto=new AddMemberDTO("song7749"
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교");
		// when
		memberManager.addMember(dto);
		// then
		verify(memberRepository,times(1)).save(any(Member.class));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddMember_duplication_id() throws Exception {
		// give
		AddMemberDTO dto = new AddMemberDTO("song7749"
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교");

		given(memberRepository.find(any(Member.class))).willReturn(new Member("song7749"));

		// when
		memberManager.addMember(dto);
		// then
		verify(memberRepository,times(1)).save(any(Member.class));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMember_validate_dto() throws Exception {
		// give
		ModifyMemberDTO dto = null;
		// when
		memberManager.modifyMember(dto);
		// then exception
	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMember_validate_dto_id() throws Exception {
		// give
		ModifyMemberDTO dto = new ModifyMemberDTO(null
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교"
				, AuthType.ADMIN);
		// when
		memberManager.modifyMember(dto);
		// then exception
	}


	@Test(expected=MemberNotFoundException.class)
	public void testModifyMember_memberNotFoundException() throws Exception {
		// give
		ModifyMemberDTO dto = new ModifyMemberDTO("song7749"
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교"
				, AuthType.ADMIN);

		given(memberRepository.find(any(Member.class))).willReturn(null);

		// when
		memberManager.modifyMember(dto);
		// then exception
	}

	@Test
	public void testModifyMember() throws Exception {
		// give
		ModifyMemberDTO dto = new ModifyMemberDTO("song7749"
				, "12345678"
				, "song7749@gmail.com"
				, "초등학교는?"
				, "대한민국 초등학교"
				, AuthType.ADMIN);

		given(memberRepository.find(any(Member.class))).willReturn(new Member());

		// when
		memberManager.modifyMember(dto);
		// then
		verify(memberRepository,times(1)).update(any(Member.class));
	}


	@Test(expected=IllegalArgumentException.class)
	public void testRemoveMember_valiate_dto() throws Exception {
		// give
		RemoveMemberDTO dto = null;
		// when
		memberManager.removeMember(dto );
		// then exception
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveMember_valiate_dto_id() throws Exception {
		// give
		RemoveMemberDTO dto = new RemoveMemberDTO();
		// when
		memberManager.removeMember(dto );
		// then exception
	}

	@Test
	public void testRemoveMember() throws Exception {
		// give
		RemoveMemberDTO dto = new RemoveMemberDTO("song7749");
		given(memberRepository.find(any(Member.class))).willReturn(new Member());

		// when
		memberManager.removeMember(dto );

		// then
		verify(memberRepository,times(1)).delete(any(Member.class));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFindMemberList_validate_dto() throws Exception {
		// give
		FindMemberListDTO dto = null;
		// when
		memberManager.findMemberList(dto);
		// then exception
	}

	@Test
	public void testFindMemberList() throws Exception {
		// give
		FindMemberListDTO dto = new FindMemberListDTO();
		// when
		memberManager.findMemberList(dto);
		// then
		verify(memberRepository,times(1)).findMemberList(dto);
	}
}