package com.song7749.log.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.log.type.LogType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(LogType.Constants.INCIDENT_ALARM)
public class LogIncidentAlarm extends Log {

	private static final long serialVersionUID = -6885750899178286611L;

	@NotNull
	@Column(nullable = false, updatable = false)
	private Long incidentAlarmId;

	@Lob
	@NotBlank
	@Size(max=50000)
	@Column(name="befor_info", nullable = false, updatable = false)
	private String before;

	@Lob
	@NotBlank
	@Size(max=50000)
	@Column(name="after_info", nullable = false, updatable = false)
	private String after;
}