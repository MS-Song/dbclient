package com.song7749.log.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.dto.SaveMemberLoginLogDTO;
import com.song7749.log.dto.SaveQueryExecuteLogDTO;
import com.song7749.log.entities.MemberLoginLog;
import com.song7749.log.entities.QueryExecuteLog;
import com.song7749.log.repositories.MemberLoginLogRepository;
import com.song7749.log.repositories.QueryExecuteLogRepository;
import com.song7749.util.ProxyUtils;
import com.song7749.util.crypto.CryptoAES;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientLogTransactionManager",defaultRollback=true)
@Transactional("dbClientLogTransactionManager")
public class LogManagerImplTest {

	@Autowired
	LogManager logManager;

	@Mock
	MemberLoginLogRepository memberLoginLogRepository;

	@Mock
	QueryExecuteLogRepository queryExecuteLogRepository;

	/**
	 * fixture
	 */
	QueryExecuteLog queryExecuteLog;
	MemberLoginLog memberLoginLog;
	@Before
	public void setup() throws InvalidPropertiesFormatException, IOException{
		// give
		queryExecuteLog = new QueryExecuteLog(
				"root",
				"10.10.10.10",
				"127.0.0.1",
				"테스트 서버",
				"member",
				"user",
				"select * from dual",
				new Date());

		memberLoginLog = new MemberLoginLog(
				"root",
				"10.10.10.10",
				CryptoAES.encrypt("root"),
				new Date());

	}

	@Before
	public void setupMock() throws Exception{
		MockitoAnnotations.initMocks(this);
		LogManager o = (LogManager)ProxyUtils.unwrapProxy(logManager);
		ReflectionTestUtils.setField(o, "memberLoginLogRepository", memberLoginLogRepository);
		ReflectionTestUtils.setField(o, "queryExecuteLogRepository", queryExecuteLogRepository);
	}


	@Test
	public void testSaveMemberLoginLog() throws Exception {
		// give
		SaveMemberLoginLogDTO dto = new SaveMemberLoginLogDTO(
				memberLoginLog.getId(),
				memberLoginLog.getIp(),
				memberLoginLog.getCipher(),
				memberLoginLog.getLoginDate());
		// when
		logManager.saveMemberLoginLog(dto);

		// then
		verify(memberLoginLogRepository,times(1)).save(any(MemberLoginLog.class));

	}

	@Test
	public void testFindMemberLoginLogList() throws Exception {
		// give
		FindMemberLoginLogListDTO dto = new FindMemberLoginLogListDTO(
				memberLoginLog.getId(),
				memberLoginLog.getIp(),
				memberLoginLog.getLoginDate(),
				memberLoginLog.getLoginDate());
		// when
		logManager.findMemberLoginLogList(dto);
		// then
		verify(memberLoginLogRepository,times(1)).findMemberLoginLogList(any(FindMemberLoginLogListDTO.class));
	}

	@Test
	public void testSaveQueryExecuteLog() throws Exception {
		// give
		SaveQueryExecuteLogDTO dto=new SaveQueryExecuteLogDTO(
				queryExecuteLog.getId(),
				queryExecuteLog.getIp(),
				queryExecuteLog.getHost(),
				queryExecuteLog.getHostAlias(),
				queryExecuteLog.getSchemaName(),
				queryExecuteLog.getAccount(),
				queryExecuteLog.getQuery(),
				queryExecuteLog.getExecuteDate());
		// when
		logManager.saveQueryExecuteLog(dto);
		// then
		verify(queryExecuteLogRepository,times(1)).save(any(QueryExecuteLog.class));
	}

	@Test
	public void testFindQueryExecuteLog() throws Exception {
		// give
		FindQueryExecuteLogListDTO dto = new FindQueryExecuteLogListDTO();
		dto.setId(queryExecuteLog.getId());
		dto.setIp(queryExecuteLog.getIp());
		dto.setHost(queryExecuteLog.getHost());
		dto.setHostAlias(queryExecuteLog.getHostAlias());
		dto.setSchemaName(queryExecuteLog.getSchemaName());
		dto.setAccount(queryExecuteLog.getAccount());
		dto.setQuery(queryExecuteLog.getQuery());
		dto.setStartDate(queryExecuteLog.getExecuteDate());
		dto.setEndDate(queryExecuteLog.getExecuteDate());

		// when
		logManager.findQueryExecuteLog(dto);
		// then
		verify(queryExecuteLogRepository,times(1)).findQueryExecuteLogList(any(FindQueryExecuteLogListDTO.class));
	}

}