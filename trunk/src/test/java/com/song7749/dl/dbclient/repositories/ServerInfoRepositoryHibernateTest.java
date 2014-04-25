package com.song7749.dl.dbclient.repositories;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.entities.ServerInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class ServerInfoRepositoryHibernateTest {

	@Resource
	ServerInfoRepository serverInfoRepository;

	/**
	 * fixture
	 */
	ServerInfo serverInfo;
	@Before
	public void setUp(){
		serverInfo = new ServerInfo("local-database", "dbBilling", "root", "1234", "mysql", "UTF-8","3306");
	}

	@Test
	public void testSave() throws Exception {
		serverInfoRepository.save(serverInfo);
	}
}