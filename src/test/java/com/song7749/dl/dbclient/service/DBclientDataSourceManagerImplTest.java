package com.song7749.dl.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.DatabaseDdlVO;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ProcedureVO;
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
		// 서버 인포 설정
		serverInfo = new ServerInfo(
						"127.0.0.1"
						, "테스트 서버"
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
		// give
		serverInfo.setName("tOrder");
		// when
		List<FieldVO> list = dbClientDataSourceManager.selectTableFieldVOList(serverInfo);
		// then
		assertThat(list, notNullValue());

	}

	@Test
	public void testSelectTableIndexVOList() throws Exception {
		// give
		serverInfo.setName("tOrder");
		// when
		List<IndexVO> list = dbClientDataSourceManager.selectTableIndexVOList(serverInfo);
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testExecuteQueryListServerInfoStringBoolean() throws Exception {
		List<Map<String,String>> list=null;
		List<String> queryList = new ArrayList<String>();
		queryList.add("INSERT INTO serverinfo SET account='dbclient' , charset='UTF-8' , driver='mysql' , host='127.0.0.1' , password='1234', port='3306', schemaName='dbclient'");
		queryList.add("select * from serverinfo");
		queryList.add("explain select * from serverinfo");
		queryList.add("update serverinfo set host='locahost'");
		queryList.add("delete from serverinfo");

		for(String query:queryList){
			list =dbClientDataSourceManager.executeQueryList(serverInfo,
					new ExecuteResultListDTO(
							serverInfo.getServerInfoSeq(), serverInfo.getHost(),
							serverInfo.getSchemaName(), serverInfo.getAccount(), false, query, false, false,"song7749", "127.0.0.1"));
			logger.debug(format("{}",""),list);
		}
		assertTrue(true);
		// 로그 기록 시간을 번다
		Thread.sleep(2000);
	}

	@Test
	public void testSelectShowCreateTable() throws Exception {
		// give
		serverInfo.setName("account");
		// when
		List<DatabaseDdlVO> list = dbClientDataSourceManager.selectShowCreateTable(serverInfo);
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testSelectAllFieldList() throws Exception {
		// give
		serverInfo.setSchemaName("dbclient");
		// when
		List<FieldVO> list = dbClientDataSourceManager.selectAllFieldList(serverInfo);
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testSelectProcedureVOList() throws Exception {
		// give
		// when
		List<ProcedureVO> list = dbClientDataSourceManager.selectProcedureVOList(serverInfo);
		// then
		assertThat(list, notNullValue());
	}

	@Test
	public void testSelectProcedureVODetailList() throws Exception {
		// give
		serverInfo.setName("string_print");
		// when
		List<Map<String,String>> list = dbClientDataSourceManager.selectProcedureDetailList(serverInfo);

		assertThat(list, notNullValue());
	}

	@Test
	public void testSelectProcedureVOSourceList() throws Exception {
		// give
		serverInfo.setName("string_print");
		// when
		List<ProcedureVO> list = dbClientDataSourceManager.selectProcedureVOSourceList(serverInfo);
		logger.trace(format("{}",""),list);
		assertThat(list, notNullValue());
	}


}