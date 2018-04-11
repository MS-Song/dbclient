package com.song7749.dbclient.drs.value;

import javax.validation.constraints.NotNull;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModelProperty;

public class MemberSaveQueryRemoveDto  extends AbstractDto {

	private static final long serialVersionUID = 5238334585376051997L;

	@ApiModelProperty(value="회원ID, 없을 경우 로그인 ID에서 추출",hidden=true)
	private Long memberId;

	@ApiModelProperty("Query ID")
	@NotNull
	private Long id;

	public MemberSaveQueryRemoveDto() {}

	/**
	 * @param memberId
	 * @param id
	 */
	public MemberSaveQueryRemoveDto(Long memberId, @NotNull Long id) {
		this.memberId = memberId;
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public Long getId() {
		return id;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setId(Long id) {
		this.id = id;
	}
}