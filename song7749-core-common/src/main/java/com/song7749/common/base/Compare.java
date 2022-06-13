package com.song7749.common.base;

/**
 * <pre>
 * Class Name : Compare.java
 * Description : 비교
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
	GRAETE_EQUAL(">="),
	LESS("<"),
	LESS_EQUAL("<="),
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