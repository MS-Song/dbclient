package com.song7749.dl.dbclient.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;

/**
 * <pre>
 * Class Name : ModifyFavorityQueryDTO.java
 * Description : 즐겨 찾는 쿼리 수정 객체
 * ID 는 변경 할 수 없음
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
public class ModifyFavorityQueryDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = 4304469670165099267L;

	@NotNull
	private Integer favorityQuerySeq;

	/**
	 * 해당 하는 ID 의 정보만 수정 될 수 있게 로그인 정보에서 획득해야 한다.
	 */
	@NotNull
	@Size(min=4,max=20)
	private String id;

	private String memo;

	private String query;

	private Date inputDate;

	public ModifyFavorityQueryDTO() {}

	public ModifyFavorityQueryDTO(Integer favorityQuerySeq, String id,
			String memo, String query, Date inputDate) {
		this.favorityQuerySeq = favorityQuerySeq;
		this.id = id;
		this.memo = memo;
		this.query = query;
		this.inputDate = inputDate;
	}

	public Integer getFavorityQuerySeq() {
		return favorityQuerySeq;
	}

	public void setFavorityQuerySeq(Integer favorityQuerySeq) {
		this.favorityQuerySeq = favorityQuerySeq;
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