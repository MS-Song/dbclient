package com.song7749.dbclient.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.common.base.AbstractDto;
import com.song7749.common.base.YN;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("개인정보 정의 모델 추가")
public class DatabasePrivacyPolicyAddDto extends AbstractDto {

	private static final long serialVersionUID = 8550395030670032844L;

	@Length(max = 255)
	@NotBlank
	@ApiModelProperty(required=true,position=2,value="Table Name||데이터베이스 테이블 명칭")
	private String tableName;

	@Length(max = 255)
	@NotBlank
	@ApiModelProperty(required=true,position=3,value="Field Name||데이터베이스 필드 명칭")
	private String fieldName;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiModelProperty(required=true,position=4,value="EnableYN ||개인정보 식별 YN")
	private YN enableYN;

	@Length(max = 255)
	@ApiModelProperty(required=true,position=5,value="Comment||EX) 전화번호, 휴대폰번호, 성명, 메일 등")
	private String comment;

	@NotNull
	@ApiModelProperty(required=true,position=8,value="Database || 연결된 Database ID")
	private Long databaseId;

	public DatabasePrivacyPolicyAddDto() {}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param enableYN
	 * @param comment
	 * @param databaseId
	 */
	public DatabasePrivacyPolicyAddDto(@Length(max = 255) @NotBlank String tableName,
			@Length(max = 255) @NotBlank String fieldName, @NotNull YN enableYN,
			@Length(max = 255) String comment, @NotNull Long databaseId) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
		this.databaseId = databaseId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public YN getEnableYN() {
		return enableYN;
	}

	public void setEnableYN(YN enableYN) {
		this.enableYN = enableYN;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}
}