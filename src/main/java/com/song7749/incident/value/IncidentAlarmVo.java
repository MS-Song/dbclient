package com.song7749.incident.value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.base.AbstractVo;
import com.song7749.base.SendMethod;
import com.song7749.base.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람작업 리스트")
public class IncidentAlarmVo extends AbstractVo {

	private static final long serialVersionUID = 7599341008290697822L;

	@ApiModelProperty(value="알람 ID",position=1)
	private Long id;

	@ApiModelProperty(value="알람 제목",position=2)
	private String subject;

	@ApiModelProperty(value="알람 전달 방법",position=3)
	private SendMethod sendMethod;

	@ApiModelProperty(value="알람 실행 여부",position=4)
	private YN enableYN;

	@ApiModelProperty(value="알람 승인 여부",position=5)
	private YN confirmYN;

	@ApiModelProperty(value="알람 스케줄",position=6)
	private String schedule;

	@ApiModelProperty(value="마지막 실행 일자",position=7)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastRunDate;

	@ApiModelProperty(value="마지막 에러 메세지",position=8)
	private String lastErrorMessage;

	public IncidentAlarmVo() {}

	/**
	 * @param id
	 * @param subject
	 * @param sendMethod
	 * @param enableYN
	 * @param confirmYN
	 * @param schedule
	 * @param lastRunDate
	 * @param lastErrorMessage
	 */
	public IncidentAlarmVo(Long id, String subject, SendMethod sendMethod, YN enableYN, YN confirmYN, String schedule,
			Date lastRunDate, String lastErrorMessage) {
		super();
		this.id = id;
		this.subject = subject;
		this.sendMethod = sendMethod;
		this.enableYN = enableYN;
		this.confirmYN = confirmYN;
		this.schedule = schedule;
		this.lastRunDate = lastRunDate;
		this.lastErrorMessage = lastErrorMessage;
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

	public YN getConfirmYN() {
		return confirmYN;
	}

	public void setConfirmYN(YN confirmYN) {
		this.confirmYN = confirmYN;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	public String getLastErrorMessage() {
		return lastErrorMessage;
	}

	public void setLastErrorMessage(String lastErrorMessage) {
		this.lastErrorMessage = lastErrorMessage;
	}
}