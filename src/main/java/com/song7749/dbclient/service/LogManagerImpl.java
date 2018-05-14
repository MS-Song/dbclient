package com.song7749.dbclient.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dbclient.repository.LogRepository;
import com.song7749.dbclient.value.LogLoginAddDto;
import com.song7749.dbclient.value.LogLoginFindDto;
import com.song7749.dbclient.value.LogLoginVo;
import com.song7749.dbclient.value.LogQueryAddDto;
import com.song7749.dbclient.value.LogQueryFindDto;
import com.song7749.dbclient.value.LogQueryVo;

@Service("logManager")
public class LogManagerImpl implements LogManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogRepository logRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Async
	@Transactional(propagation=Propagation.NOT_SUPPORTED )
	@Override
	public void addLogLogin(LogLoginAddDto dto) {
		logRepository.saveAndFlush(dto.getLogLogin(mapper)).getLogLoginVo(mapper);
	}

	@Async
	@Transactional(propagation=Propagation.NOT_SUPPORTED )
	@Override
	public void addQueryExecuteLog(LogQueryAddDto dto) {
		logRepository.saveAndFlush(dto.getLogLogin(mapper)).getLogLoginVo(mapper);
	}


	@Override
	public List<LogLoginVo> findMemberLoginLogList(LogLoginFindDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogQueryVo> findQueryExecuteLog(LogQueryFindDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
}