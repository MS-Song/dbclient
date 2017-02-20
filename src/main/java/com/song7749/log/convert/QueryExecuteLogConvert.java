package com.song7749.log.convert;

import java.util.ArrayList;
import java.util.List;

import com.song7749.log.dto.SaveQueryExecuteLogDTO;
import com.song7749.log.entities.QueryExecuteLog;
import com.song7749.log.vo.QueryExecuteLogVO;

/**
 * <pre>
 * Class Name : QueryExecuteLogConvert.java
 * Description : 쿼리 실행로그 객체 convert
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public class QueryExecuteLogConvert {


	public static QueryExecuteLog convert(SaveQueryExecuteLogDTO dto){
		if(null==dto){
			return null;
		}
		return new QueryExecuteLog(
				dto.getId(),
				dto.getIp(),
				dto.getHost(),
				dto.getHostAlias(),
				dto.getSchemaName(),
				dto.getAccount(),
				// 실행 쿼리의 사이즈가 4k 이하로 되게 처리 한다.
				dto.getQuery().substring(0, dto.getQuery().length() > 4000 ? 4000 : dto.getQuery().length() ),
				dto.getExecuteDate());
	}

	public static QueryExecuteLogVO convert(QueryExecuteLog queryExecuteLog){
		if(null==queryExecuteLog){
			return null;
		}
		return new QueryExecuteLogVO(
				queryExecuteLog.getQueryExeucteSeq(),
				queryExecuteLog.getId(),
				queryExecuteLog.getIp(),
				queryExecuteLog.getHost(),
				queryExecuteLog.getHostAlias(),
				queryExecuteLog.getSchemaName(),
				queryExecuteLog.getAccount(),
				queryExecuteLog.getQuery(),
				queryExecuteLog.getExecuteDate());
	}

	public static List<QueryExecuteLogVO> convert(List<QueryExecuteLog> list){
		if(null==list){
			return null;
		}

		List<QueryExecuteLogVO> rList = new ArrayList<QueryExecuteLogVO>();
		for(QueryExecuteLog qel:list){
			rList.add(convert(qel));
		}

		return rList;
	}
}