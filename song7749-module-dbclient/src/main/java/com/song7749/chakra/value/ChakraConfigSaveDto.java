package com.song7749.chakra.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("샤크라 개인정보 동기화 설정")
public class ChakraConfigSaveDto extends AbstractDto{

	private static final long serialVersionUID = 7835050084054801380L;

	@ApiModelProperty(required=false,position=2,value="설정ID")
	private Long id;

	@NotNull
	@ApiModelProperty(required=true,position=2,value="샤크라 데이터베이스")
	private Long chakraDatabaseId;

	@NotNull
	@ApiModelProperty(required=true,position=2,value="타겟 데이터베이스")
	private Long targetDatabaseId;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiModelProperty(required=true,position=3,value="샤크라와 자동 동기화")
	private YN autoSyncYN;

	public ChakraConfigSaveDto() {}

	/**
	 * @param id
	 */
	public ChakraConfigSaveDto(Long id) {
		this.id = id;
	}

	/**
	 * @param chakraDatabaseId
	 * @param targetDatabaseId
	 * @param autoSyncYN
	 */
	public ChakraConfigSaveDto(@NotNull Long chakraDatabaseId, @NotNull Long targetDatabaseId, @NotNull YN autoSyncYN) {
		this.chakraDatabaseId = chakraDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.autoSyncYN = autoSyncYN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChakraDatabaseId() {
		return chakraDatabaseId;
	}

	public void setChakraDatabaseId(Long chakraDatabaseId) {
		this.chakraDatabaseId = chakraDatabaseId;
	}

	public Long getTargetDatabaseId() {
		return targetDatabaseId;
	}

	public void setTargetDatabaseId(Long targetDatabaseId) {
		this.targetDatabaseId = targetDatabaseId;
	}

	public YN getAutoSyncYN() {
		return autoSyncYN;
	}

	public void setAutoSyncYN(YN autoSyncYN) {
		this.autoSyncYN = autoSyncYN;
	}
}