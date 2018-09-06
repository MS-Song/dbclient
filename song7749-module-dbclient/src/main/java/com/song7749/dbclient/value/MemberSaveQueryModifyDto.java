package com.song7749.dbclient.value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.common.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 저장된 쿼리 수정")
public class MemberSaveQueryModifyDto  extends AbstractDto {

	private static final long serialVersionUID = -1731451356144987473L;

	@ApiModelProperty(value="회원ID, 없을 경우 로그인 ID에서 추출",hidden=true)
	private Long memberId;

	@ApiModelProperty("Query ID")
	@NotNull
	private Long id;

	@ApiModelProperty("Query 메모")
	@NotBlank
	@Size(min = 4)
	private String memo;

	@ApiModelProperty("Query 내용")
	@NotBlank
	@Size(min = 10, max=12000)
	private String query;

	public MemberSaveQueryModifyDto() {}

	/**
	 * @param memberId
	 * @param id
	 * @param memo
	 * @param query
	 */
	public MemberSaveQueryModifyDto(Long memberId, @NotNull Long id, @NotBlank @Size(min = 4) String memo,
			@NotBlank @Size(min = 10, max = 12000) String query) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.memo = memo;
		this.query = query;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getId() {
		return id;
	}

	public String getMemo() {
		return memo;
	}

	public String getQuery() {
		return query;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}