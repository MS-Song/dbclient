package com.song7749.chakra.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.song7749.chakra.domain.ChakraConfig;
import com.song7749.chakra.repository.ChakraConfigRepository;
import com.song7749.chakra.value.ChakraConfigFindDto;
import com.song7749.chakra.value.ChakraConfigSaveDto;
import com.song7749.chakra.value.ChakraConfigVo;
import com.song7749.common.MessageVo;
import com.song7749.common.YN;
import com.song7749.common.validate.Validate;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.DatabasePrivacyPolicy;
import com.song7749.dbclient.repository.DatabasePrivacyPolicyRepository;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.dbclient.value.FieldVo;
import com.song7749.member.service.LoginSession;


/**
 * <pre>
 * Class Name : ChakraConfigManagerImpl.java
 * Description : Chakra Config Manager 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 11. 2.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 11. 2.
*/

@Service
public class ChakraConfigManagerImpl implements ChakraConfigManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ChakraConfigRepository chakraConfigRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	DatabasePrivacyPolicyRepository databasePrivacyPolicyRepository;

	@Autowired
	LoginSession loginSession;

	@Autowired
	DBclientManager dbClientManager;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;


	@Validate
	@Transactional
	@Override
	public synchronized MessageVo saveChakraConfig(ChakraConfigSaveDto dto) {
		ChakraConfig cc = null;
		if(null!=dto.getId()) {
			Optional<ChakraConfig> oCC = chakraConfigRepository.findById(dto.getId());
			if(oCC.isPresent()) {
				cc = oCC.get();
			} else {
				throw new IllegalArgumentException(dto.getId() + "][ChakraConfig] 존재하지 않는 환경 변수 입니다. ");
			}
		} else {
			cc = new ChakraConfig();
		}

		// 자동 동기화 값 설정
		cc.setAutoSyncYN(dto.getAutoSyncYN());

		// chakra 데이터베이스 설정
		if(null!=dto.getChakraDatabaseId()) {
			Optional<Database> oDB = databaseRepository.findById(dto.getChakraDatabaseId());
			if(!oDB.isPresent()) {
				throw new IllegalArgumentException(dto.getChakraDatabaseId() + "][ChakraDatabaseId] 존재하지 않는 데이터베이스 ID 입니다. ");
			} else {
				cc.setChakraDatabase(oDB.get());
			}
		}

		// target 데이터베이스 설정
		if(null!=dto.getTargetDatabaseId()) {
			Optional<Database> oDB = databaseRepository.findById(dto.getTargetDatabaseId());
			if(!oDB.isPresent()) {
				throw new IllegalArgumentException(dto.getTargetDatabaseId() + "][TargetDatabaseId] 존재하지 않는 데이터베이스 ID 입니다. ");
			} else {
				cc.setTargetDatabase(oDB.get());
			}
		}

		chakraConfigRepository.saveAndFlush(cc);
		return new MessageVo(HttpStatus.OK.value(), 1, "Chakra 연결 정보가 저정되었습니다.");
	}

	@Override
	@Validate
	@Transactional(readOnly=true)
	public synchronized ChakraConfigVo getChakraConfig(ChakraConfigFindDto dto) {
		List<ChakraConfig> list = chakraConfigRepository.findAll(dto);
		if(!CollectionUtils.isEmpty(list)) {
			ChakraConfigVo vo = mapper.map(list.get(0), ChakraConfigVo.class);
			vo.setChakraDatabaseId(list.get(0).getChakraDatabase().getId());
			vo.setTargetDatabaseId(list.get(0).getTargetDatabase().getId());
			return vo;
		}
		return null;
	}

	@Override
	@Validate
	@Transactional
	public MessageVo syncChakraPrivacyPolicy(ChakraConfigFindDto dto) {
		int insertRows = 0;
		int updateRows = 0;
		// 샤크라 개인저보 설정 정보를 가져온다.
		List<ChakraConfig> list = chakraConfigRepository.findAll(dto);
		if(CollectionUtils.isEmpty(list)) {
			return new MessageVo(HttpStatus.NO_CONTENT.value(),"샤크라 동기화 설정이 없습니다.");
		}

		// 타겟을 지정하지 않을 경우에 모든 DB에 대해서 수행하며, 타겟이 지정되면, 특정 DB 에 대해서만 수행 한다.
		for(ChakraConfig cc : list) {

			// 샤크라 DB와 연결하여 개인정보 설정을 조회 한다.
			ExecuteQueryDto executeDto = new ExecuteQueryDto();
			executeDto.setId(cc.getChakraDatabase().getId());
			executeDto.setUsePLSQL(false);
			executeDto.setUseCache(true);
			executeDto.setUseLimit(false);
			executeDto.setAutoCommit(false);
			if(null!=loginSession
					&& null!=loginSession.getLogin()) {
				executeDto.setLoginId(loginSession.getLogin().getLoginId());
				executeDto.setIp(loginSession.getLogin().getIp());
			} else {
				executeDto.setLoginId("SYSTEM");
				executeDto.setIp("SYSTEM-JOB");
			}
			executeDto.setQuery("select  * from sensitive_object");
			MessageVo chakraMappingData = dbClientManager.executeQuery(executeDto);

			// 샤크라 데이터를 FieldVo 형식으로 가공 한다.
			List<FieldVo> chakraVoList = new ArrayList<FieldVo>();
			List<Map<String,String>> chakraList = (List<Map<String,String>>)chakraMappingData.getContents();

			for(Map<String,String> data : chakraList) {
				if(StringUtils.isBlank(data.get("column_name"))){
					FieldVo vo  = new FieldVo();
					vo.setTableName(data.get("object_name"));
					vo.setColumnName("*");
					chakraVoList.add(vo);
				} else {
					String[] columns = data.get("column_name").split(",");
					for(String c : columns) {
						FieldVo vo  = new FieldVo();
						vo.setTableName(data.get("object_name"));
						vo.setColumnName(c);
						chakraVoList.add(vo);
					}
				}

			}

			// 타겟 데이터베이스의 모든 필드 정보를 힉득 한다.
			executeDto.setId(cc.getTargetDatabase().getId());
			List<FieldVo> targetVoList = dbClientManager.selectAllFieldList(executeDto);

			// 샤크라 DB의 개인정보 설정과 해당 DB의 Databae 테이블/필드를 매치 한다.
			List<DatabasePrivacyPolicy> ppList = new ArrayList<DatabasePrivacyPolicy>();
			for(FieldVo c : chakraVoList) {
				for(FieldVo t : targetVoList) {
					// 테이블 명칭이 일치할 경우
					if(c.getTableName().equals(t.getTableName())) {
						// 모든 필드 저장 시....
						if(c.getColumnName().equals("*")
								|| c.getColumnName().equals(t.getColumnName())) {
							DatabasePrivacyPolicy dpp = new DatabasePrivacyPolicy();
							dpp.setDatabase(cc.getTargetDatabase());
							dpp.setEnableYN(YN.Y);
							dpp.setFieldName(t.getColumnName());
							dpp.setTableName(t.getTableName());
							dpp.setComment("chakra");
							ppList.add(dpp);
						}
					}
				}
			}

			logger.trace(format("{}", "ChakraDB Match TargetDB"),ppList);

			// 데이터를 sync 한다.
			for(DatabasePrivacyPolicy savePreparedDpp : ppList) {
				Optional<DatabasePrivacyPolicy> oDpp = databasePrivacyPolicyRepository.findOne(Example.of(savePreparedDpp));
				// 이미 존재 하는 경우에 skip.
				if(oDpp.isPresent()) {
					updateRows++;
					// skip
					//logger.trace(format("{}", "chakra sync skip"),oDpp);
				} else { // 저장되지 않은 경우에만 추가 저장
//					databasePrivacyPolicyRepository.saveAndFlush(savePreparedDpp);
//					logger.trace(format("{}", "chakra sync add"),savePreparedDpp);
					insertRows++;
				}
			}
			logger.trace(format("insert : {}, update {} ", "ChakraDB insert rows"),insertRows,updateRows);

			// 업데이트 되지 않고 남은 데이터 들은 없어진 것들임으로.. 제거 한다.
			// 샤크라 연동 데이터 전체를 로딩 한다.
			List<DatabasePrivacyPolicy> dppList = databasePrivacyPolicyRepository.findAll();
			// 제거 대상 객체를 담기..
			List<DatabasePrivacyPolicy> removeList = new ArrayList<DatabasePrivacyPolicy>();
			for(DatabasePrivacyPolicy aleadyDpp : dppList) {
				boolean isRemoved = true;
				for(DatabasePrivacyPolicy savePreparedDpp : ppList) {
					if(aleadyDpp.equals(savePreparedDpp)) {
						isRemoved=false;
					}
				}
				if(isRemoved
					&& aleadyDpp.getComment().equals("chakra")) {
					removeList.add(aleadyDpp);
				}
			}
			// 제거 실행
			logger.trace(format("{}", "ChakraDB remove rows"),removeList);
			databasePrivacyPolicyRepository.deleteAll(removeList);
		}
		return new MessageVo(HttpStatus.OK.value(), insertRows+insertRows, "샤크라 연동이 " + insertRows+insertRows + " 건 완료 되었습니다.");
	}
}