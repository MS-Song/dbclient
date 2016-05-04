package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : ModifyMemberDatabaseDTO.java
 * Description : 회원과 Database 간의 관계를 처리하는 DTO
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 5. 3.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 5. 3.
*/

@ApiModel("회원 과 Database 간의 연결 처리 DTO")
public class ModifyMemberDatabaseDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = -3558847838980242565L;

	@NotNull
	@Size(min=4,max=20)
	@ApiModelProperty(value="id",required=true)
	private String id;


	@NotNull
	@ApiModelProperty(value="서버 등록 번호",required=true)
	private Integer serverInfoSeq;

	@NotNull
	@ApiModelProperty(value="입력/삭제",required=true)
	private Boolean input;

	public ModifyMemberDatabaseDTO() {}

	public ModifyMemberDatabaseDTO(String id, Integer serverInfoSeq,
			Boolean input) {
		this.id = id;
		this.serverInfoSeq = serverInfoSeq;
		this.input = input;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public Boolean getInput() {
		return input;
	}

	public void setInput(Boolean input) {
		this.input = input;
	}
}