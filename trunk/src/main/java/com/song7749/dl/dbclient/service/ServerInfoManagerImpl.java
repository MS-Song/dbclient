package com.song7749.dl.dbclient.service;

import static com.song7749.dl.dbclient.service.ServerInfoConvert.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	public void deleteServerInfo(DeleteServerInfoDTO dto) {
		logger.debug("delete serverInfo {}",dto);
		serverInfoRepository.delete(new ServerInfo(dto.getServerInfoSeq()));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	public ServerInfoVO findServerInfo(FindServerInfoDTO dto) {
		ServerInfo serverInfo = new ServerInfo(dto.getServerInfoSeq());
		return convert(serverInfoRepository.find(serverInfo));
	}

	@Override
	@Validate
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	public List<ServerInfoVO> findServerInfoList(FindServerInfoListDTO dto) {
		return convert(serverInfoRepository.findServerInfoList(dto));
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	public void saveServerInfoFacade(List<SaveServerInfoDTO> list) {
		for (SaveServerInfoDTO saveServerInfoDTO : list) {
			saveServerInfo(saveServerInfoDTO);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	public void modifyServerInfoFacade(List<ModifyServerInfoDTO> list) {
		for (ModifyServerInfoDTO modifyServerInfoDTO : list) {
			modifyServerInfo(modifyServerInfoDTO);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	public void deleteServerInfoFacade(List<DeleteServerInfoDTO> list) {
		for (DeleteServerInfoDTO deleteServerInfoDTO : list) {
			deleteServerInfo(deleteServerInfoDTO);
		}
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	public List<DeleteServerInfoDTO> getPossibleDeleteServerInfoList(List<DeleteServerInfoDTO> list){
		List<ServerInfoVO> findList = findServerInfoList(new FindServerInfoListDTO());
		Iterator<ServerInfoVO> iterator = findList.iterator();

		logger.debug("find All serverInfo List {}",findList);

		// 존재하는 것은 제외하기 위해 처리 -- 삭제될 리스트만 남긴다.
		for(DeleteServerInfoDTO dto : list){
			for(;iterator.hasNext();){
				if(iterator.next().getServerInfoSeq().equals(
								dto.getServerInfoSeq())){

					iterator.remove();
				}
			}
		}

		// 삭제되고 남은 리스트 형 변환
		List<DeleteServerInfoDTO> deleteList = new ArrayList<DeleteServerInfoDTO>();
		for(ServerInfoVO vo : findList){
			deleteList.add(new DeleteServerInfoDTO(vo.getServerInfoSeq()));
		}

		logger.debug("delete serverInfo List {}",deleteList);

		return deleteList;
	}

	@Override
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Validate(VG = { ValidateGroupSelect.class })
	public List<TableVO> findTableVOList(FindTableDTO dto) {

		return dbClientDataSourceManager.selectTableVOList(serverInfoRepository
				.find(new ServerInfo(dto.getServerInfoSeq())));
	}

	@Override
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Validate
	public List<FieldVO> findTableFieldVOList(FindTableDTO dto) {

		return dbClientDataSourceManager
				.selectTableFieldVOList(serverInfoRepository
						.find(new ServerInfo(dto.getServerInfoSeq())), dto
						.getTableName());
	}

	@Override
	@Transactional(value = "dbClientTransactionManager", readOnly = true)
	@Validate
	public List<IndexVO> findTableIndexVOList(FindTableDTO dto) {

		return dbClientDataSourceManager
				.selectTableIndexVOList(serverInfoRepository
						.find(new ServerInfo(dto.getServerInfoSeq())), dto
						.getTableName());
	}

	@Override
	@Transactional(value = "dbClientTransactionManager")
	@Validate
	public List<Map<String, String>> executeResultList(ExecuteResultListDTO dto) {
		return dbClientDataSourceManager.executeQueryList(serverInfoRepository.find(new ServerInfo(dto.getServerInfoSeq())), dto);
	}
}