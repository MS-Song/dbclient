package com.song7749.dl.dbclient.service;

import static com.song7749.dl.dbclient.service.ServerInfoConvert.convert;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.repositories.ServerInfoRepository;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.util.validate.annotation.Validate;

@Service("serverInfoManager")
@TransactionConfiguration(transactionManager = "dbClientTransactionManager", defaultRollback = true)
public class ServerInfoManagerImpl implements ServerInfoManager {

	@Resource
	private ServerInfoRepository serverInfoRepository;

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	public void saveServerInfo(SaveServerInfoDTO dto) {

		ServerInfo serverInfo = new ServerInfo(dto.getHost(),
				dto.getSchemaName(), dto.getAccount(), dto.getPassword(),
				dto.getDriver(), dto.getCharset(), dto.getPort());

		serverInfoRepository.save(serverInfo);
	}

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	public void modifyServerInfo(ModifyServerInfoDTO dto) {
		ServerInfo serverInfo = serverInfoRepository.find(new ServerInfo(dto
				.getServerInfoSeq()));
		if (null != serverInfo) {
			serverInfo.setHost(dto.getHost());
			serverInfo.setSchemaName(dto.getSchemaName());
			serverInfo.setAccount(dto.getAccount());
			serverInfo.setPassword(dto.getPassword());
			serverInfo.setDriver(dto.getDriver());
			serverInfo.setCharset(dto.getCharset());
			serverInfoRepository.update(serverInfo);
		}
	}

	@Override
	public void deleteServerInfo(DeleteServerInfoDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServerInfoVO findServerInfo(FindServerInfoDTO dto) {

		return null;
	}

	@Override
	@Transactional(value="dbClientTransactionManager",readOnly=true)
	public List<ServerInfoVO> findServerInfoList(FindServerInfoListDTO dto) {
		return convert(serverInfoRepository.findServerInfoList(dto));
	}
}