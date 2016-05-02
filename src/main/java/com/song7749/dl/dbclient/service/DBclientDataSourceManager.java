package com.song7749.dl.dbclient.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.FunctionVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ProcedureVO;
import com.song7749.dl.dbclient.vo.SequenceVO;
import com.song7749.dl.dbclient.vo.TableVO;
import com.song7749.dl.dbclient.vo.TriggerVO;
import com.song7749.dl.dbclient.vo.ViewVO;

/**
 * <pre>
 * Class Name : DBclientDataSourceManager.java
 * Description : DBclientDataSourceManager
 * 데이터 베이스 관련 기능을 관리하는 매니저
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 4. 27.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 4. 27.
 */
public interface DBclientDataSourceManager {

	/**
	 * serverInfo connection get
	 * @param serverInfo
	 * @return Connection
	 * @throws SQLException
	 */
	Connection getConnection(ServerInfo serverInfo) throws SQLException;

	/**
	 * connection close
	 * @param serverInfo
	 * @return boolean
	 * @throws SQLException
	 */
	boolean closeConnection(ServerInfo serverInfo) throws SQLException;

	/**
	 * database table information
	 * @param serverInfo
	 * @return List<TableVO>
	 */
	List<TableVO> selectTableVOList(ServerInfo serverInfo);

	/**
	 * database field information
	 * @param serverInfo
	 * @return List<FieldVO>
	 */
	List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo);

	/**
	 * database index information
	 * @param serverInfo
	 * @return List<IndexVO>
	 */
	List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo);

	/**
	 * result set List
	 * 유저가 실행 요청한 쿼리를 실행하고 실행 결과를 리턴 한다.
	 * @param serverInfo
	 * @param dto
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> executeQueryList(ServerInfo serverInfo,
			ExecuteResultListDTO dto);


	/**
	 * database view search
	 * @param serverInfo
	 * @return List<ViewVO>
	 */
	List<ViewVO> selectViewVOList(ServerInfo serverInfo);

	/**
	 * database view detail search
	 * @param serverInfo
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectViewDetailList(ServerInfo serverInfo);

	/**
	 * database view source search
	 * @param serverInfo
	 * @return List<ViewVO>
	 */
	List<ViewVO> selectViewVOSourceList(ServerInfo serverInfo);

	/**
	 * database stored procedure search
	 * @param serverInfo
	 * @return List<ProcedureVO>
	 */
	List<ProcedureVO> selectProcedureVOList(ServerInfo serverInfo);

	/**
	 * database stored procedure Detail search
	 * @param serverInfo
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectProcedureDetailList(ServerInfo serverInfo);

	/**
	 * database stored procedure source search
	 * @param serverInfo
	 * @return List<ProcedureVO>
	 */
	List<ProcedureVO> selectProcedureVOSourceList(ServerInfo serverInfo);

	/**
	 * database function search
	 * @param serverInfo
	 * @return List<FunctionVO>
	 */
	List<FunctionVO> selectFunctionVOList(ServerInfo serverInfo);

	/**
	 * database function Detail search
	 * @param serverInfo
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectFunctionDetailList(ServerInfo serverInfo);

	/**
	 * database function Detail search
	 * @param serverInfo
	 * @return List<FunctionVO>
	 */
	List<FunctionVO> selectFunctionVOSourceList(ServerInfo serverInfo);

	/**
	 * database trigger search
	 * @param serverInfo
	 * @return List<TriggerVO>
	 */
	List<TriggerVO> selectTriggerVOList(ServerInfo serverInfo);

	/**
	 * database trigger Detail search
	 * @param serverInfo
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectTriggerDetailList(ServerInfo serverInfo);

	/**
	 * database trigger Detail search
	 * @param serverInfo
	 * @return List<FunctionVO>
	 */
	List<TriggerVO> selectTriggerVOSourceList(ServerInfo serverInfo);


	/**
	 * Sequence List search
	 * @param serverInfo
	 * @return List<SequenceVO>
	 */
	List<SequenceVO> selectSequenceVOList(ServerInfo serverInfo);

	/**
	 * database Sequence Detail search
	 * @param serverInfo
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectSequenceDetailList(ServerInfo serverInfo);

	/**
	 * 유저가 실행한 쿼리를 취소 한다.
	 * @param serverInfo
	 * @param dto
	 */
	void killQuery(ServerInfo serverInfo, ExecuteResultListDTO dto);
}