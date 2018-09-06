package com.song7749.dbclient.value;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.song7749.common.AbstractDto;
import com.song7749.common.Compare;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.DatabasePrivacyPolicy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("개인정보 정의 모델 검색")
public class DatabasePrivacyPolicyFindDto extends AbstractDto implements Specification<DatabasePrivacyPolicy> {

	private static final long serialVersionUID = -3314296469511533681L;

	@ApiModelProperty(position=1,value="ID")
	private Long id;

	@ApiModelProperty(position=2,value="Table Name")
	private String tableName;

	@ApiModelProperty(hidden=true)
	private Compare tableNameCompare = Compare.EQUAL;

	@ApiModelProperty(position=3,value="Field Name")
	private String fieldName;

	@ApiModelProperty(hidden=true)
	private Compare fieldNameCompare = Compare.EQUAL;

	@ApiModelProperty(position=4,value="EnableYN")
	private YN enableYN;

	@ApiModelProperty(position=5,value="Comment")
	private String comment;

	@ApiModelProperty(position=8,value="Database")
	private Long databaseId;


	public DatabasePrivacyPolicyFindDto() {}

	/**
	 * @param id
	 */
	public DatabasePrivacyPolicyFindDto(Long id) {
		this.id = id;
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

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public Compare getTableNameCompare() {
		return tableNameCompare;
	}

	public void setTableNameCompare(Compare tableNameCompare) {
		this.tableNameCompare = tableNameCompare;
	}

	public Compare getFieldNameCompare() {
		return fieldNameCompare;
	}

	public void setFieldNameCompare(Compare fieldNameCompare) {
		this.fieldNameCompare = fieldNameCompare;
	}

	@Override
	public Predicate toPredicate(Root<DatabasePrivacyPolicy> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		Predicate p = cb.conjunction();


		if(id != null) {
			p.getExpressions()
				.add(cb.equal(root.<Long>get("id"), id));
		}

		if(!StringUtils.isEmpty(tableName)) {
			if(Compare.LIKE.equals(tableNameCompare)) {
				p.getExpressions().add(cb.like(root.<String>get("tableName"),  "%" + tableName + "%"));
			} else {
				p.getExpressions().add(cb.equal(root.<String>get("tableName"), tableName));
			}
		}

		if(!StringUtils.isEmpty(fieldName)) {
			if(Compare.LIKE.equals(fieldNameCompare)) {
				p.getExpressions().add(cb.like(root.<String>get("fieldName"),  "%" + fieldName + "%"));
			} else {
				p.getExpressions().add(cb.equal(root.<String>get("fieldName"), fieldName));
			}
		}

		if(!StringUtils.isEmpty(comment)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("comment"),  "%" + comment + "%"));
		}

		if(null!=enableYN) {
			p.getExpressions()
				.add(cb.equal(root.<YN>get("enableYN"), enableYN));
		}

		if(null!=databaseId) {
			p.getExpressions()
				.add(cb.equal(root.<Database>get("database"), new Database(databaseId)));
		}
		return p;
	}
}