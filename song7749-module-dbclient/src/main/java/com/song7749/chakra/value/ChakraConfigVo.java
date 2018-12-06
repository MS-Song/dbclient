package com.song7749.chakra.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.song7749.common.AbstractVo;
import com.song7749.common.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ChakraConfigVo extends AbstractVo{

	private static final long serialVersionUID = -3721496184590255201L;

	@ApiModelProperty(position=1,value="설정ID")
	private Long id;

	@ApiModelProperty(required=true,position=2,value="샤크라 데이터베이스")
	private Long chakraDatabaseId;

	@ApiModelProperty(required=true,position=2,value="타겟 데이터베이스")
	private Long targetDatabaseId;

	@Enumerated(EnumType.STRING)
	@ApiModelProperty(position=3,value="샤크라와 자동 동기화")
	private YN autoSync;

	@ApiModelProperty(position=4,value="동기화 에러 메세지")
	private String errorMessage;


	public ChakraConfigVo() {}

	/**
	 * @param id
	 * @param chakraDatabaseId
	 * @param targetDatabaseId
	 * @param autoSync
	 * @param errorMessage
	 */
	public ChakraConfigVo(Long id, Long chakraDatabaseId, Long targetDatabaseId, YN autoSync, String errorMessage) {
		this.id = id;
		this.chakraDatabaseId = chakraDatabaseId;
		this.targetDatabaseId = targetDatabaseId;
		this.autoSync = autoSync;
		this.errorMessage = errorMessage;
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

	public YN getAutoSync() {
		return autoSync;
	}

	public void setAutoSync(YN autoSync) {
		this.autoSync = autoSync;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}