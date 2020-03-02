package com.song7749.dbclient.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyAddDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyVo;
import com.song7749.dbclient.value.DatabaseRemoveDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.util.ProxyUtils;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/dbclient-application.yml")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	@Mock
	LoginSession loginSession;

	@Autowired
	MemberRepository memberRepository;

	/**
	 * Fixture
	 */
	DatabaseAddDto databaseAddDto = new DatabaseAddDto("10.10.10.10"
			, "Test Database"
			, "Test Schema"
			, "song7749"
			, "1234"
			, DatabaseDriver.MYSQL
			, Charset.UTF8
			, "3306");

	DatabaseVo dv = null;
	
	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		DatabaseManager dm = (DatabaseManager) ProxyUtils.unwrapProxy(databaseManager);
		ReflectionTestUtils.setField(dm, "loginSession", loginSession);
		ReflectionTestUtils.setField(dm, "memberRepository", memberRepository);
		
		// 기초 데이터 입력
		dv = databaseManager.addDatabase(databaseAddDto);
		
	}

	@Test
	@Order(1)
	@DisplayName("데이터베이스 ModelMapper 테스트")
	public void testMapper() {
		// give
		// when
		Database database = mapper.map(databaseAddDto, Database.class);
		// then
		assertThat(database.getHost(), equalTo(database.getHost()));
		assertThat(database.getAccount(), equalTo(database.getAccount()));
		assertThat(database.getCharset(), equalTo(database.getCharset()));
		assertThat(database.getPort(), equalTo(database.getPort()));
	}

	@Test
	@Order(2)
	@DisplayName("데이터베이스 추가 null 실패 테스트")
	public void testAddDatabaseGivenNull() {
		assertThrows(IllegalArgumentException.class, () -> {	
			databaseManager.addDatabase(null);
		});
	}

	@Test
	@Order(3)
	@DisplayName("데이터베이스 추가 host null 실패 테스트")
	public void testAddDatabaseGivenHostNull() {
		// give
		databaseAddDto.setHost(null);
		// when
		assertThrows(IllegalArgumentException.class, () -> {
			databaseManager.addDatabase(databaseAddDto);
		});
	}

	@Test
	@Order(4)
	@DisplayName("데이터베이스 추가 테스트")
	public void testAddDatabase() {
		// give
		// when
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);
		// then
		assertThat(dv.getId(), notNullValue());
		assertThat(dv.getCreateDate(), notNullValue());
		assertThat(dv.getModifyDate(), notNullValue());
		//assertEquals(dv.getCreateDate(), dv.getModifyDate());
	}

	@Test
	@Order(5)
	@DisplayName("데이터베이스 수정 테스트")
	public void testModifyDatabase() throws Exception {
		// give
		DatabaseModifyDto dto = new DatabaseModifyDto();
		dto.setId(dv.getId());
		dto.setPassword("1234abcd");

		// when
		Thread.sleep(1000);
		DatabaseVo rDv = databaseManager.modifyDatabase(dto);
		// then
		assertNotEquals(dv.getModifyDate(), rDv.getModifyDate());
	}

	@Test
	@Order(6)
	@DisplayName("데이터베이스 수정 테스트")
	public void testFindDatabaseList() throws Exception {
		// give
		List<DatabaseVo> voList = new ArrayList<DatabaseVo>();
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));
		voList.add(databaseManager.addDatabase(databaseAddDto));

		DatabaseFindDto dto = new DatabaseFindDto();
		dto.setHost(databaseAddDto.getHost());
		Pageable page = PageRequest.of(0, 10);// ,Direction.DESC,"id");;
		// when
		Page<DatabaseVo> list = databaseManager.findDatabaseList(dto, page);
		// then
		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(6));

		// give
		dto.setId(voList.get(0).getId());
		dto.setHost(voList.get(0).getHost());
		// when
		list = databaseManager.findDatabaseList(dto, page);
		// then
		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(1));

		List<Long> ids = voList.stream().map(v -> v.getId()).collect(Collectors.toList());

		// give
		dto.setIds(ids);

		// when
		list = databaseManager.findDatabaseList(dto, page);

		// then

		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(5));
	}

	@Test
	@Order(6)
	@DisplayName("데이터베이스 삭제 테스트")
	public void testRemoveDatabase() throws Exception {
		// give
		DatabaseRemoveDto dto = new DatabaseRemoveDto(dv.getId());
		// when
		databaseManager.removeDatabase(dto);
		// then
		DatabaseFindDto find = new DatabaseFindDto();
		find.setId(dv.getId());
		Pageable page = PageRequest.of(0, 10);// ,Direction.DESC,"id");;
		Page<DatabaseVo> list = databaseManager.findDatabaseList(find, page);

		assertThat(list.getSize(), equalTo(10));
		assertThat(list.getContent().size(), equalTo(0));

	}

	@Test
	@Order(6)
	@DisplayName("데이터베이스 개인정보 정의 입력")
	public void testAddDatabaseDatabasePrivacyPolicyAddDto() throws Exception {
		// give
		Member member = new Member("song7749@gmail.com"
				, "12345678"
				, "패스워드질문은?"
				, "패스워드답변은?"
				, "제일잘나가는팀"
				, "song7749"
				, AuthType.ADMIN);
		memberRepository.saveAndFlush(member);
		given(loginSession.getLogin()).willReturn(new LoginAuthVo(member.getId()));
		
		DatabasePrivacyPolicyAddDto dto = new DatabasePrivacyPolicyAddDto("tableName"
				, "fieldName"
				, YN.Y
				, "테스트"
				, dv.getId());

		// when
		DatabasePrivacyPolicyVo vo = databaseManager.addDatabasePrivacyPolicy(dto);
		// then
		assertThat(vo.getId(), notNullValue());

		// give
		DatabasePrivacyPolicyModifyDto modifyDto = new DatabasePrivacyPolicyModifyDto(
				vo.getId()
				, YN.Y
				, "테스트2"
				, dv.getId());
		// when
		vo = databaseManager.modifyDatabasePrivacyPolicy(modifyDto);
		// then
		assertThat(vo.getEnableYN(), equalTo(modifyDto.getEnableYN()));
	}
}