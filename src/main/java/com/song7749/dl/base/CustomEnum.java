package com.song7749.dl.base;

/**
 * <pre>
 * Class Name : CustomEnum.java
 * Description : Code 와 Name 으로 구성된 Enum 클래스는 반드시 CustomEnum 을 구현해야 한다.
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 28.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 28.
*/
public interface CustomEnum {
	/**
	 * 코드값 반환
	 * @return int
	 */
	public int getCode();

	/**
	 * 코드명 반환
	 * @return String
	 */
	public String getName();
}
