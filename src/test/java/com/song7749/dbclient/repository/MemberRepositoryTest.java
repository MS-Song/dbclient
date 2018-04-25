package com.song7749.dbclient.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.domain.MemberDatabase;
import com.song7749.dbclient.domain.MemberSaveQuery;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.dbclient.drs"})
public class MemberRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

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


	@Test
	public void tesetSave() {
		//give
		//when
		Member m1 = memberRepository.saveAndFlush(member);
		//then
		assertThat(m1.getId(),notNullValue());

		//give
		m1.setName("Song");
		//when
		Member m2 = memberRepository.saveAndFlush(m1);
		//then
		assertThat(m1.getName(),equalTo(m2.getName()));

	}

	@Test
	public void testSaveMemeberDatabase() {
		//give
		Database ds = new Database("10.10.10.10"
				, "test server"
				, "dbTest"
				, "song7749"
				, "12345678"
				, DatabaseDriver.ORACLE
				, Charset.UTF8
				, "3333"
				, "");

		databaseRepository.saveAndFlush(ds);

		MemberDatabase md = new MemberDatabase(ds);
		member.addMemberDatabaseList(md);
		//when
		Member m2 = memberRepository.saveAndFlush(member);
		//then
		Optional<Member> m3 = memberRepository.findById(m2.getId());

		assertEquals(m2.getId(), m3.get().getId());
		assertThat(m2.getMemberDatabaseList(), notNullValue());

		// remove child
		m2.getMemberDatabaseList().remove(0);
		memberRepository.saveAndFlush(m2);

	}


	@Test
	public void testSaveMemeberSaveQuery() {
		//give
		Database ds = new Database("10.10.10.10"
				, "test server"
				, "dbTest"
				, "song7749"
				, "12345678"
				, DatabaseDriver.MYSQL
				, Charset.UTF8
				, "3333"
				, "");

		databaseRepository.saveAndFlush(ds);

		MemberSaveQuery msd = new MemberSaveQuery("테스트메모 입니다."
				, "selelct * from dual", member, ds);

		member.addMemberSaveQueryList(msd);


		//when
		Member m2 = memberRepository.saveAndFlush(member);
		//then
		Optional<Member> m3 = memberRepository.findById(m2.getId());

		assertEquals(m2.getId(), m3.get().getId());
		assertThat(m2.getMemberSaveQueryList(), notNullValue());

		// remove child
		m2.getMemberSaveQueryList().remove(0);
		memberRepository.saveAndFlush(m2);

	}

	@Test
	public void testEntityManager() {
		//give
		member.setLoginId("abcd@gmail.com");
		memberRepository.saveAndFlush(member);
		//when
		String sql = "select m from Member m where m.loginId = ?1";
		TypedQuery<Member> query = em.createQuery(sql,Member.class);
		query.setParameter(1, member.getLoginId());
		List<Member> memberList = query.getResultList();
		//then
		assertThat(memberList.get(0).getId(), equalTo(member.getId()));

	}

	@Test
	public void testFindByLoginId() throws Exception {
		//give
		Member m = memberRepository.saveAndFlush(member);
		//when
		Member m2 = memberRepository.findByLoginId(m.getLoginId());
		//then
		assertThat(m.getLoginId(), equalTo(m2.getLoginId()));
	}

	@Test
	public void testFindByLoginIdAndPassword() throws Exception {
		//give
		Member m = memberRepository.saveAndFlush(member);
		//when
		Member m2 = memberRepository.findByLoginIdAndPassword(m.getLoginId(), m.getPassword());
		//then
		assertThat(m.getLoginId(), equalTo(m2.getLoginId()));
	}
}