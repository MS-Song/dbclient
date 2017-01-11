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

import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.repositories.ServerInfoRepository;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.member.repositories.MemberRepository;
import com.song7749.util.ProxyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/application-context.xml" })
@TransactionConfiguration(transactionManager="dbClientTransactionManager",defaultRollback=true)
@Transactional("dbClientTransactionManager")
public class ServerInfoManagerImplTest {

	@Mock
	private ServerInfoRepository serverInfoRepository;
	@Mock
	private MemberRepository memberRepository;

	@Autowired
	private ServerInfoManager serverInfoManager;

	/**
	 * fixture
	 */
	ServerInfo serverInfo;
	@Before
	public void setup() throws InvalidPropertiesFormatException, IOException{
		// give
		serverInfo = new ServerInfo(
				"10.20.10.41"
				,"테스트서버"
				, "dbclient"
				, "dbclient"
				, "1234"
				, DatabaseDriver.mysql
				, "UTF-8"
				,"3306");
		serverInfo.setServerInfoSeq(1);
	}

	@Before
	public void setupMock() throws Exception{
		MockitoAnnotations.initMocks(this);
		ServerInfoManager o = (ServerInfoManager)ProxyUtils.unwrapProxy(serverInfoManager);
		ReflectionTestUtils.setField(o, "serverInfoRepository", serverInfoRepository);
		ReflectionTestUtils.setField(o, "memberRepository", memberRepository);
	}

	@Test(expected=IllegalArgumentException.class)//then
	public void testSaveServerInfo_null_check() throws Exception {
		//give // when
		serverInfoManager.saveServerInfo(null);
	}

	@Test
	public void testSaveServerInfo() throws Exception {
		// give
		SaveServerInfoDTO saveServerInfoDTO = new SaveServerInfoDTO(
				serverInfo.getHost(),
				serverInfo.getHostAlias(),
				serverInfo.getSchemaName(),
				serverInfo.getAccount(),
				serverInfo.getPassword(),
				serverInfo.getDriver(),
				serverInfo.getCharset(),
				serverInfo.getPort());
		// when
		serverInfoManager.saveServerInfo(saveServerInfoDTO);
		// then
		verify(serverInfoRepository).save(any(ServerInfo.class));
	}

	@Test(expected=IllegalArgumentException.class)//then
	public void testModifyServerInfo_null_chcek() throws Exception {
		//give // when
		serverInfoManager.modifyServerInfo(null);
	}

	@Test
	public void testModifyServerInfo() throws Exception {
		// give
		ModifyServerInfoDTO modifyServerInfoDTO = new ModifyServerInfoDTO(
				serverInfo.getServerInfoSeq(),
				serverInfo.getHost()+"1111",
				serverInfo.getHostAlias(),
				serverInfo.getSchemaName(),
				serverInfo.getAccount(),
				serverInfo.getPassword(),
				serverInfo.getDriver(),
				serverInfo.getCharset(),
				serverInfo.getPort());

		given(serverInfoRepository.find(any(ServerInfo.class))).willReturn(serverInfo);
		// when
		serverInfoManager.modifyServerInfo(modifyServerInfoDTO);
		// then
		verify(serverInfoRepository).update(any(ServerInfo.class));
	}

	@Test
	public void testDeleteServerInfo() throws Exception {
		// give
		DeleteServerInfoDTO dto = new DeleteServerInfoDTO();
		// when
		serverInfoManager.deleteServerInfo(dto);
		// then
		verify(serverInfoRepository).delete(any(ServerInfo.class));
	}

	@Test
	public void testFindServerInfo() throws Exception {
		// give
		FindServerInfoDTO dto = new FindServerInfoDTO(1,false);
		// when
		serverInfoManager.findServerInfo(dto);
		// then
		verify(serverInfoRepository).find(any(ServerInfo.class));
	}
}