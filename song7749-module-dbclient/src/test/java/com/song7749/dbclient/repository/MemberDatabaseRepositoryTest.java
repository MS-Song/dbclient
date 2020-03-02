package com.song7749.dbclient.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.MemberDatabase;
import com.song7749.dbclient.domain.MemberSaveQuery;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;


@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class, properties = "spring.config.location=classpath:/dbclient-application.yml")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDatabaseRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberDatabaseRepository memberDatabaseRepository;

	@Autowired
	MemberSaveQueryRepository memberSaveQueryRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@PersistenceContext
	private EntityManager em;

	/**
	 * fixture
	 */
	Member member = new Member("song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);

	Database ds = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			, "");


	@BeforeEach
	@DisplayName("기초 데이터 입력")
	public void tesetSave() {
		memberRepository.saveAndFlush(member);
		databaseRepository.saveAndFlush(ds);
	}

	@Test
	public void testSaveMemeberDatabase() {
		// give
		MemberDatabase md = new MemberDatabase(ds, member);

		// when
		memberDatabaseRepository.saveAndFlush(md);
		// then
		assertThat(md.getId(), notNullValue());
	}


	@Test
	public void testSaveMemeberSaveQuery() {
		// give
		MemberSaveQuery msd = new MemberSaveQuery("테스트메모 입니다."
				, "selelct * from dual", member, ds);

		// when
		memberSaveQueryRepository.saveAndFlush(msd);

		// then
		assertThat(msd.getId(), notNullValue());
	}
}