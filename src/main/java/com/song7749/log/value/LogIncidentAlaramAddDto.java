package com.song7749.log.value;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.base.AbstractDto;
import com.song7749.log.domain.LogIncidentAlarm;

public class LogIncidentAlaramAddDto extends AbstractDto {

	private static final long serialVersionUID = -2714781506402961813L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@NotNull
	@Column(nullable = false, updatable = false)
	private Long incidentAlarmId;

	@NotBlank
	@Size(max=12000)
	@Column(nullable = false, updatable = false)
	private String before;

	@NotBlank
	@Size(max=12000)
	@Column(nullable = false, updatable = false)
	private String after;

	public LogIncidentAlaramAddDto() {}

	/**
	 * @param ip
	 * @param incidentAlarmId
	 * @param before
	 * @param after
	 */
	public LogIncidentAlaramAddDto(@NotBlank @Size(min = 8, max = 64) String ip, @NotNull Long incidentAlarmId,
			@NotBlank @Size(max = 12000) String before, @NotBlank @Size(max = 12000) String after) {
		this.ip = ip;
		this.incidentAlarmId = incidentAlarmId;
		this.before = before;
		this.after = after;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getIncidentAlarmId() {
		return incidentAlarmId;
	}

	public void setIncidentAlarmId(Long incidentAlarmId) {
		this.incidentAlarmId = incidentAlarmId;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public LogIncidentAlarm getLogIncidentAlarm(ModelMapper mapper) {
		return mapper.map(this, LogIncidentAlarm.class);
	}
}