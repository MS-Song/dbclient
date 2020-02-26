package com.song7749.member.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.song7749.ModuleCommonApplicationTests;
import com.song7749.member.domain.Member;
import com.song7749.member.type.AuthType;

@ActiveProfiles("test")
@SpringBootTest(classes = ModuleCommonApplicationTests.class)
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@PersistenceContext
	private EntityManager em;

	
	@BeforeEach
	@DisplayName("기초 회원 데이터 입력")
	public void setup() throws Exception {
		// 회원 기초 데이터 입력 
		memberRepository.saveAndFlush(
				new Member("song7749@gmail.com"
				, "12345678"
				, "패스워드질문은?"
				, "패스워드답변은?"
				, "제일잘나가는팀"
				, "song7749"
				, AuthType.ADMIN));
	}
	
	@Test
	@Order(1)
	@DisplayName("회원 입력/수정 테스트")
	public void tesetSave() {
		//give
		Member member = new Member("song7749@test.com"
				, "12345678"
				, "패스워드질문은?"
				, "패스워드답변은?"
				, "제일잘나가는팀"
				, "song7749"
				, AuthType.ADMIN);
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
	@Order(2)
	@DisplayName("EntityManager 를 통한 sql 직접 호출 테스트, 추가로 findByLoginId 도 테스트")
	public void testEntityManager() {
		// give 
		Member m1 = memberRepository.findByLoginId("song7749@gmail.com");
		//when
		String sql = "select m from Member m where m.loginId = ?1";
		TypedQuery<Member> query = em.createQuery(sql,Member.class);
		query.setParameter(1, m1.getLoginId());
		List<Member> memberList = query.getResultList();
		//then
		assertThat(memberList.get(0).getId(), equalTo(m1.getId()));

	}

	@Test
	@Order(2)
	@DisplayName("로그인 ID / Pw 입력을 통한 조회 테스트")
	public void testFindByLoginIdAndPassword() throws Exception {
		//give
		Member m1 = memberRepository.findByLoginId("song7749@gmail.com");
		//when
		Member m2 = memberRepository.findByLoginIdAndPassword(m1.getLoginId(), m1.getPassword());
		//then
		assertThat(m1.getLoginId(), equalTo(m2.getLoginId()));
	}
}