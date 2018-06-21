package com.song7749.dbclient.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.DatabasePrivacyPolicy;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.repository.DatabasePrivacyPolicyRepository;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberRepository;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyAddDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyFindDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyVo;
import com.song7749.dbclient.value.DatabaseRemoveDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.util.validate.Validate;

/**
 * <pre>
 * Class Name : databaseManager.java
 * Description : Database 관련 처리를 담당하는 Manager 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 23.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 23.
*/

@Service("databaseManager")
public class DatabaseManagerImpl implements DatabaseManager {

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	DatabasePrivacyPolicyRepository dppRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	LoginSession loginSession;


	@Override
	@Validate
	@Transactional
	public DatabaseVo addDatabase(DatabaseAddDto dto) {
		if(StringUtils.isBlank(dto.getSchemaOwner())) {
			dto.setSchemaOwner(dto.getAccount());
		}

		return databaseRepository
				.saveAndFlush(mapper.map(dto, Database.class))
				.getDatabaseVo(mapper);
	}

	@Override
	@Validate
	@Transactional
	public DatabaseVo modifyDatabase(DatabaseModifyDto dto) {
		Optional<Database> oDatabase = databaseRepository.findById(dto.getId());
		if(!oDatabase.isPresent()) {
			throw new IllegalArgumentException("Database 가 존재하지 않습니다.");
		}

		if(StringUtils.isBlank(dto.getSchemaOwner())) {
			dto.setSchemaOwner(dto.getAccount());
		}

		Database database = oDatabase.get();
		mapper.map(dto,database);

		return databaseRepository
				.saveAndFlush(database)
				.getDatabaseVo(mapper);
	}

	@Override
	@Validate
	@Transactional
	public Integer addOrModifyDatabaseFasade(List<DatabaseModifyDto> dtos) {
		List<Database> databases = new ArrayList<Database>();
		for(DatabaseModifyDto dto : dtos) {
			if(StringUtils.isBlank(dto.getSchemaOwner())) {
				dto.setSchemaOwner(dto.getAccount());
			}
			// TODO Database 에 대한 조회가 빈번 할 수 있다 ID 기반으로 대량 조회하는 것을 고민해볼 필요가 있다.
			Optional<Database> oDatabase = databaseRepository.findById(dto.getId());
			if(oDatabase.isPresent()) {
				Database database = oDatabase.get();
				mapper.map(dto,database);
				databases.add(database);
			}
		}

		databaseRepository.saveAll(databases);
		databaseRepository.flush();

		return databases.size();
	}

	@Override
	@Validate
	@Transactional
	public void removeDatabase(DatabaseRemoveDto dto) {
		databaseRepository.deleteById(dto.getId());
		databaseRepository.flush();
	}

	@Override
	@Validate
	@Transactional(readOnly=true)
	public Page<DatabaseVo> findDatabaseList(DatabaseFindDto dto, Pageable page) {
		Page<Database> pageList = null;
		if(!CollectionUtils.isEmpty(dto.getIds())) {
			pageList = databaseRepository.findByIdIn(dto.getIds(),page);
		} else {
			Database database = mapper.map(dto, Database.class);
			Example<Database> example = Example.of(database);
			pageList = databaseRepository.findAll(example, page);
		}
		return pageList.map(
			new Function<Database, DatabaseVo>() {
				@Override
				public DatabaseVo apply(Database t) {
					return mapper.map(t, DatabaseVo.class);
				}
			}
		);
	}

	@Validate
	@Transactional
	@Override
	public DatabasePrivacyPolicyVo addDatabasePrivacyPolicy(DatabasePrivacyPolicyAddDto dto) {
		DatabasePrivacyPolicy dpp = mapper.map(dto, DatabasePrivacyPolicy.class);
		Optional<Database> oDB = databaseRepository.findById(dto.getDatabaseId());

		if(oDB.isPresent()) {
			dpp.setDatabase(oDB.get());
		} else {
			throw new IllegalArgumentException("존재하지 않는 데이터베이스 ID 입니다.");
		}

		// 수정자를 저장 한다.
		Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
		dpp.setLastModifyMember(oMember.get());

		return mapper.map(dppRepository.saveAndFlush(dpp),DatabasePrivacyPolicyVo.class);
	}

	@Validate
	@Transactional
	@Override
	public DatabasePrivacyPolicyVo modifyDatabasePrivacyPolicy(DatabasePrivacyPolicyModifyDto dto) {
		Optional<DatabasePrivacyPolicy> oDpp = dppRepository.findById(dto.getId());
		DatabasePrivacyPolicy dpp = null;
		if(oDpp.isPresent()) {
			dpp = oDpp.get();
			mapper.map(dto, dpp);
		}

		// 수정자를 저장 한다.
		Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
		dpp.setLastModifyMember(oMember.get());

		DatabasePrivacyPolicyVo vo = mapper.map(dppRepository.saveAndFlush(dpp),DatabasePrivacyPolicyVo.class);
		vo.setDatabaseVo(dpp.getDatabase().getDatabaseVo(mapper));
		return vo;
	}

	@Validate
	@Transactional
	@Override
	public void removeDatabasePrivacyPolicy(Long id) {
		Optional<DatabasePrivacyPolicy> oDpp = dppRepository.findById(id);
		if(oDpp.isPresent()) {
			dppRepository.delete(oDpp.get());
		} else {
			throw new IllegalArgumentException("존재하지 않는 개인정보 식별 ID="+id+" 입니다.");
		}

	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<DatabasePrivacyPolicyVo> findDatabasePrivacyPolicyList(DatabasePrivacyPolicyFindDto dto, Pageable page) {
		return dppRepository.findAll(dto, page).map(
				new Function<DatabasePrivacyPolicy, DatabasePrivacyPolicyVo>() {
					@Override
					public DatabasePrivacyPolicyVo apply(DatabasePrivacyPolicy t) {
						DatabasePrivacyPolicyVo vo = mapper.map(t, DatabasePrivacyPolicyVo.class);
						vo.setDatabaseVo(t.getDatabase().getDatabaseVo(mapper));
						return vo;
					}
				});
	}

	@Validate
	@Transactional
	@Override
	public void copyDatabasePrivacyPolicyList(Long sourceDatabaseId, Long targetDatabaseId) {
		Example<DatabasePrivacyPolicy> example = Example.of(new DatabasePrivacyPolicy(sourceDatabaseId));
		// 소스 정보를 가져온다.
		List<DatabasePrivacyPolicy> sourceList = dppRepository.findAll(example);

		// 타겟 데이터베이스 정볼르 가져온다.
		Optional<Database> oDatabase = databaseRepository.findById(sourceDatabaseId);
		// 현재 세션 사용자를 조회 한다.
		Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());

		for(DatabasePrivacyPolicy dpp : sourceList) {
			DatabasePrivacyPolicy dppTarget = new DatabasePrivacyPolicy(
					dpp.getTableName(),
					dpp.getFieldName(),
					dpp.getEnableYN(),
					dpp.getComment(),
					oDatabase.get(),
					oMember.get());
			dppRepository.saveAndFlush(dppTarget);
		}
	}

	@Override
	@Transactional
	public void addOrModifyDatabasePrivacyPolicyFasade(List<DatabasePrivacyPolicyAddDto> list) {
		// 개인정보 테이블을 조회 한다 -- 개인정보 필드는 많지 않다. 전체 조회 한다.
		List<DatabasePrivacyPolicy> dppList = dppRepository.findAll();

		// 삭제 대상을 먼저 정리한다.
		List<Long> deleteDatabaseIds = new ArrayList<Long>();
		for(DatabasePrivacyPolicy dpp : dppList) {
			boolean isAready = false;
			for(DatabasePrivacyPolicyAddDto dto : list) {
				if(dpp.getTableName().equals(dto.getTableName())
						&& dpp.getFieldName().equals(dto.getFieldName())) {
					// 존재 한다.
					isAready=true;
					break;
				}
			}
			if(isAready==false) {
				deleteDatabaseIds.add(dpp.getId());
			}
		}
		// 먼저 삭제 처리 한다.
		for(Long id : deleteDatabaseIds) {
			dppRepository.deleteById(id);
		}
		dppRepository.flush();

		// 추가대상을찾는다.
		List<DatabasePrivacyPolicyAddDto> addlist = new ArrayList<DatabasePrivacyPolicyAddDto>();
		for(DatabasePrivacyPolicyAddDto dto : list) {
			boolean isAready = false;
			for(DatabasePrivacyPolicy dpp : dppList) {
				if(dpp.getTableName().equals(dto.getTableName())
						&& dpp.getFieldName().equals(dto.getFieldName())) {
					// 존재 한다.
					isAready=true;
					break;
				}
			}
			if(isAready==false) {
				addlist.add(dto);
			}
		}
		//추가 한다.
		for(DatabasePrivacyPolicyAddDto dto : addlist) {
			addDatabasePrivacyPolicy(dto);
		}
	}
}