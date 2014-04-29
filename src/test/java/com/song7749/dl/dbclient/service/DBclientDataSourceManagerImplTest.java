package com.song7749.dl.dbclient.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
		List<FieldVO> list = dbClientDataSourceManager.selectTableFieldVOList(serverInfo, "tOrder");
		// then
		// TODO test code

	}

	@Test
	public void testSelectTableIndexVOList() throws Exception {
		// give // when
		List<IndexVO> list = dbClientDataSourceManager.selectTableIndexVOList(serverInfo, "tOrder");
		// then
		// TODO test code
	}

}