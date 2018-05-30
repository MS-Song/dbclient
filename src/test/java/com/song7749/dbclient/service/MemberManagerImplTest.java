package com.song7749.dbclient.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.song7749.base.Compare;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberDatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.repository.MemberSaveQueryRepository;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.type.MemberModifyByAdminDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.LoginAuthVo;
import com.song7749.dbclient.value.MemberAddDto;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberFindDto;
import com.song7749.dbclient.value.MemberModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;
import com.song7749.dbclient.value.MemberVo;
import com.song7749.util.ProxyUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient","com.song7749.log"})
public class MemberManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberSaveQueryRepository memberSQRepository;

	@Autowired
	MemberDatabaseRepository memberDatabaseRepository;

	@Autowired
	DatabaseRepository databaseRepository;

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

	Database ds = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			, "");

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

	@Test
	public void testAddMemberSaveQuery() throws Exception {
		// give
		member.setLoginId("savequery@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);

		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());

		// when
		MemberSaveQueryVo msqv = memberManager.addMemberSaveQuery(dto);
		// then
		assertThat(msqv.getId(), notNullValue());
	}

	@Test
	public void testModifyMemberSaveQuery() throws Exception {
		// give
		member.setLoginId("savequeryModify@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);

		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = memberManager.addMemberSaveQuery(dto);

		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId(), msqv.getId(), "메모변경", "select * from dual");
		// when
		msqv = memberManager.modifyMemberSaveQuery(modifyDto);
		// then
		assertNotSame(dto.getMemo(), msqv.getMemo());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMemberSaveQuery_not_data() throws Exception {
		// give
		member.setLoginId("savequeryModify@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(ds);

		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId(), 1L , "메모변경", "select * from dual");
		// when
		memberManager.modifyMemberSaveQuery(modifyDto);
		// then

	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMemberSaveQuery_not_same_member() throws Exception {
		// give
		member.setLoginId("savequeryModify@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);

		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = memberManager.addMemberSaveQuery(dto);

		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId()+1L, msqv.getId(), "메모변경", "select * from dual");
		// when
		msqv = memberManager.modifyMemberSaveQuery(modifyDto);
	}

	@Test
	public void testRemoveMemberSaveQuery() throws Exception {
		// give
		member.setLoginId("savequeryDelete@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);

		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = memberManager.addMemberSaveQuery(dto);

		MemberSaveQueryRemoveDto removeDto = new MemberSaveQueryRemoveDto(m.getId(), msqv.getId());
		// when
		memberManager.removeMemberSaveQuery(removeDto);
		// then
		// FIXME find id
	}

	@Test
	public void testFindMemberSaveQueray() throws Exception {
		// give
		member.setLoginId("savequerySelect@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);

		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = memberManager.addMemberSaveQuery(dto);

		MemberSaveQueryFindDto msqfDto = new MemberSaveQueryFindDto(m.getId(), d.getId(), msqv.getId());

		Pageable page = PageRequest.of(0, 10);
		// when
		Page<MemberSaveQueryVo> pageVo = memberManager.findMemberSaveQueray(msqfDto, page );
		// then
		assertThat(pageVo.getContent().size(), equalTo(1));
	}

	@Test
	public void testAddMemberDatabase() throws Exception {
		// give
		member.setLoginId("memberDatabaseAdd@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);
		MemberDatabaseAddOrModifyDto dto = new MemberDatabaseAddOrModifyDto(d.getId(), m.getId());
		// when
		MemberDatabaseVo vo = memberManager.addOrModifyMemberDatabase(dto);
		// then
		assertThat(vo.getId(), notNullValue());

		// give
		dto = new MemberDatabaseAddOrModifyDto(vo.getId());
		// when
		vo = memberManager.addOrModifyMemberDatabase(dto);
		// then
		assertThat(vo.getId(), nullValue());

	}


	@Test
	public void testFindMemberDatabaseList() throws Exception {
		// give
		member.setLoginId("memberDatabaseFind@gmail.com");
		Member m = memberRepository.saveAndFlush(member);
		Database d = databaseRepository.saveAndFlush(ds);
		MemberDatabaseAddOrModifyDto dto = new MemberDatabaseAddOrModifyDto(d.getId(), m.getId());
		MemberDatabaseVo vo = memberManager.addOrModifyMemberDatabase(dto);
		MemberDatabaseFindDto findDto = new MemberDatabaseFindDto(vo.getId(), m.getId(), d.getId());

		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");
		// when
		Page<MemberDatabaseVo> pageList = memberManager.findMemberDatabaseList(findDto, page);

		// then
		assertThat(pageList.getContent().size(), equalTo(1));

		// give // when
		Page<DatabaseVo> databaeList = memberManager.findDatabaseListByMemberAllow(findDto, page);
		// then
		assertThat(databaeList.getContent().size(), equalTo(1));
	}
}