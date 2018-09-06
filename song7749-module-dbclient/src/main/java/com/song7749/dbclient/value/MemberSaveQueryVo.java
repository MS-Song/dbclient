package com.song7749.dbclient.value;

import java.util.Date;

import com.song7749.common.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 저장되어 있는 SQL")
public class MemberSaveQueryVo extends AbstractVo {

	private static final long serialVersionUID = 3268956526572478996L;

	@ApiModelProperty("저장되어 있는 Query ID")
	private Long id;

	@ApiModelProperty("메모")
	private String memo;

	@ApiModelProperty("SQL")
	private String query;

	@ApiModelProperty("생성일")
	private Date createDate;

	@ApiModelProperty("수정일")
	private Date modifyDate;

	public MemberSaveQueryVo() {
	}

	/**
	 * @param id
	 * @param memo
	 * @param query
	 * @param createDate
	 * @param modifyDate
	 * @param databaseVo
	 */
	public MemberSaveQueryVo(Long id, String memo, String query, Date createDate, Date modifyDate) {
		this.id = id;
		this.memo = memo;
		this.query = query;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
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

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
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

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
