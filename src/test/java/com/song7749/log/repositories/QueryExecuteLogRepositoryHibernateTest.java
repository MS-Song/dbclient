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

import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.entities.QueryExecuteLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientLogTransactionManager",defaultRollback=true)
@Transactional("dbClientLogTransactionManager")
public class QueryExecuteLogRepositoryHibernateTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	QueryExecuteLogRepository queryExecuteLogRepository;

	/**
	 * fixture
	 */
	QueryExecuteLog queryExecuteLog;
	@Before
	public void setUp(){
		queryExecuteLog = new QueryExecuteLog(
				"root",
				"10.10.10.10",
				"127.0.0.1",
				"테스트 서버",
				"member",
				"user",
				"select * from dual",
				new Date());
	}


	@Test
	public void testCRFasade() throws Exception{
		QueryExecuteLog rExecuteLog = testSave();
		testFind(rExecuteLog);
		testFindQueryExecuteLogList(rExecuteLog);
	}

	public QueryExecuteLog testSave() throws Exception {
		// give // when
		queryExecuteLogRepository.save(queryExecuteLog);

		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), notNullValue());
		return queryExecuteLog;
	}

	public void testFind(QueryExecuteLog queryExecuteLog) throws Exception {
		// give // when
		QueryExecuteLog rQueryExecuteLog = queryExecuteLogRepository.find(queryExecuteLog);
		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), is(rQueryExecuteLog.getQueryExeucteSeq()));
	}

	public void testFindQueryExecuteLogList(QueryExecuteLog queryExecuteLog) throws Exception {
		// give
		FindQueryExecuteLogListDTO dto = new FindQueryExecuteLogListDTO();
		dto.setId(queryExecuteLog.getId());
		dto.setIp(queryExecuteLog.getIp());
		dto.setHost(queryExecuteLog.getHost());
		dto.setHostAlias(queryExecuteLog.getHostAlias());
		dto.setSchemaName(queryExecuteLog.getSchemaName());
		dto.setAccount(queryExecuteLog.getAccount());
		dto.setQuery(queryExecuteLog.getQuery());
		// when
		List<QueryExecuteLog> list = queryExecuteLogRepository.findQueryExecuteLogList(dto);
		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), is(list.get(0).getQueryExeucteSeq()));

		// give
		dto.setStartDate(queryExecuteLog.getExecuteDate());
		// when
		list = queryExecuteLogRepository.findQueryExecuteLogList(dto);
		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), is(list.get(0).getQueryExeucteSeq()));

		// give
		dto.setStartDate(null);
		dto.setEndDate(queryExecuteLog.getExecuteDate());
		// when
		list = queryExecuteLogRepository.findQueryExecuteLogList(dto);
		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), is(list.get(0).getQueryExeucteSeq()));

		// give
		dto.setStartDate(queryExecuteLog.getExecuteDate());
		dto.setEndDate(queryExecuteLog.getExecuteDate());
		// when
		list = queryExecuteLogRepository.findQueryExecuteLogList(dto);
		// then
		assertThat(queryExecuteLog.getQueryExeucteSeq(), is(list.get(0).getQueryExeucteSeq()));

	}
}