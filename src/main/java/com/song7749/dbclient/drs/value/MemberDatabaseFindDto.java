package com.song7749.dbclient.drs.value;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 Database 조회")
public class MemberDatabaseFindDto  extends AbstractDto {

	private static final long serialVersionUID = 4848783091300314515L;

	@ApiModelProperty("회원의 데이터베이스 권한 ID")
	private Long id;
	@ApiModelProperty("회원 ID")
	private Long memberId;
	@ApiModelProperty("Database ID")
	private Long DatabaseId;

	public MemberDatabaseFindDto() {}

	/**
	 * @param memberId
	 */
	public MemberDatabaseFindDto(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @param id
	 * @param memberId
	 * @param databaseId
	 */
	public MemberDatabaseFindDto(Long id, Long memberId, Long databaseId) {
		this.id = id;
		this.memberId = memberId;
		DatabaseId = databaseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getDatabaseId() {
		return DatabaseId;
	}

	public void setDatabaseId(Long databaseId) {
		DatabaseId = databaseId;
	}
}