package com.song7749.dbclient.value;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.song7749.base.AbstractDto;
import com.song7749.base.Compare;
import com.song7749.dbclient.domain.Member;
import com.song7749.dbclient.type.AuthType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MemberFindDto.java
 * Description : Member 검색용 DTO
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 27.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 27.
*/
@ApiModel("회원정보 조회")
public class MemberFindDto extends AbstractDto implements Specification<Member> {

	private static final long serialVersionUID = -111258667094644234L;

	@ApiModelProperty("회원ID")
	private Long id;

	@ApiModelProperty("회원IDs")
	private List<Long> ids;

	@ApiModelProperty("로그인ID")
	private String loginId;

	@ApiModelProperty(value="로그인 ID 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare loginIdCompare = Compare.LIKE;

	@ApiModelProperty("인증키")
	private String certificationKey;

	@ApiModelProperty("권한")
	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@ApiModelProperty("팀명")
	private String teamName;
	@ApiModelProperty(value="팀명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare teamNameCompare = Compare.LIKE;

	@ApiModelProperty("성명")
	private String name;
	@ApiModelProperty(value="성명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare nameCompare = Compare.LIKE;

	@ApiModelProperty("핸드폰 번호")
	private String mobileNumber;
	@ApiModelProperty(value="핸드폰 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare mobileNumberCompare = Compare.LIKE;


	public MemberFindDto() {}

	/**
	 * @param id
	 */
	public MemberFindDto(Long id) {
		this.id = id;
	}

	/**
	 * @param loginId
	 */
	public MemberFindDto(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @param name
	 * @param nameCompare
	 */
	public MemberFindDto(String name, Compare nameCompare) {
		this.name = name;
		this.nameCompare = nameCompare;
	}

	/**
	 * @param id
	 * @param ids
	 * @param loginId
	 * @param loginIdCompare
	 * @param certificationKey
	 * @param authType
	 * @param teamName
	 * @param teamNameCompare
	 * @param name
	 * @param nameCompare
	 * @param mobileNumber
	 * @param mobileNumberCompare
	 */
	public MemberFindDto(Long id, List<Long> ids, String loginId, Compare loginIdCompare, String certificationKey,
			AuthType authType, String teamName, Compare teamNameCompare, String name, Compare nameCompare,
			String mobileNumber, Compare mobileNumberCompare) {
		super();
		this.id = id;
		this.ids = ids;
		this.loginId = loginId;
		this.loginIdCompare = loginIdCompare;
		this.certificationKey = certificationKey;
		this.authType = authType;
		this.teamName = teamName;
		this.teamNameCompare = teamNameCompare;
		this.name = name;
		this.nameCompare = nameCompare;
		this.mobileNumber = mobileNumber;
		this.mobileNumberCompare = mobileNumberCompare;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public Compare getLoginIdCompare() {
		return loginIdCompare;
	}


	public void setLoginIdCompare(Compare loginIdCompare) {
		this.loginIdCompare = loginIdCompare;
	}


	public String getCertificationKey() {
		return certificationKey;
	}


	public void setCertificationKey(String certificationKey) {
		this.certificationKey = certificationKey;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public Compare getTeamNameCompare() {
		return teamNameCompare;
	}


	public void setTeamNameCompare(Compare teamNameCompare) {
		this.teamNameCompare = teamNameCompare;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Compare getNameCompare() {
		return nameCompare;
	}


	public void setNameCompare(Compare nameCompare) {
		this.nameCompare = nameCompare;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Compare getMobileNumberCompare() {
		return mobileNumberCompare;
	}

	public void setMobileNumberCompare(Compare mobileNumberCompare) {
		this.mobileNumberCompare = mobileNumberCompare;
	}

	@Override
	public Predicate toPredicate(
			Root<Member> root,
			CriteriaQuery<?> query,
			CriteriaBuilder cb) {

		Predicate p = cb.conjunction();

		if(id != null) {
			p.getExpressions().add(cb.equal(root.<Long>get("id"), id));
		}

		if(null!=ids && ids.size()>0) {
			p.getExpressions() .add(root.<Long>get("id").in(ids));
		}

		if(null!=authType) {
			p.getExpressions().add(cb.equal(root.<AuthType>get("authType"), authType));
		}

		if(!StringUtils.isEmpty(name)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("name"),  "%" + name + "%"));
		}

		if(!StringUtils.isEmpty(teamName)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("teamName"),  "%" + teamName + "%"));
		}

		if(!StringUtils.isEmpty(loginId)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("loginId"),  "%" + loginId + "%"));
		}

		return p;
	}
}