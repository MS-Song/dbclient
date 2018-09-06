package com.song7749.mail.service;

import com.song7749.common.MessageVo;
import com.song7749.mail.value.MailConfigDto;
import com.song7749.mail.value.MailConfigVo;

/**
 * <pre>
 * Class Name : CommonConfigService.java
 * Description : 서비스의 공통 설정을 서비스한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 29.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 29.
*/
public interface EmailConfigService {

	/**
	 * 메일 환경을 테스트 한다.
	 * @param dto
	 * @return
	 */
	MessageVo testMailConfig(MailConfigDto dto);

	/**
	 * 메일 환경을 저장 한다.
	 * 1개의 config 만 저장 되도록 처리 한다.
	 * @param dto
	 * @return
	 */
	MailConfigVo saveMailConfig(MailConfigDto dto);

	/**
	 * 메일 환경을 불러온다.
	 * @return
	 */
	MailConfigVo findMailConfig();
}
