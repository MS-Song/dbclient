package com.song7749.dl.dbclient.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

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
	 * database table information
	 * @param serverInfo
	 * @return List<TableVO>
	 */
	List<TableVO> selectTableVOList(ServerInfo serverInfo);

	/**
	 * database field information
	 * @param serverInfo
	 * @param tableName
	 * @return List<FieldVO>
	 */
	List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo, String tableName);

	/**
	 * database index information
	 * @param serverInfo
	 * @param tableName
	 * @return List<IndexVO>
	 */
	List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo, String tableName);

	/**
	 * result set List
	 * @param serverInfo
	 * @param dto
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> executeQueryList(ServerInfo serverInfo,
			ExecuteResultListDTO dto);
}