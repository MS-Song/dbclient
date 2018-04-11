package com.song7749.base;

/**
 * <pre>
 * Class Name : CustomEnum.java
 * Description : ENUM 는 CustomEnum 을  구현해야 한다.
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
public interface CustomEnum {
	/**
	 * ENUM Code 첫번째 인자는 반드시 Code 로 구성한다.
	 * @return int
	 */
	public int getCode();

	/**
	 * ENUM Name 두번째 인자는 반드시 Name 로 구성한다.
	 * @return String
	 */
	public String getName();
}
