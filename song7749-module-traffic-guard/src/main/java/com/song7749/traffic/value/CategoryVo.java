package com.song7749.traffic.value;

import com.song7749.common.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("카테고리")
public class CategoryVo extends AbstractVo{

	private static final long serialVersionUID = -5565475077052892508L;

	@ApiModelProperty(value="카테고리 ID")
	private Long id;

	@ApiModelProperty(value="카테고리 명칭")
	private String name;

	@ApiModelProperty(value="카테고리 설명")
	private String desc;

	public CategoryVo() {}

	/**
	 * @param id
	 * @param name
	 * @param desc
	 */
	public CategoryVo(Long id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
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
}