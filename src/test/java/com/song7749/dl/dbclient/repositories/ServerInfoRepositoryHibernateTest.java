package com.song7749.dl.dbclient.repositories;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;

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
	public void setUp() throws InvalidPropertiesFormatException, IOException{
		Properties prop = new Properties();
		prop.loadFromXML(ClassLoader.getSystemResource("properties/dbProperties.xml").openStream());
		serverInfo = new ServerInfo(prop.getProperty("dbClient.database.host")
				, prop.getProperty("dbClient.database.schemaName")
				, prop.getProperty("dbClient.database.username")
				, prop.getProperty("dbClient.database.password")
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");
	}

	@Test
	public void testSave() throws Exception {
		serverInfoRepository.save(serverInfo);
	}

	@Test
	public void testDelete() throws Exception {
		ServerInfo deleteServerInfo = new ServerInfo();
		serverInfoRepository.delete(deleteServerInfo);
	}
}