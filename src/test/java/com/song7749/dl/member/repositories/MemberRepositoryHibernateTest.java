package com.song7749.dl.member.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
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
import com.song7749.dl.member.entities.MemberDatabase;
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

	@Test(expected=IllegalArgumentException.class)
	public void testSave_valiate_id_null() throws Exception {
		// give
		Member member=new Member(null, "12345678", "song7749@gmail.com", "초등학교는?", "대한민국초등학교",AuthType.ADMIN);

		// when
		memberRepository.save(member);

		// then exception
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSave_valiate_id_empty() throws Exception {
		// give
		Member member=new Member("", "12345678", "song7749@gmail.com", "초등학교는?", "대한민국초등학교",AuthType.ADMIN);

		// when
		memberRepository.save(member);

		// then exception
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSave_valiate_id_size_over() throws Exception {
		// give
		Member member=new Member("sssssssssssssssssssssss", "12345678", "song7749@gmail.com", "초등학교는?", "대한민국초등학교",AuthType.ADMIN);

		// when
		memberRepository.save(member);

		// then exception
	}


	@Test
	public void testCURDFasade() throws Exception {
		Member member = testSave();
		testFindMemberList(member);
		testUpdate(member);
		testUpdateAddMemberDatabase(member);
		testUpdateRemoveMemberDatabase(member);
		testDelete(member);
	}



	public Member testSave() throws Exception {
		// give
		Member member=new Member("song774999", "12345678", "song7749@gmail.com", "출신초등학교는?", "대한민국초등학교",AuthType.ADMIN);

		// when
		memberRepository.save(member);

		// then
		Member selectedMember = memberRepository.find(member);
		assertThat(member.getId(), is(selectedMember.getId()));
		return member;
	}



	public void testUpdate(Member member) throws Exception {
		// give
		member.setPassword("45671234");
		// when
		memberRepository.update(member);
		// then
		Member selectedMember = memberRepository.find(member);
		assertThat(member.getPassword(), is(selectedMember.getPassword()));
	}

	public void testUpdateAddMemberDatabase(Member member) throws Exception {
		// give
		member.addMemberDatabaseList(new MemberDatabase(1));
		// when
		memberRepository.update(member);
		// then
		Member selectedMember = memberRepository.find(member);
		assertThat(selectedMember.getMemberDatabaseList().get(0).getServerInfoSeq(), is(member.getMemberDatabaseList().get(0).getServerInfoSeq()));
	}


	public void testUpdateRemoveMemberDatabase(Member member) throws Exception {
		// give
		Member selectedMember = memberRepository.find(member);

		// 삭제를 위해서는 iterator 를 사용해야 한다.
		for(Iterator<MemberDatabase> md = selectedMember.getMemberDatabaseList().listIterator() ; md.hasNext();){
			if(md.next().equals(member.getMemberDatabaseList().get(0))){
				md.remove();
			}
		}

		// when
		memberRepository.update(member);
		// then
		selectedMember = memberRepository.find(member);
		assertThat(selectedMember.getMemberDatabaseList().size(), is(0));
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
		dto.setId("song774999");
		dto.setEmail("song7749@gmail.com");
		dto.setAuthType(AuthType.ADMIN);
		// when
		List<Member> list = memberRepository.findMemberList(dto);
		// then
		assertThat(list, notNullValue());
	}
}