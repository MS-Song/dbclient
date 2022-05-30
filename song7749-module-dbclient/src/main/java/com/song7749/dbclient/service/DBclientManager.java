package com.song7749.dbclient.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.song7749.common.base.MessageVo;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseDdlVo;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.dbclient.value.FieldVo;
import com.song7749.dbclient.value.FunctionVo;
import com.song7749.dbclient.value.IndexVo;
import com.song7749.dbclient.value.ProcedureVo;
import com.song7749.dbclient.value.SequenceVo;
import com.song7749.dbclient.value.TableVo;
import com.song7749.dbclient.value.TriggerVo;
import com.song7749.dbclient.value.ViewVo;

/**
 * <pre>
 * Class Name : DBclienManager.java
 * Description : dto Shema info 와 connection pool 등을 관리 한다.
 *
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 22.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 22.
*/
public interface DBclientManager {

	/**
	 * get connection
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection(Database database) throws SQLException;

	/**
	 * close connection
	 * SQL 실행 중에 종료가 일어나면 안된다. 앞에서 SQL의 사용 여부를 확인 후에 종료 해야 한다.
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	boolean closeConnection(Database database) throws SQLException;

	/**
	 * Database Test Connection.
	 * UI connection test click. database server connect test. will be sucess.
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	MessageVo testConnection(DatabaseAddDto dto)  throws SQLException;

	/**
	 * dto table information
	 * @param dto
	 * @return
	 */
	List<TableVo> selectTableVoList(ExecuteQueryDto dto);

	/**
	 * dto field information
	 * @param dto
	 * @return
	 */
	List<FieldVo> selectTableFieldVoList(ExecuteQueryDto dto);

	/**
	 * dto index information
	 * @param dto
	 * @return
	 */
	List<IndexVo> selectTableIndexVoList(ExecuteQueryDto dto);

	/**
	 * dto view search
	 * @param dto
	 * @return List<ViewVO>
	 */
	List<ViewVo> selectViewVoList(ExecuteQueryDto dto);

	/**
	 * dto view detail search
	 * @param dto
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectViewDetailList(ExecuteQueryDto dto);

	/**
	 * dto view source search
	 * @param dto
	 * @return List<ViewVO>
	 */
	List<ViewVo> selectViewVoSourceList(ExecuteQueryDto dto);

	/**
	 * dto stored procedure search
	 * @param dto
	 * @return List<ProcedureVO>
	 */
	List<ProcedureVo> selectProcedureVoList(ExecuteQueryDto dto);

	/**
	 * dto stored procedure Detail search
	 * @param dto
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectProcedureDetailList(ExecuteQueryDto dto);

	/**
	 * dto stored procedure source search
	 * @param dto
	 * @return List<ProcedureVO>
	 */
	List<ProcedureVo> selectProcedureVoSourceList(ExecuteQueryDto dto);

	/**
	 * dto function search
	 * @param dto
	 * @return List<FunctionVO>
	 */
	List<FunctionVo> selectFunctionVoList(ExecuteQueryDto dto);

	/**
	 * dto function Detail search
	 * @param dto
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectFunctionDetailList(ExecuteQueryDto dto);

	/**
	 * dto function Detail search
	 * @param dto
	 * @return List<FunctionVO>
	 */
	List<FunctionVo> selectFunctionVoSourceList(ExecuteQueryDto dto);

	/**
	 * dto trigger search
	 * @param dto
	 * @return List<TriggerVO>
	 */
	List<TriggerVo> selectTriggerVoList(ExecuteQueryDto dto);

	/**
	 * dto trigger Detail search
	 * @param dto
	 * @return List<Map<String,String>>
	 */
	List<Map<String,String>> selectTriggerDetailList(ExecuteQueryDto dto);

	/**
	 * dto trigger Detail search
	 * @param dto
	 * @return List<FunctionVO>
	 */
	List<TriggerVo> selectTriggerVoSourceList(ExecuteQueryDto dto);


	/**
	 * Sequence List search
	 * @param dto
	 * @return
	 */
	List<SequenceVo> selectSequenceVoList(ExecuteQueryDto dto);

	/**
	 * dto Sequence Detail search
	 * @param dto
	 * @return
	 */
	List<Map<String,String>> selectSequenceDetailList(ExecuteQueryDto dto);

	/**
	 * dto table DDLquery Search
	 * @param dto
	 * @return
	 */
	List<DatabaseDdlVo> selectShowCreateTable(ExecuteQueryDto dto);

	/**
	 * 자동완성을 위해 테이블의 모든 필드 리스트와 comment 를 조회 한다.
	 * @param dto
	 * @return
	 */
	List<FieldVo> selectAllFieldList(ExecuteQueryDto dto);

	/**
	 * result set List
	 * 유저가 실행 요청한 쿼리를 실행하고 실행 결과를 리턴 한다.
	 * @param dto
	 * @return
	 */
	MessageVo executeQuery(ExecuteQueryDto dto);

	/**
	 * 유저가 실행한 쿼리를 취소 한다.
	 * @param dto
	 */
	void killQuery(ExecuteQueryDto dto);

	/**
	 * Database ID 를 이용해서 Database 객체 조회
	 * @param databaseId
	 * @return
	 */
	Database getDatabase(Long databaseId);
}