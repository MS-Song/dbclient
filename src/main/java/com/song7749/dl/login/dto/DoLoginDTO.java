package com.song7749.dl.login.dto;

import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
import com.sun.istack.NotNull;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

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
@ApiModel
public class DoLoginDTO extends BaseObject implements Dto{

	private static final long serialVersionUID = -582753518955573813L;

	@NotNull
	@Size(min=4,max=20)
	@ApiModelProperty(value="로그인 id",required=true)
	private String id;

	@NotNull
	@Size(min=8,max=20)
	@ApiModelProperty(value="비밀번호",required=true)
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
