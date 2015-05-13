package com.song7749.dl.login.dto;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
import com.sun.istack.NotNull;

/**
 * <pre>
 * Class Name : DoLoginDTO.java
 * Description : 로그인 실행 DTO 객체
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
public class DoLoginDTO extends BaseObject implements Dto{

	private static final long serialVersionUID = -582753518955573813L;

	@NotNull
	private String id;
	@NotNull
	private String password;

	public DoLoginDTO() {
	}

	public DoLoginDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
