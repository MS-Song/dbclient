package com.song7749.traffic.value;

import javax.validation.constraints.NotBlank;

import com.song7749.common.AbstractDto;

import io.swagger.annotations.ApiModel;

@ApiModel("카테고리 저장 모델")
public class CategorySaveDto extends AbstractDto{

	private static final long serialVersionUID = 7220263723523578812L;

	private Long id;

	@NotBlank
	private String name;

	private String desc;

	private Long parentCategoryId;

	public CategorySaveDto() {}

	public CategorySaveDto(Long id, @NotBlank String name, String desc, Long parentCategoryId) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.parentCategoryId = parentCategoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
}
