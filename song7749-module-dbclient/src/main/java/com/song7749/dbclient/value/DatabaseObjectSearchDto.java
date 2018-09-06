package com.song7749.dbclient.value;

import javax.validation.constraints.NotNull;

import com.song7749.common.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Database Object Search")
public class DatabaseObjectSearchDto  extends AbstractDto {

	private static final long serialVersionUID = -5148556905392769778L;

	@NotNull
	@ApiModelProperty("Database ID")
	private Long id;

	@ApiModelProperty("Database Object Name")
	private String name;

	public DatabaseObjectSearchDto() {}

	/**
	 * @param apikey
	 * @param databaseId
	 * @param name
	 */
	public DatabaseObjectSearchDto(String apikey, @NotNull Long id, String name) {
		super(apikey);
		this.id = id;
		this.name = name;
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
}