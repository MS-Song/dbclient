package com.song7749.traffic.value;

import javax.validation.constraints.NotNull;

import com.song7749.common.AbstractDto;

public class CategoryDeleteDto extends AbstractDto {

	private static final long serialVersionUID = -6844902460068720800L;

	@NotNull
	private Long id;

	private Long parentCategoryId;

	public CategoryDeleteDto() {}

	/**
	 * @param id
	 * @param parentCategoryId
	 */
	public CategoryDeleteDto(@NotNull Long id, Long parentCategoryId) {
		this.id = id;
		this.parentCategoryId = parentCategoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
}