package com.song7749.member.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : AuthType.java
 * Description : 회원 권한 타입
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 29.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 29.
*/

@ApiModel(description="회원 권한 타입")
public enum AuthType {
	@ApiModelProperty("관리자") ADMIN,
	@ApiModelProperty("일반") NORMAL
}