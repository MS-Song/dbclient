package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : RemoveMemberDTO.java
 * Description : 회원 삭제 DTO
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
public class RemoveMemberDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = 8220095335803571308L;


	@NotNull
	@Size(min=8,max=20)
	@ApiModelProperty(value="ID",required=true)
	private String id;

	public RemoveMemberDTO() {}

	public RemoveMemberDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}