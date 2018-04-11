package com.song7749.base;

/**
 * <pre>
 * Class Name : Compare.java
 * Description : 비교 연산자
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 15.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 15.
*/

public enum Compare {
	GRAETE(">"),
	LESS("<"),
	EQUAL_GRAETE(">="),
	EQUAL_LESS("<="),
	EQUAL("="),
	LIKE ("like");

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