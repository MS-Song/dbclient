package com.song7749.dl.base;

import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * <pre>
 * Class Name : Compare.java
 * Description : 조건 비교 Enum 구현체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2014. 9. 15.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2014. 9. 15.
*/

public enum Compare {
	@ApiModelProperty("초과") GRAETE(">"),
	@ApiModelProperty("미만") LESS("<"),
	@ApiModelProperty("이상") EQUAL_GRAETE(">="),
	@ApiModelProperty("이하") EQUAL_LESS("<="),
	@ApiModelProperty("동등") EQUAL("=");

	private final String value;

	Compare(String value) {
		this.value=value;
	}

	public String getValue(){
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static String getValues(){
		return Compare.values().toString();
	}
}