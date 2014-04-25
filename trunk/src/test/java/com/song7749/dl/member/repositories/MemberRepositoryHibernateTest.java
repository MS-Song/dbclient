package com.song7749.dl.member.repositories;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.entities.MemberAuth;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class MemberRepositoryHibernateTest {

	@Resource
	MemberRepository memberRepository;


	/**
	 * fixture
	 */
	Member member;
	MemberAuth memberAuth;
	@Before
	public void setup(){
		member=new Member("song7749", "1234", "song7749@gmail.com", "초등학교는?", "대한민국초등학교");
		member.addMemberAuthList(new MemberAuth(1));
	}

	@Test
	public void testSave() throws Exception {
		memberRepository.save(member);
	}
}