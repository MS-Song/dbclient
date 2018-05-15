package com.song7749.incident.value;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.song7749.base.AbstractDto;
import com.song7749.base.SendMethod;
import com.song7749.base.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람 승인전 수정")
public class IncidentAlarmModifyBeforeConfirmDto extends AbstractDto {

	private static final long serialVersionUID = 8931222738608566508L;

	@ApiModelProperty(value="알람ID")
	@NotNull
	private Long id;

	@ApiModelProperty(value="알람명칭")
	@NotBlank
	@Length(max = 60)
	private String subject;

	@ApiModelProperty(value="알람 감지 SQL",example="Y가 리턴되도록 작성, EX) SELECT 'Y' as enable FROM dual")
	@NotBlank
	@Length(max = 8000)
	private String beforeSql;

	@ApiModelProperty(value="알람 내역",example="담당자에게 전송할 내용을 생성하는 SQL, Email-> <table> 형태, SMS--> plain text 형태 ")
	@NotBlank
	@Length(max = 8000)
	private String runSql;

	@ApiModelProperty(value="알람 방법",example="EMAIL or SMS[MMS]")
	@NotNull
	@Enumerated(EnumType.STRING)
	private SendMethod sendMethod;

	@ApiModelProperty(value="동작여부")
	@NotNull
	@Enumerated(EnumType.STRING)
	private YN enableYN;

	@ApiModelProperty(value="알람 주기",example="crontab 양식, * * * * * * (초 분 시 날짜 달 주의날짜 년도)")
	@NotBlank
	private String schedule;

	@ApiModelProperty(value="database id",example="실행할 데이터베이스의 ID")
	@NotNull
	private Long databaseId;

	@ApiModelProperty(value="등록자ID")
	@NotNull
	private Long memberId;

	@ApiModelProperty(value="전송대상자ID")
	@NotNull
	private List<Long> sendMemberIds;

	public IncidentAlarmModifyBeforeConfirmDto() {}

	public IncidentAlarmModifyBeforeConfirmDto(@NotNull Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param subject
	 * @param beforeSql
	 * @param runSql
	 * @param sendMethod
	 * @param enableYN
	 * @param schedule
	 * @param databaseId
	 * @param memberId
	 * @param sendMemberIds
	 */
	public IncidentAlarmModifyBeforeConfirmDto(@NotNull Long id, @NotBlank @Length(max = 60) String subject,
			@NotBlank @Length(max = 8000) String beforeSql, @NotBlank @Length(max = 8000) String runSql,
			@NotNull SendMethod sendMethod, @NotNull YN enableYN, @NotBlank String schedule, @NotNull Long databaseId,
			@NotNull Long memberId, @NotNull List<Long> sendMemberIds) {
		super();
		this.id = id;
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.runSql = runSql;
		this.sendMethod = sendMethod;
		this.enableYN = enableYN;
		this.schedule = schedule;
		this.databaseId = databaseId;
		this.memberId = memberId;
		this.sendMemberIds = sendMemberIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBeforeSql() {
		return beforeSql;
	}

	public void setBeforeSql(String beforeSql) {
		this.beforeSql = beforeSql;
	}

	public String getRunSql() {
		return runSql;
	}

	public void setRunSql(String runSql) {
		this.runSql = runSql;
	}

	public SendMethod getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(SendMethod sendMethod) {
		this.sendMethod = sendMethod;
	}

	public YN getEnableYN() {
		return enableYN;
	}

	public void setEnableYN(YN enableYN) {
		this.enableYN = enableYN;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public List<Long> getSendMemberIds() {
		return sendMemberIds;
	}

	public void setSendMemberIds(List<Long> sendMemberIds) {
		this.sendMemberIds = sendMemberIds;
	}
}