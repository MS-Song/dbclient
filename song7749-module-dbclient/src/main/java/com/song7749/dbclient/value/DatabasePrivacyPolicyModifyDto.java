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

@ApiModel("개인정보 정의 모델 수정")
public class DatabasePrivacyPolicyModifyDto  extends AbstractDto {

	private static final long serialVersionUID = 8146631320290612714L;

	@NotNull
	@ApiModelProperty(required=true,position=1,value="ID||개인정보  정의 모델 ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiModelProperty(required=true,position=4,value="EnableYN ||개인정보 식별 YN")
	private YN enableYN;

	@Length(max = 255)
	@NotBlank
	@ApiModelProperty(required=true,position=5,value="Comment||EX) 전화번호, 휴대폰번호, 성명, 메일 등")
	private String comment;

	@NotNull
	@ApiModelProperty(required=true,position=8,value="Database || 연결된 Database ID")
	private Long databaseId;

	public DatabasePrivacyPolicyModifyDto() {}

	/**
	 * @param id
	 * @param enableYN
	 * @param comment
	 * @param databaseId
	 */
	public DatabasePrivacyPolicyModifyDto(@NotNull Long id, @NotNull YN enableYN,
			@Length(max = 255) @NotBlank String comment, @NotNull Long databaseId) {
		this.id = id;
		this.enableYN = enableYN;
		this.comment = comment;
		this.databaseId = databaseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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