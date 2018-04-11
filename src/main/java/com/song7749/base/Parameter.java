package com.song7749.base;

/**
 * <pre>
 * Class Name : Parameter.java
 * Description : JPA Query paramter 설정 객체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 27.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 27.
*/
public class Parameter extends BaseObject{

	private static final long serialVersionUID = -4928691854934517302L;

	private String name;

	private Object value;

	public Parameter() {}

	/**
	 * @param name
	 * @param value
	 */
	public Parameter(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}