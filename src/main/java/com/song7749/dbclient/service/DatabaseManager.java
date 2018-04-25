package com.song7749.dbclient.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabaseRemoveDto;
import com.song7749.dbclient.value.DatabaseVo;

/**
 * <pre>
 * Class Name : DatabaseManager.java
 * Description : Database Manager
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 25.
*/
public interface DatabaseManager {

	DatabaseVo addDatabase(DatabaseAddDto dto);

	DatabaseVo modifyDatabase(DatabaseModifyDto dto);

	Integer addOrModifyDatabaseFasade(List<DatabaseModifyDto> dtos);

	void removeDatabase(DatabaseRemoveDto dto);

	Page<DatabaseVo> findDatabaseList(DatabaseFindDto dto, Pageable page);
}
