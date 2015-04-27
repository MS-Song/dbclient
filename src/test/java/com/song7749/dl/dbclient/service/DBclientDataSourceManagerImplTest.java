package com.song7749.dl.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")

@Ignore	// 테스트 DB 서버가 준비되지 않아 막아 놓음. 테스트 빌드 서버가 구비되면, 해제 바람
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
		// 서버 인포 설정
		serverInfo = new ServerInfo("10.20.10.41"
						, "dbclient"
						, "dbclient"
						, "1234"
						, DatabaseDriver.mysql
						, "UTF-8"
						,"3306");
	}

	@Test
	public void testGetConnection() throws Exception {
		// give .. when
		dbClientDataSourceManager.getConnection(serverInfo);
		// then
		assertTrue(true);
	}

	@Test
	public void testSelectTableVOList() throws Exception {
		// give // when
		List<TableVO> list = dbClientDataSourceManager.selectTableVOList(serverInfo);
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testSelectTableFieldVOList() throws Exception {
		// give // when
		List<FieldVO> list = dbClientDataSourceManager.selectTableFieldVOList(serverInfo,"tOrder");
		// then
		assertThat(list, notNullValue());

	}

	@Test
	public void testSelectTableIndexVOList() throws Exception {
		// give // when
		List<IndexVO> list = dbClientDataSourceManager.selectTableIndexVOList(serverInfo,"tOrder");
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testExecuteQueryListServerInfoStringBoolean() throws Exception {
		List<Map<String,String>> list=null;
		List<String> queryList = new ArrayList<String>();
		queryList.add("INSERT INTO tService SET nServiceSeq=1, sServiceName='테스트DB'");
		queryList.add("select * from tService");
		queryList.add("explain select * from tService");
		queryList.add("update tService set sServiceName='테스트 DB 1'");
		queryList.add("delete from tService where nServiceSeq=1");

		for(String query:queryList){
			list =dbClientDataSourceManager.executeQueryList(serverInfo,new ExecuteResultListDTO(query));
			logger.debug(format("result : {} ","test code"),list);
		}
	}
}