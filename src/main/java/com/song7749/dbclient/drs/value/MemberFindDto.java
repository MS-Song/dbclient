package com.song7749.dbclient.drs.value;

import com.song7749.base.AbstractDto;
import com.song7749.base.Compare;

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
public class MemberFindDto extends AbstractDto {

	private static final long serialVersionUID = -111258667094644234L;

	@ApiModelProperty("회원ID")
	private Long id;

	@ApiModelProperty("로그인ID")
	private String loginId;

	@ApiModelProperty(value="로그인 ID 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare loginIdCompare = Compare.EQUAL;

	@ApiModelProperty("인증키")
	private String certificationKey;

	@ApiModelProperty("팀명")
	private String teamName;
	@ApiModelProperty(value="팀명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare teamNameCompare = Compare.EQUAL;

	@ApiModelProperty("성명")
	private String name;
	@ApiModelProperty(value="성명 조건",example="like , =  외 적당한 조건을 넣는다.")
	private Compare nameCompare = Compare.EQUAL;

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
	 * @param loginId
	 * @param loginIdCompare
	 * @param certificationKey
	 * @param teamName
	 * @param teamNameCompare
	 * @param name
	 * @param nameCompare
	 */
	public MemberFindDto(Long id, String loginId, Compare loginIdCompare, String certificationKey, String teamName,
			Compare teamNameCompare, String name, Compare nameCompare) {
		this.id = id;
		this.loginId = loginId;
		this.loginIdCompare = loginIdCompare;
		this.certificationKey = certificationKey;
		this.teamName = teamName;
		this.teamNameCompare = teamNameCompare;
		this.name = name;
		this.nameCompare = nameCompare;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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
}