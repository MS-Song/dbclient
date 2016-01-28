package com.song7749.dl.dbclient.dto;

import com.song7749.dl.base.AbstractDto;

/**
 * <pre>
 * Class Name : FindFavorityQueryListDTO.java
 * Description : FavorityQuery List DTO
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
public class FindFavorityQueryListDTO extends AbstractDto{

	private static final long serialVersionUID = -4346721635507503813L;

	private Integer favorityQuerySeq;

	private String id;

	public FindFavorityQueryListDTO() {}

	public FindFavorityQueryListDTO(Integer favorityQuerySeq) {
		this.favorityQuerySeq = favorityQuerySeq;
	}

	public FindFavorityQueryListDTO(String id) {
		this.id = id;
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
}