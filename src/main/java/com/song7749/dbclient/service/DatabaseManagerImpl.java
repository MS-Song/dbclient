package com.song7749.dbclient.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
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
public class DatabaseManagerImpl implements DatabaseManager{

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	@Override
	@Validate
	@Transactional
	public DatabaseVo addDatabase(DatabaseAddDto dto) {
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

}