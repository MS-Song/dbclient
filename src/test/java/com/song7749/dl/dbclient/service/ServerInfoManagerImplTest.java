package com.song7749.dl.dbclient.service;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.IOException;
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

import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.repositories.ServerInfoRepository;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.util.ProxyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class ServerInfoManagerImplTest {

	@Mock
	private ServerInfoRepository serverInfoRepository;

	@Autowired
	private ServerInfoManager serverInfoManager;

	/**
	 * fixture
	 */
	SaveServerInfoDTO saveServerInfoDTO;
	ModifyServerInfoDTO modifyServerInfoDTO;
	@Before
	public void setup() throws InvalidPropertiesFormatException, IOException{
		// give
		ServerInfo serverInfo = new ServerInfo(
				"10.20.10.41"
				,"테스트서버"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");


		saveServerInfoDTO = new SaveServerInfoDTO(
				"10.20.10.41"
				,"테스트서버"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");

		modifyServerInfoDTO = new ModifyServerInfoDTO(1
				, "10.20.10.42"
				,"테스트서버"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");
	}

	@Before
	public void setupMock() throws Exception{
		MockitoAnnotations.initMocks(this);
		ServerInfoManager o = (ServerInfoManager)ProxyUtils.unwrapProxy(serverInfoManager);
		ReflectionTestUtils.setField(o, "serverInfoRepository", serverInfoRepository);
	}

	@Test
	public void testSaveServerInfo() throws Exception {
		// give // when
		serverInfoManager.saveServerInfo(saveServerInfoDTO);
		// then
		verify(serverInfoRepository).save(any(ServerInfo.class));
	}

	@Test
	public void testModifyServerInfo() throws Exception {
		// give
		ServerInfo serverInfo = new ServerInfo(
				"10.20.10.41"
				,"테스트서버"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");

		given(serverInfoRepository.find(any(ServerInfo.class))).willReturn(serverInfo);
		// when
		serverInfoManager.modifyServerInfo(modifyServerInfoDTO);
		// then
		verify(serverInfoRepository).update(any(ServerInfo.class));
	}
}