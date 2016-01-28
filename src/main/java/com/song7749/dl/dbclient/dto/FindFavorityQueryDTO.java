package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;

/**
 * <pre>
 * Class Name : FindFavorityQueryDTO.java
 * Description : 즐겨 찾는 쿼리 검색 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 1. 27.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 1. 27.
*/
public class FindFavorityQueryDTO extends AbstractDto{

	private static final long serialVersionUID = 6093631138950496503L;

	@NotNull
	private Integer favorityQuerySeq;

	public FindFavorityQueryDTO() {}

	public FindFavorityQueryDTO(Integer favorityQuerySeq) {
		this.favorityQuerySeq = favorityQuerySeq;
	}

	public Integer getFavorityQuerySeq() {
		return favorityQuerySeq;
	}

	public void setFavorityQuerySeq(Integer favorityQuerySeq) {
		this.favorityQuerySeq = favorityQuerySeq;
	}
}
