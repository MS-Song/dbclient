package com.song7749.member.value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.common.AbstractVo;
import com.song7749.member.type.AuthType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author song7749@gmail.com
 *
 */
@ApiModel("회원정보")
public class MemberVo extends AbstractVo {

	private static final long serialVersionUID = -1825545819541825666L;

	@ApiModelProperty("회원번호")
	private Long id;

	@ApiModelProperty("로그인 ID")
	private String loginId;

	@ApiModelProperty("인증키")
	private String apikey;

	@ApiModelProperty("패스워드 찾기 질문")
	private String passwordQuestion;

	@ApiModelProperty("팀명칭")
	private String teamName;

	@ApiModelProperty("성명")
	private String name;

	@ApiModelProperty("전화번호")
	private String mobileNumber;

	@ApiModelProperty("권한")
	private AuthType authType;

	@ApiModelProperty("가입일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;

	@ApiModelProperty("회원정보 수정일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date modifyDate;

	@ApiModelProperty("최근 로그인 일시")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastLoginDate;

	public MemberVo() {}

	/**
	 * @param id
	 * @param loginId
	 * @param apikey
	 * @param passwordQuestion
	 * @param teamName
	 * @param name
	 * @param mobileNumber
	 * @param authType
	 * @param createDate
	 * @param modifyDate
	 * @param lastLoginDate
	 */
	public MemberVo(Long id, String loginId, String apikey, String passwordQuestion, String teamName, String name,
			String mobileNumber, AuthType authType, Date createDate, Date modifyDate, Date lastLoginDate) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.apikey = apikey;
		this.passwordQuestion = passwordQuestion;
		this.teamName = teamName;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.authType = authType;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.lastLoginDate = lastLoginDate;
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

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}