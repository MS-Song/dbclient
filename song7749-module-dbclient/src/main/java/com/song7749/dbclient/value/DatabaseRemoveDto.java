package com.song7749.dbclient.value;

import javax.validation.constraints.NotNull;

import com.song7749.common.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("database 정보 삭제")
public class DatabaseRemoveDto extends AbstractDto {

	private static final long serialVersionUID = -3769900444482363606L;

	@ApiModelProperty(required=true, value="데이터베이스 ID")
	@NotNull
	private Long id;

	public DatabaseRemoveDto() {}

	/**
	 * @param id
	 */
	public DatabaseRemoveDto(@NotNull Long id) {
		this.id = id;
	}

	/**
	 * @param apikey
	 * @param id
	 */
	public DatabaseRemoveDto(String apikey, @NotNull Long id) {
		super(apikey);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}