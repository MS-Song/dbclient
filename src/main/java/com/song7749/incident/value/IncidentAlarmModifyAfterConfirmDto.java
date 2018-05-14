package com.song7749.incident.value;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.song7749.base.AbstractDto;
import com.song7749.base.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람 승인 후 수정")
public class IncidentAlarmModifyAfterConfirmDto extends AbstractDto {

	private static final long serialVersionUID = 208893498283713844L;

	@ApiModelProperty("알람ID")
	@NotNull
	private Long id;

	@ApiModelProperty("알람명칭")
	@NotBlank
	@Length(max = 60)
	private String subject;

	@ApiModelProperty(name="알람 감지 SQL",example="Y가 리턴되도록 작성, EX) SELECT 'Y' as enable FROM dual")
	@NotBlank
	@Length(max = 8000)
	private String beforeSql;

	@ApiModelProperty(name="동작여부")
	@NotNull
	@Enumerated(EnumType.STRING)
	private YN enableYN;

	@ApiModelProperty(name="알람 주기",example="crontab 양식, * * * * * * (초 분 시 날짜 달 주의날짜 년도)")
	@NotBlank
	private String schedule;

	@ApiModelProperty(name="전송대상자ID")
	@NotNull
	private List<Long> sendMemberIds;

	public IncidentAlarmModifyAfterConfirmDto() {}

	/**
	 * @param id
	 */
	public IncidentAlarmModifyAfterConfirmDto(@NotNull Long id) {
		this.id = id;
	}

	/**
	 * @param apikey
	 * @param id
	 * @param subject
	 * @param beforeSql
	 * @param enableYN
	 * @param schedule
	 * @param sendMemberIds
	 */
	public IncidentAlarmModifyAfterConfirmDto(@NotNull Long id,
			@NotBlank @Length(max = 60) String subject, @NotBlank @Length(max = 8000) String beforeSql,
			@NotNull YN enableYN, @NotBlank String schedule, @NotNull List<Long> sendMemberIds) {
		this.id = id;
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.enableYN = enableYN;
		this.schedule = schedule;
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

	public List<Long> getSendMemberIds() {
		return sendMemberIds;
	}

	public void setSendMemberIds(List<Long> sendMemberIds) {
		this.sendMemberIds = sendMemberIds;
	}
}