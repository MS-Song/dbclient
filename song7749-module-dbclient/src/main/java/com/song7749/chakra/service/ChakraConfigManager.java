package com.song7749.chakra.service;

import com.song7749.chakra.value.ChakraConfigFindDto;
import com.song7749.chakra.value.ChakraConfigSaveDto;
import com.song7749.chakra.value.ChakraConfigVo;
import com.song7749.common.MessageVo;

/**
 * <pre>
 * Class Name : ChakraConfigManager.java
 * Description : Chakra Config 에 대한 매니저
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 11. 1.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 11. 1.
*/

public interface ChakraConfigManager {

	/**
	 * 샤크라 개인정보 설정 저장
	 * @param dto
	 * @return
	 */
	MessageVo saveChakraConfig(ChakraConfigSaveDto dto);

	/**
	 * 샤크라 게인정보 설정 조회
	 * @param ChakraConfigFindDto
	 * @return
	 */
	ChakraConfigVo getChakraConfig(ChakraConfigFindDto dto);


	/**
	 * 샤크라 개인정보 설정과 Dbclient 개인정보 설정 간의 동기화
	 * @param ChakraConfigFindDto
	 * @return
	 */
	MessageVo syncChakraPrivacyPolicy(ChakraConfigFindDto dto);
}