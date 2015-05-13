package com.song7749.dl.member.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.entities.MemberAuth;
import com.song7749.dl.member.type.AuthType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class MemberRepositoryHibernateTest {

	@Resource
	MemberRepository memberRepository;

	@Before
	public void setup(){
	}


	@Test
	public void testGetSesson() throws Exception {
		// give
		// when
		memberRepository.getSesson();
		// then
		assertTrue(true);
	}

	@Test
	public void testGetCriteriaOf() throws Exception {
		// give
		// when
		memberRepository.getCriteriaOf(Member.class);
		// then
		assertTrue(true);
	}

	@Test
	public void testCURDFasade() throws Exception {
		Member member = testSave();
		testFindMemberList(member);
		testUpdate(member);
		testDelete(member);
	}



	public Member testSave() throws Exception {
		// give
		Member member=new Member("song7749", "12345678", "song7749@gmail.com", "초등학교는?", "대한민국초등학교");
		member.addMemberAuthList(new MemberAuth(AuthType.ADMIN));

		// when
		memberRepository.save(member);

		// then
		Member selectedMember = memberRepository.find(member);
		assertThat(member.getId(), is(selectedMember.getId()));
		return member;
	}



	public void testUpdate(Member member) throws Exception {
		// give
		member.setPassword("4567");
		// when
		memberRepository.update(member);
		// then
		Member selectedMember = memberRepository.find(member);
		assertThat(member.getPassword(), is(selectedMember.getPassword()));
	}



	public void testDelete(Member member) throws Exception {
		// give // when
		memberRepository.delete(member);
		//then
		Member selectedMember = memberRepository.find(member);
		assertThat(selectedMember, nullValue());
	}



	public void testFindMemberList(Member member) throws Exception {
		// give
		FindMemberListDTO dto=new FindMemberListDTO();
		// when
		List<Member> list = memberRepository.findMemberList(dto);
		// then
		assertThat(list, notNullValue());
	}
}