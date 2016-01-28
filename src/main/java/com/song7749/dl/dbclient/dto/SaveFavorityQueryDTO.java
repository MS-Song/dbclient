package com.song7749.dl.dbclient.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;

/**
 * <pre>
 * Class Name : SaveFavorityQueryDTO.java
 * Description : 즐겨 찾는 쿼리 저장 DTO 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 1. 25.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 1. 25.
*/

public class SaveFavorityQueryDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = -4662850906286708234L;

	@NotNull
	@Size(min=4,max=20)
	private String id;

	@NotNull
	@Size(min=4)
	private String memo;

	@NotNull
	@Size(min=10)
	private String query;

	@NotNull
	private Date inputDate;

	public SaveFavorityQueryDTO() {}

	public SaveFavorityQueryDTO(String id, String memo, String query,
			Date inputDate) {
		super();
		this.id = id;
		this.memo = memo;
		this.query = query;
		this.inputDate = inputDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
}