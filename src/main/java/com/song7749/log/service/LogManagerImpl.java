package com.song7749.log.service;

import static com.song7749.log.convert.MemberLoginLogConvert.convert;
import static com.song7749.log.convert.QueryExecuteLogConvert.convert;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.dto.SaveMemberLoginLogDTO;
import com.song7749.log.dto.SaveQueryExecuteLogDTO;
import com.song7749.log.repositories.MemberLoginLogRepository;
import com.song7749.log.repositories.QueryExecuteLogRepository;
import com.song7749.log.vo.MemberLoginLogVO;
import com.song7749.log.vo.QueryExecuteLogVO;

@Service("logManager")
public class LogManagerImpl implements LogManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private MemberLoginLogRepository memberLoginLogRepository;

	@Resource
	private QueryExecuteLogRepository queryExecuteLogRepository;


	@Override
	@Transactional("dbClientLogTransactionManager")
	public void saveMemberLoginLog(SaveMemberLoginLogDTO dto) {
		memberLoginLogRepository.save(convert(dto));
	}

	@Override
	@Transactional(value="dbClientLogTransactionManager",readOnly=true)
	public List<MemberLoginLogVO> findMemberLoginLogList(
			FindMemberLoginLogListDTO dto) {
		return convert(memberLoginLogRepository.findMemberLoginLogList(dto));
	}

	@Override
	@Transactional("dbClientLogTransactionManager")
	public void saveQueryExecuteLog(SaveQueryExecuteLogDTO dto) {
		queryExecuteLogRepository.save(convert(dto));
	}

	@Override
	@Transactional(value="dbClientLogTransactionManager",readOnly=true)
	public List<QueryExecuteLogVO> findQueryExecuteLog(
			FindQueryExecuteLogListDTO dto) {
		return convert(queryExecuteLogRepository.findQueryExecuteLogList(dto));
	}
}