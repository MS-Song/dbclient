package com.song7749.log.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.entities.MemberLoginLog;
import com.song7749.util.crypto.CryptoAES;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientLogTransactionManager",defaultRollback=true)
@Transactional("dbClientLogTransactionManager")
public class MemberLoginLogRepositoryHibernateTest {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	MemberLoginLogRepository memberLoginLogRepository;

	/**
	 * fixture
	 */
	MemberLoginLog memberLoginLog;
	@Before
	public void setUp(){
		memberLoginLog = new MemberLoginLog(
			"root",
			"10.10.10.10",
			CryptoAES.encrypt("root"),
			new Date());
	}


	@Test
	public void testCRFasade() throws Exception{
		MemberLoginLog rMemberLoginLog = testSave();
		testFind(rMemberLoginLog);
		testFindMemberLoginLogList();



	}

	public MemberLoginLog testSave() throws Exception {
		// give // when
		memberLoginLogRepository.save(memberLoginLog);
		// then
		assertThat(memberLoginLog.getMemberLoginLogSeq(), notNullValue());

		return memberLoginLog;
	}

	public void testFind(MemberLoginLog memberLoginLog) throws Exception {
		// give // when
		MemberLoginLog rMemberLoginLog=memberLoginLogRepository.find(memberLoginLog);

		// then
		assertThat(rMemberLoginLog.getMemberLoginLogSeq(), is(memberLoginLog.getMemberLoginLogSeq()));
	}

	public void testFindMemberLoginLogList() throws Exception {
		// give
		FindMemberLoginLogListDTO dto = new FindMemberLoginLogListDTO();
		dto.setId(memberLoginLog.getId());
		dto.setIp(memberLoginLog.getIp());
		dto.setStartDate(memberLoginLog.getLoginDate());
		// when
		List<MemberLoginLog> list = memberLoginLogRepository.findMemberLoginLogList(dto );
		// then
		assertThat(memberLoginLog.getMemberLoginLogSeq(), is(list.get(0).getMemberLoginLogSeq()));

		dto.setStartDate(null);
		dto.setEndDate(memberLoginLog.getLoginDate());
		// when
		list = memberLoginLogRepository.findMemberLoginLogList(dto );
		// then
		assertThat(memberLoginLog.getMemberLoginLogSeq(), is(list.get(0).getMemberLoginLogSeq()));

		dto.setStartDate(memberLoginLog.getLoginDate());
		dto.setEndDate(memberLoginLog.getLoginDate());
		// when
		list = memberLoginLogRepository.findMemberLoginLogList(dto );
		// then
		assertThat(memberLoginLog.getMemberLoginLogSeq(), is(list.get(0).getMemberLoginLogSeq()));
	}
}