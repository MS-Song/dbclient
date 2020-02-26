package com.song7749.member.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import com.song7749.ModuleCommonApplicationTests;
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

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberManagerImplTest {

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
	MemberVo vo = null;
	MemberAddDto dto = new MemberAddDto(
			"song7749@test.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, "123-123-1234");

	@BeforeEach 
	public void setup() throws Exception {
		vo = memberManager.findMember(dto.getLoginId());
		if(null==vo) {
			vo = memberManager.addMemeber(dto);
		}

		loginSession = new LoginSession();
		loginSession.setSesseion(new LoginAuthVo(vo.getId(),vo.getAuthType()));

		MockitoAnnotations.initMocks(this);
		MemberManager mm = (MemberManager) ProxyUtils.unwrapProxy(memberManager);
		ReflectionTestUtils.setField(mm, "loginSession", loginSession);

	}

	@Test
	@Order(1)
	@DisplayName("회원 추가 시에 이메일을 null 로 전달 하면 Exception 이 발생 해야 함 -- assertThrows 이용")
	public void testAddMemeberGivenLoginIdIsNull() throws Exception {
		// give
		dto.setLoginId(null);
		// when
	   Exception exception = assertThrows(IllegalArgumentException.class, () -> {
		   memberManager.addMemeber(dto);
		   // then
	    });
	   
	   assertTrue(exception.getMessage().equals("loginId 은(는)(=) 반드시 값이 존재하고 공백 문자를 제외한 길이가 0보다 커야 합니다."));
	}
	

	@Test
	@Order(2)
	@DisplayName("회원 추가 정상 작동")
	public void testAddMemeber() throws Exception {
		// give
		dto.setLoginId("song7749@gmail.com");
		// when
		vo = memberManager.addMemeber(dto);
		// then
		assertThat(vo.getId(),notNullValue());
	}

	@Test
	@Order(3)
	@DisplayName("회원 수정 정상 작동")
	public void testModifyMember() throws Exception {
		// give
		MemberModifyDto dto = mapper.map(vo, MemberModifyDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");

		// when
		MemberVo vo = memberManager.modifyMember(dto);
		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
	}

	@Test
	@Order(4)
	@DisplayName("관리자 회원 수정 정상 작동 - 권한을 변경 가능 함")
	public void testModifyMemberMemberModifyByAdminDto() throws Exception {
		//give
		MemberModifyByAdminDto dto = mapper.map(vo, MemberModifyByAdminDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");
		dto.setAuthType(AuthType.ADMIN);

		// when
		MemberVo vo = memberManager.modifyMember(dto);

		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
		assertThat(dto.getAuthType(), equalTo(vo.getAuthType()));

	}

	@Test
	@Order(5)
	@DisplayName("회원 조회 기능 테스트 ")
	public void testFindMemberList() throws Exception {
		// give
		dto.setLoginId("song0@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song1@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song2@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song3@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song4@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song5@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song6@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song7@test.com");
		memberManager.addMemeber(dto);
		dto.setLoginId("song8@test.com");
		memberManager.addMemeber(dto);

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
	@Order(99)
	@DisplayName("회원 삭제 정상 작동")
	public void testRemoveMember() throws Exception {
		// give
		memberManager.removeMember(vo.getId());
		// when
		vo = memberManager.findMember(vo.getLoginId());
		// then
		assertThat(vo,nullValue());
	}

	@Test
	@Order(6)
	@DisplayName("회원 ID 조회 ")
	public void testFindMemberLong() throws Exception {
		// give
		// when
		MemberVo vo2 = memberManager.findMember(vo.getId());
		// then
		assertThat(vo.getId(), equalTo(vo2.getId()));
	}

	@Test
	@Order(7)
	@DisplayName("없는 회원 조회")
	public void testFindMemberLongReturnNull() throws Exception {
		// give
		Long id = 100L;
		// when
		MemberVo vo2 = memberManager.findMember(id);
		// then
		assertNull(vo2);
	}

	@Test
	@Order(8)
	@DisplayName("로그인 ID로 조회")
	public void testFindMemberString() throws Exception {
		// give
		// when
		MemberVo vo2 = memberManager.findMember(vo.getLoginId());
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));
	}

	@Test
	@Order(9)
	@DisplayName("없는 로그인 ID 조회")
	public void testFindMemberStringReturnNull() throws Exception {
		// give
		String loginId="song9999@gmail.com";
		// when
		MemberVo vo2 = memberManager.findMember(loginId);
		// then
		assertNull(vo2);
	}

	@Test
	@Order(10)
	@DisplayName("로그인ID 와 PW 로 조회")
	public void testFindMemberStringString() throws Exception {
		// give
		Member m = memberRepository.findByLoginId(vo.getLoginId());
;		// when
		MemberVo vo2 = memberManager.findMember(m.getLoginId(), m.getPassword());
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));

	}
}