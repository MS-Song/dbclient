package com.song7749.dbclient.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyAddDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyFindDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyVo;
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

	DatabasePrivacyPolicyVo addDatabasePrivacyPolicy(DatabasePrivacyPolicyAddDto dto);

	DatabasePrivacyPolicyVo modifyDatabasePrivacyPolicy(DatabasePrivacyPolicyModifyDto dto);

	/**
	 * 일괄로 처리한다.
	 * @param list
	 */
	void addOrModifyDatabasePrivacyPolicyFasade(List<DatabasePrivacyPolicyAddDto> list);

	void removeDatabasePrivacyPolicy(Long id);

	Page<DatabasePrivacyPolicyVo> findDatabasePrivacyPolicyList(DatabasePrivacyPolicyFindDto dto, Pageable page);

	/**
	 * 개인정보 정의 복세 기능 테스트 서버 --> 운영서버  혹은 반대의 경우 사용
	 * @param sourceDatabaseId
	 * @param targetDatabaseId
	 */
	void copyDatabasePrivacyPolicyList(Long sourceDatabaseId, Long targetDatabaseId);
}