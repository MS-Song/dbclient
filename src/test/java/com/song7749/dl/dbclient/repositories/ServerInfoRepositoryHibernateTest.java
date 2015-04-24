package com.song7749.dl.dbclient.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class ServerInfoRepositoryHibernateTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	ServerInfoRepository serverInfoRepository;

	@Before
	public void setUp(){

	}

	@Test
	public void CURDFasade() throws Exception{
		ServerInfo serverInfo = testSave();
		testFind(serverInfo);
		testFindServerInfoList(serverInfo);
		testDelete(serverInfo);
	}

	public ServerInfo testSave() throws Exception {
		// give
		ServerInfo serverInfo = new ServerInfo("10.20.10.41"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");


		// when
		serverInfoRepository.save(serverInfo);

		// then
		logger.trace("\nseq : {}",serverInfo.getServerInfoSeq());
		assertThat(serverInfo.getServerInfoSeq(), notNullValue());

		return serverInfo;
	}

	public void testDelete(ServerInfo serverInfo) throws Exception {
		// give // when
		serverInfoRepository.delete(serverInfo);
		serverInfoRepository.getSesson().flush();

		// then
		logger.trace("\nserverInfo : {}",serverInfo);
	}

	public ServerInfo testFind(ServerInfo serverInfo) throws Exception {
		// give // when
		ServerInfo returnInfo = serverInfoRepository.find(serverInfo);
		// then
		assertThat(serverInfo.getServerInfoSeq(), is(returnInfo.getServerInfoSeq()));

		return returnInfo;
	}


	public void testFindServerInfoList(ServerInfo serverInfo) throws Exception {
		// give
		FindServerInfoListDTO dto = new FindServerInfoListDTO();
		dto.setAccount(serverInfo.getAccount());
		dto.setHost(serverInfo.getHost());
		dto.setSchemaName(serverInfo.getSchemaName());

		// when
		List<ServerInfo> infoList = serverInfoRepository.findServerInfoList(dto);
		// then
		logger.trace("\ninfoList : {}",infoList);
	}
}