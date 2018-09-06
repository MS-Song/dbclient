package com.song7749.dbclient.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

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
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberDatabaseRepository;
import com.song7749.dbclient.repository.MemberSaveQueryRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.util.ProxyUtils;

public class DBClientMemberManagerImplTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	MemberManager memberManager;

	@Autowired
	DBClientMemberManager dbClientMemberManager;

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
	Member m = new Member(
			"song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);

	Database d = new Database("10.10.10.10"
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
		loginSession.setSesseion(new LoginAuthVo(m.getId(),m.getAuthType()));

		memberRepository.saveAndFlush(m);
		databaseRepository.saveAndFlush(d);

		MockitoAnnotations.initMocks(this);
		DBClientMemberManager dmm = (DBClientMemberManager) ProxyUtils.unwrapProxy(dbClientMemberManager);
		ReflectionTestUtils.setField(dmm, "loginSession", loginSession);
	}

	@Test
	public void testAddMemberSaveQuery() throws Exception {
		// give
		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		// when
		MemberSaveQueryVo msqv = dbClientMemberManager.addMemberSaveQuery(dto);
		// then
		assertThat(msqv.getId(), notNullValue());
	}

	@Test
	public void testModifyMemberSaveQuery() throws Exception {
		// give
		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = dbClientMemberManager.addMemberSaveQuery(dto);

		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId(), msqv.getId(), "메모변경", "select * from dual");
		// when
		msqv = dbClientMemberManager.modifyMemberSaveQuery(modifyDto);
		// then
		assertNotSame(dto.getMemo(), msqv.getMemo());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMemberSaveQuery_not_data() throws Exception {
		// give
		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId(), 1L , "메모변경", "select * from dual");
		// when
		dbClientMemberManager.modifyMemberSaveQuery(modifyDto);
		// then
	}

	@Test(expected=IllegalArgumentException.class)
	public void testModifyMemberSaveQuery_not_same_member() throws Exception {
		// give
		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = dbClientMemberManager.addMemberSaveQuery(dto);

		MemberSaveQueryModifyDto modifyDto = new MemberSaveQueryModifyDto(
				m.getId()+1L, msqv.getId(), "메모변경", "select * from dual");
		// when
		msqv = dbClientMemberManager.modifyMemberSaveQuery(modifyDto);
	}

	@Test
	public void testRemoveMemberSaveQuery() throws Exception {
		// give
		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = dbClientMemberManager.addMemberSaveQuery(dto);

		MemberSaveQueryRemoveDto removeDto = new MemberSaveQueryRemoveDto(m.getId(), msqv.getId());
		// when
		dbClientMemberManager.removeMemberSaveQuery(removeDto);
		// then
		// FIXME find id
	}

	@Test
	public void testFindMemberSaveQueray() throws Exception {
		// give
		MemberSaveQueryAddDto dto = new MemberSaveQueryAddDto(
				m.getId(), "쿼리메모", "select 1 from dual", d.getId());
		MemberSaveQueryVo msqv = dbClientMemberManager.addMemberSaveQuery(dto);

		MemberSaveQueryFindDto msqfDto = new MemberSaveQueryFindDto(m.getId(), d.getId(), msqv.getId());

		Pageable page = PageRequest.of(0, 10);
		// when
		Page<MemberSaveQueryVo> pageVo = dbClientMemberManager.findMemberSaveQueray(msqfDto, page );
		// then
		assertThat(pageVo.getContent().size(), equalTo(1));
	}

	@Test
	public void testAddMemberDatabase() throws Exception {
		// give
		MemberDatabaseAddOrModifyDto dto = new MemberDatabaseAddOrModifyDto(d.getId(), m.getId());
		// when
		MemberDatabaseVo vo = dbClientMemberManager.addOrModifyMemberDatabase(dto);
		// then
		assertThat(vo.getId(), notNullValue());

		// give
		dto = new MemberDatabaseAddOrModifyDto(vo.getId());
		// when
		vo = dbClientMemberManager.addOrModifyMemberDatabase(dto);
		// then
		assertThat(vo.getId(), nullValue());

	}


	@Test
	public void testFindMemberDatabaseList() throws Exception {
		// give
		MemberDatabaseAddOrModifyDto dto = new MemberDatabaseAddOrModifyDto(d.getId(), m.getId());
		MemberDatabaseVo vo = dbClientMemberManager.addOrModifyMemberDatabase(dto);
		MemberDatabaseFindDto findDto = new MemberDatabaseFindDto(vo.getId(), m.getId(), d.getId());

		Pageable page = PageRequest.of(0, 10);//,Direction.DESC,"id");
		// when
		Page<MemberDatabaseVo> pageList = dbClientMemberManager.findMemberDatabaseList(findDto, page);

		// then
		assertThat(pageList.getContent().size(), equalTo(1));

		// give // when
		Page<DatabaseVo> databaeList = dbClientMemberManager.findDatabaseListByMemberAllow(findDto, page);
		// then
		assertThat(databaeList.getContent().size(), equalTo(1));
	}
}