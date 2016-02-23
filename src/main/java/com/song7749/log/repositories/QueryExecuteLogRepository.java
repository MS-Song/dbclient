package com.song7749.log.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.entities.QueryExecuteLog;

/**
 * <pre>
 * Class Name : QueryExecuteLogRepository.java
 * Description :  Query 실행 로그를 기록하는 Repository
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 22.
*/
public interface QueryExecuteLogRepository extends Repository<QueryExecuteLog>{

	/**
	 * QueryExecuteLog 리스트 조회
	 * @param dto
	 * @return List<QueryExecuteLog>
	 */
	List<QueryExecuteLog> findQueryExecuteLogList(FindQueryExecuteLogListDTO dto);

}
