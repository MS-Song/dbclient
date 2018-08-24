package com.song7749.dbclient.service;

import org.springframework.web.multipart.MultipartFile;

import com.song7749.base.MessageVo;

/**
 * <pre>
 * Class Name : CSVFileDataManager.java
 * Description : CSV 파일을 받아 Database 에 저장 시키는 매니저
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 8. 23.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 8. 23.
*/
public interface CSVFileDataManager {

	MessageVo saveData(MultipartFile file,Long databaseId,String tableName,Boolean isCreateTable);
}