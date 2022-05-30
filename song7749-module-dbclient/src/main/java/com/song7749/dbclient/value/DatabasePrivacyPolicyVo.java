package com.song7749.dbclient.value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.common.base.AbstractVo;
import com.song7749.common.base.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("개인정보 정의 모델")
public class DatabasePrivacyPolicyVo extends AbstractVo{

	private static final long serialVersionUID = 5721445638954417408L;

	@ApiModelProperty(position=1,value="ID||개인정보  정의 모델 ID")
	private Long id;

	@ApiModelProperty(position=2,value="Table Name||데이터베이스 테이블 명칭")
	private String tableName;

	@ApiModelProperty(position=3,value="Field Name||데이터베이스 필드 명칭")
	private String fieldName;

	@ApiModelProperty(position=4,value="EnableYN ||개인정보 식별 YN")
	private YN enableYN;

	@ApiModelProperty(position=5,value="Comment||EX) 전화번호, 휴대폰번호, 성명, 메일 등")
	private String comment;

	@ApiModelProperty(position=6,value="Create Date||생성일자")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;

	@ApiModelProperty(position=7,value="Modify Date||수정일자")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date updateDate;

	@ApiModelProperty(position=8,value="Database || 연결된 Database")
	private DatabaseVo databaseVo;

	public DatabasePrivacyPolicyVo() {}

	/**
	 * @param id
	 * @param tableName
	 * @param fieldName
	 * @param enableYN
	 * @param comment
	 * @param createDate
	 * @param updateDate
	 * @param databaseVo
	 */
	public DatabasePrivacyPolicyVo(Long id, String tableName, String fieldName, YN enableYN, String comment,
			Date createDate, Date updateDate, DatabaseVo databaseVo) {
		this.id = id;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.databaseVo = databaseVo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public DatabaseVo getDatabaseVo() {
		return databaseVo;
	}

	public void setDatabaseVo(DatabaseVo databaseVo) {
		this.databaseVo = databaseVo;
	}
}