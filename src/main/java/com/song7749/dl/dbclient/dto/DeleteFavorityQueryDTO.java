package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeleteFavorityQueryDTO {
	@NotNull
	private Integer favorityQuerySeq;

	/**
	 * 해당 하는 ID 의 정보만 수정 될 수 있게 로그인 정보에서 획득해야 한다.
	 */
	@NotNull
	@Size(min=4,max=20)
	private String id;

	public DeleteFavorityQueryDTO() {}

	public DeleteFavorityQueryDTO(Integer favorityQuerySeq, String id) {
		this.favorityQuerySeq = favorityQuerySeq;
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
