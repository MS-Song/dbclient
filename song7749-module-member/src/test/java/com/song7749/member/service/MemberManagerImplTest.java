package com.song7749.member.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.song7749.UnitTest;
import com.song7749.common.Compare;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberFindDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.util.ProxyUtils;

public class MemberManagerImplTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ModelMapper mapper;

	@Mock
	LoginSession loginSession;

	/**
	 * fixture
	 */
	Member member = new Member(
			"song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);


	MemberAddDto memberAddDto = new MemberAddDto(
			"song7749@test.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, "123-123-1234");

	@Before
	public void setup() throws Exception {
		loginSession = new LoginSession();
		loginSession.setSesseion(new LoginAuthVo(member.getId(),member.getAuthType()));

		MockitoAnnotations.initMocks(this);
		MemberManager mm = (MemberManager) ProxyUtils.unwrapProxy(memberManager);
		ReflectionTestUtils.setField(mm, "loginSession", loginSession);

	}

	@Test
	public void testModelMapperMemberToMemberVo() {
		mapper.map(member, MemberVo.class);
	}

	@Test
	public void testModelMapperMemberAddDtoToMember() {
		mapper.map(memberAddDto, member);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddMemeberGivenLoginIdIsNull() throws Exception {
		// give
		memberAddDto.setLoginId(null);
		// when
		memberManager.addMemeber(memberAddDto);
		// then expected
	}
	@Test
	public void testAddMemeber() throws Exception {
		// give
		// when
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// then
		assertThat(vo.getId(),notNullValue());
	}

	@Test
	public void testModifyMember() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberModifyDto dto = mapper.map(vo, MemberModifyDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");

		// when
		vo = memberManager.modifyMember(dto);
		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
	}

	@Test
	public void testModifyMemberMemberModifyByAdminDto() throws Exception {
		//give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberModifyByAdminDto dto = mapper.map(vo, MemberModifyByAdminDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");
		dto.setAuthType(AuthType.ADMIN);

		// when
		vo = memberManager.modifyMember(dto);

		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
		assertThat(dto.getAuthType(), equalTo(vo.getAuthType()));

	}

	@Test
	public void testFindMemberList() throws Exception {
		// give
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song1@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song2@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song3@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song4@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song5@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song6@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song7@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song8@test.com");
		memberManager.addMemeber(memberAddDto);

		MemberFindDto dto = new MemberFindDto();
		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");

		// when
		Page<MemberVo> voPageList = memberManager.findMemberList(dto,page);
		//then
		assertTrue(voPageList.getContent().size() >= 8);

		// give
		// when
		for(MemberVo vo : voPageList.getContent()) {
			dto = new MemberFindDto();
			Page<MemberVo> pageList = null;

			// give
			dto.setId(vo.getId());
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			List<Long> ids = new ArrayList<Long>();
			ids.add(vo.getId());
			dto.setIds(ids);
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			dto.setLoginId(vo.getLoginId());
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);


			// give
			dto.setLoginIdCompare(Compare.LIKE);
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			dto.setName(vo.getName());
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			dto.setNameCompare(Compare.LIKE);
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			dto.setTeamName(vo.getTeamName());
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);

			// give
			dto.setTeamNameCompare(Compare.LIKE);
			pageList = memberManager.findMemberList(dto,page);
			// then
			assertTrue(pageList.getContent().size() == 1);
		}
	}

	@Test
	public void testRemoveMember() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberFindDto dto = new MemberFindDto();
		Pageable page = PageRequest.of(0, 10);
		// when
		memberManager.removeMember(vo.getId());
		dto.setId(vo.getId());
		Page<MemberVo> pageList = memberManager.findMemberList(dto,page);
		// then
		assertTrue(pageList.getContent().size() == 0);
	}

	@Test
	public void testFindMemberLong() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getId());
		// then
		assertThat(vo.getId(), equalTo(vo2.getId()));
	}

	@Test
	public void testFindMemberLongReturnNull() throws Exception {
		// give
		Long id = 100L;
		// when
		MemberVo vo2 = memberManager.findMember(id);
		// then
		assertNull(vo2);
	}

	@Test
	public void testFindMemberString() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getLoginId());
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));
	}

	@Test
	public void testFindMemberStringReturnNull() throws Exception {
		// give
		String loginId="song9999@gmail.com";
		// when
		MemberVo vo2 = memberManager.findMember(loginId);
		// then
		assertNull(vo2);
	}

	@Test
	public void testFindMemberStringString() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getLoginId(), "12345678");
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));

	}
}