package com.song7749.log.service;

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

import com.song7749.log.repository.LogRepository;
import com.song7749.log.value.LogIncidentAlaramAddDto;
import com.song7749.log.value.LogLoginAddDto;
import com.song7749.log.value.LogQueryAddDto;

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
		logRepository.saveAndFlush(dto.getLogLogin(mapper));
	}

	@Async
	@Transactional(propagation=Propagation.NOT_SUPPORTED )
	@Override
	public void addQueryExecuteLog(LogQueryAddDto dto) {
		logRepository.saveAndFlush(dto.getLogQuery(mapper));
	}

	@Async
	@Transactional(propagation=Propagation.NOT_SUPPORTED )
	@Override
	public void addIncidentAlarmLog(LogIncidentAlaramAddDto dto) {
		logRepository.saveAndFlush(dto.getLogIncidentAlarm(mapper));

	}

}