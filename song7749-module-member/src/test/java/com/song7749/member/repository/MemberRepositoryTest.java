package com.song7749.member.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.member.domain.Member;
import com.song7749.member.type.AuthType;

public class MemberRepositoryTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

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