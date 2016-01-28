package com.song7749.dl.dbclient.service;

import static com.song7749.dl.dbclient.convert.FavorityQueryConvert.convert;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.dbclient.dto.DeleteFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.FindFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.dto.ModifyFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.SaveFavorityQueryDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.dl.dbclient.repositories.FavorityQueryRepository;
import com.song7749.dl.dbclient.vo.FavorityQueryVO;
import com.song7749.dl.login.exception.AuthorityUserException;
import com.song7749.util.validate.annotation.Validate;

@Service("favorityQueryManager")
@TransactionConfiguration(transactionManager = "dbClientTransactionManager", defaultRollback = true)
public class FavorityQueryManagerImpl implements FavorityQueryManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private FavorityQueryRepository favorityQueryRepository;

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	public void saveFavorityQuery(SaveFavorityQueryDTO dto) {
		favorityQueryRepository.save(
			new FavorityQuery(
					dto.getId(),
					dto.getMemo(),
					dto.getQuery(),
					dto.getInputDate()));
	}

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	public void modifyFavorityQuery(ModifyFavorityQueryDTO dto) {
		// ID 비교를 위해 조회 한다.
		FavorityQuery favorityQuery = favorityQueryRepository.find(
				new FavorityQuery(dto.getFavorityQuerySeq()));

		// 해당하는 ID의 즐겨 찾는 쿼리만 수정 가능해야 한다.
		if(
			favorityQuery == null
				|| favorityQuery.getId() == null
				|| dto == null
				|| dto.getId() == null
				|| !favorityQuery.getId().equals(dto.getId())
		){
			throw new AuthorityUserException("사용자의 즐겨찾는 쿼리가 아닙니다.");
		}

		// 업데이트 한다.
		if(null!=favorityQuery){
			favorityQueryRepository.update(favorityQuery);
		}
	}

	@Override
	@Validate
	@Transactional("dbClientTransactionManager")
	public void deleteFavorityQuery(DeleteFavorityQueryDTO dto) {
		// ID 비교를 위해 조회 한다.
		FavorityQuery favorityQuery = favorityQueryRepository.find(
				new FavorityQuery(dto.getFavorityQuerySeq()));


		// 해당하는 ID의 즐겨 찾는 쿼리만 삭제 가능해야 한다.
		if(
			favorityQuery == null
				|| favorityQuery.getId() == null
				|| dto == null
				|| dto.getId() == null
				|| !favorityQuery.getId().equals(dto.getId())
		){
			throw new AuthorityUserException("사용자의 즐겨찾는 쿼리가 아닙니다.");
		}

		// 지운다
		if(null!=favorityQuery){
			favorityQueryRepository.delete(favorityQuery);
		}
	}

	@Override
	@Validate
	@Transactional(value="dbClientTransactionManager",readOnly=true)
	public FavorityQueryVO findFavorityQuery(FindFavorityQueryDTO dto) {
		return convert(favorityQueryRepository.find(convert(dto)));
	}

	@Override
	@Validate
	@Transactional(value="dbClientTransactionManager",readOnly=true)
	public List<FavorityQueryVO> findFavorityQueryVOList(
			FindFavorityQueryListDTO dto) {
		return convert(favorityQueryRepository.findFavorityQueryList(dto));
	}

}
