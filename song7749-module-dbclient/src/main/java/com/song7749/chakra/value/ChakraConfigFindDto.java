package com.song7749.chakra.value;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.song7749.chakra.domain.ChakraConfig;
import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("샤크라 연동환경 조회")
public class ChakraConfigFindDto extends AbstractDto  implements Specification<ChakraConfig> {

	private static final long serialVersionUID = 8911623344598875922L;

	@ApiModelProperty(value="연동 환경 ID",position=1)
	private Long id;

	@ApiModelProperty(value="샤크라 DB 정보",position=2)
	private Long chakraDatabaseId;

	@ApiModelProperty(value="타겟 데이터베이스 정보",position=3)
	private Long targetDatabaseId;

	@ApiModelProperty(value="자동 싱크",position=4)
	private YN autoSyncYN;

	public ChakraConfigFindDto() {}

	/**
	 * @param id
	 */
	public ChakraConfigFindDto(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param chakraDatabaseId
	 * @param targetDatabaseId
	 * @param autoSyncYN
	 */
	public ChakraConfigFindDto(Long id, Long chakraDatabaseId, Long targetDatabaseId, YN autoSyncYN) {
		this.id = id;
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


	@Override
	public Predicate toPredicate(
			Root<ChakraConfig> root,
			CriteriaQuery<?> query,
			CriteriaBuilder cb) {

		Predicate p = cb.conjunction();

		if(id != null) {
			p.getExpressions()
				.add(cb.equal(root.<Long>get("id"), id));
		}

		if(null!=chakraDatabaseId) {
			p.getExpressions()
				.add(cb.equal(root.<Database>get("chakraDatabase"), new Database(chakraDatabaseId)));
		}

		if(null!=targetDatabaseId) {
			p.getExpressions()
				.add(cb.equal(root.<Database>get("targetDatabase"), new Database(targetDatabaseId)));
		}

		return p;
	}
}