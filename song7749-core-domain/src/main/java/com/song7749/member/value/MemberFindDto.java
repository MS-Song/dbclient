package com.song7749.member.value;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.song7749.common.base.AbstractDto;
import com.song7749.common.base.Compare;
import com.song7749.member.domain.Member;
import com.song7749.member.type.AuthType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("회원정보 조회")
public class MemberFindDto extends AbstractDto implements Specification<Member> {

	private static final long serialVersionUID = -111258667094644234L;

	@ApiModelProperty("회원ID")
	private Long id;

	@ApiModelProperty("회원IDs")
	private List<Long> ids;

	@ApiModelProperty("로그인ID")
	private String loginId;

	@Builder.Default
	@ApiModelProperty(value="로그인 ID 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare loginIdCompare = Compare.LIKE;

	@ApiModelProperty("인증키")
	private String certificationKey;

	@ApiModelProperty("권한")
	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@ApiModelProperty("팀명")
	private String teamName;

	@Builder.Default
	@ApiModelProperty(value="팀명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare teamNameCompare = Compare.LIKE;

	@ApiModelProperty("성명")
	private String name;
	
	@Builder.Default
	@ApiModelProperty(value="성명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare nameCompare = Compare.LIKE;

	@ApiModelProperty("핸드폰 번호")
	private String mobileNumber;

	@Builder.Default
	@ApiModelProperty(value="핸드폰 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare mobileNumberCompare = Compare.LIKE;

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
			if(Compare.LIKE.equals(nameCompare)) {
				p.getExpressions().add(cb.like(root.<String>get("name"),  "%" + name + "%"));
			} else {
				p.getExpressions().add(cb.equal(root.<String>get("name"), name));
			}
		}

		if(!StringUtils.isEmpty(teamName)) {
			if(Compare.LIKE.equals(teamNameCompare)) {
				p.getExpressions().add(cb.like(root.<String>get("teamName"),  "%" + teamName + "%"));
			} else {
				p.getExpressions().add(cb.equal(root.<String>get("teamName"), teamName));
			}
		}

		if(!StringUtils.isEmpty(loginId)) {
			if(Compare.LIKE.equals(loginIdCompare)) {
				p.getExpressions().add(cb.like(root.<String>get("loginId"),  "%" + loginId + "%"));
			} else {
				p.getExpressions().add(cb.equal(root.<String>get("loginId"), loginId));
			}
		}

		return p;
	}
}