package com.song7749.dl.login.exception;

/**
 * <pre>
 * Class Name : AdminUserException.java
 * Description : 관리자가 아닐 경우에 발생하는 익셉션
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 5. 13.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 5. 13.
*/
public class AdminUserException extends RuntimeException{

	private static final long serialVersionUID = -3787440008426318275L;

	public AdminUserException(){
		super("관리자만 접근 가능합니다.");
	}

	public AdminUserException(String message){
		super(message);
	}
}