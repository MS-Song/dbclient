package com.song7749.dl.dbclient.service;

import static com.song7749.dl.dbclient.convert.ServerInfoConvert.convert;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.FindTableDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.repositories.ServerInfoRepository;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.dl.dbclient.vo.TableVO;
import com.song7749.dl.dbclient.vo.ViewVO;
import com.song7749.util.validate.ValidateGroupSelect;
import com.song7749.util.validate.annotation.Validate;


@Service("serverInfoManager")
@TransactionConfiguration(transactionManager = "dbClientTransactionManager", defaultRollback = true)
public class ServerInfoManagerImpl implements ServerInfoManager {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private ServerInfoRepository serverInfoRepository;

	@Resource
	private DBclientDataSourceManager dbClientDataSourceManager;

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void saveServerInfo(SaveServerInfoDTO dto) {

		ServerInfo serverInfo = new ServerInfo(dto.getHost(), dto.getHostAlias(),
				dto.getSchemaName(), dto.getAccount(), dto.getPassword(),
				dto.getDriver(), dto.getCharset(), dto.getPort());

		serverInfoRepository.save(serverInfo);
	}

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void modifyServerInfo(ModifyServerInfoDTO dto) {
		ServerInfo serverInfo = serverInfoRepository.find(new ServerInfo(dto
				.getServerInfoSeq()));
		if (null != serverInfo) {
			serverInfo.setHost(dto.getHost());
			serverInfo.setHostAlias(dto.getHostAlias());
			serverInfo.setSchemaName(dto.getSchemaName());
			serverInfo.setAccount(dto.getAccount());
			serverInfo.setPassword(dto.getPassword());
			serverInfo.setDriver(dto.getDriver());
			serverInfo.setCharset(dto.getCharset());
			serverInfo.setPort(dto.getPort());;
			serverInfoRepository.update(serverInfo);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void deleteServerInfo(DeleteServerInfoDTO dto) {
		serverInfoRepository.delete(new ServerInfo(dto.getServerInfoSeq()));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public ServerInfoVO findServerInfo(FindServerInfoDTO dto) {
		ServerInfo serverInfo = new ServerInfo(dto.getServerInfoSeq());
		return convert(serverInfoRepository.find(serverInfo));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public List<ServerInfoVO> findServerInfoList(FindServerInfoListDTO dto) {
		return convert(serverInfoRepository.findServerInfoList(dto));
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void saveServerInfoFacade(List<SaveServerInfoDTO> list) {
		for (SaveServerInfoDTO saveServerInfoDTO : list) {
			saveServerInfo(saveServerInfoDTO);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void modifyServerInfoFacade(List<ModifyServerInfoDTO> list) {
		for (ModifyServerInfoDTO modifyServerInfoDTO : list) {
			modifyServerInfo(modifyServerInfoDTO);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void deleteServerInfoFacade(List<DeleteServerInfoDTO> list) {
		for (DeleteServerInfoDTO deleteServerInfoDTO : list) {
			deleteServerInfo(deleteServerInfoDTO);
		}
	}

	@Override
	@Validate(VG = { ValidateGroupSelect.class })
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public List<TableVO> findTableVOList(FindTableDTO dto) {

		return dbClientDataSourceManager.selectTableVOList(serverInfoRepository
				.find(new ServerInfo(dto.getServerInfoSeq())));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public List<FieldVO> findTableFieldVOList(FindTableDTO dto) {

		return dbClientDataSourceManager
				.selectTableFieldVOList(serverInfoRepository
						.find(new ServerInfo(dto.getServerInfoSeq())), dto
						.getTableName());
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public List<IndexVO> findTableIndexVOList(FindTableDTO dto) {

		return dbClientDataSourceManager
				.selectTableIndexVOList(serverInfoRepository
						.find(new ServerInfo(dto.getServerInfoSeq())), dto
						.getTableName());
	}

	@Override
	@Validate(VG = { ValidateGroupSelect.class })
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Cacheable(cacheName="com.song7749.cache.serverInfo.cache",cacheableInteceptorName="cacheAbleInterceptorImpl")
	public List<ViewVO> findViewVOList(FindTableDTO dto) {

		return dbClientDataSourceManager
				.selectViewVOList(serverInfoRepository
						.find(new ServerInfo(dto.getServerInfoSeq())));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager")
	public List<Map<String, String>> executeResultList(ExecuteResultListDTO dto) {
		return dbClientDataSourceManager.executeQueryList(serverInfoRepository.find(new ServerInfo(dto.getServerInfoSeq())), dto);
	}

	@Override
	@TriggersRemove(cacheName="com.song7749.cache.serverInfo.cache",removeAll=true)
	public void clearCache() {}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	public void killExecutedQuery(ExecuteResultListDTO dto) {
		dbClientDataSourceManager.killQuery(serverInfoRepository.find(new ServerInfo(dto.getServerInfoSeq())), dto);
	}
}
