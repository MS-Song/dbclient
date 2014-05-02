package com.song7749.dl.dbclient.service;

import static com.song7749.util.LogMessageFormatter.logFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class DBclientDataSourceManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DBclientDataSourceManager dbClientDataSourceManager;

	/**
	 * fixture
	 */
	ServerInfo serverInfo;

	@Before
	public void setUp() throws Exception {
		serverInfo = new ServerInfo(1, "local-database", "dbBilling", "root", "1234", DatabaseDriver.mysql, "utf-8","3306");
	}

	@Test
	public void testGetDataSource() throws Exception {
		dbClientDataSourceManager.getDataSource(serverInfo);
	}

	@Test
	public void testSelectTableVOList() throws Exception {
		// give // when
		List<TableVO> list = dbClientDataSourceManager.selectTableVOList(serverInfo);
		// then
		// TODO test code
	}

	@Test
	public void testSelectTableFieldVOList() throws Exception {
		// give // when
		List<FieldVO> list = dbClientDataSourceManager.selectTableFieldVOList(serverInfo,"tOrder");
		// then
		// TODO test code

	}

	@Test
	public void testSelectTableIndexVOList() throws Exception {
		// give // when
		List<IndexVO> list = dbClientDataSourceManager.selectTableIndexVOList(serverInfo,"tOrder");
		// then
		// TODO test code
	}

	@Test
	public void testExecuteQueryListServerInfoStringBoolean() throws Exception {
		List<Map<String,String>> list=null;
		List<String> queryList = new ArrayList<String>();
		queryList.add("INSERT INTO tService SET nServiceSeq=1, sServiceName='다나와'");
		queryList.add("select * from tService");
		queryList.add("explain select * from tService");
		queryList.add("update tService set sServiceName='너나와'");
		queryList.add("delete from tService where nServiceSeq=1");

		for(String query:queryList){
			list =dbClientDataSourceManager.executeQueryList(serverInfo,query, true);
			logger.debug(logFormat("result : {} ","test code"),list);
		}

	}
}