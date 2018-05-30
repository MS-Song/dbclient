package com.song7749.incident.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.log.domain.Log;
import com.song7749.log.type.LogType;

/**
 * <pre>
 * Class Name : LogIncidentAlarm.java
 * Description : 알람 수정 로그
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 30.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 30.
*/

@Entity
@DiscriminatorValue(LogType.INCIDENT_ALARM)
public class LogIncidentAlarm extends Log {

	private static final long serialVersionUID = -6885750899178286611L;

	@NotNull
	@Column(nullable = false, updatable = false)
	private Long incidentAlarmId;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String before;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String after;

	public LogIncidentAlarm() {}

	/**
	 * @param incidentAlarmId
	 * @param before
	 * @param after
	 */
	public LogIncidentAlarm(@NotNull Long incidentAlarmId, @NotBlank String before, @NotBlank String after) {
		this.incidentAlarmId = incidentAlarmId;
		this.before = before;
		this.after = after;
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
}