package com.song7749.log.value;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.common.base.AbstractDto;
import com.song7749.log.domain.LogIncidentAlarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogIncidentAlaramAddDto extends AbstractDto {

	private static final long serialVersionUID = -2714781506402961813L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@NotNull
	@Column(nullable = false, updatable = false)
	private Long incidentAlarmId;

	@NotBlank
	@Size(max=50000)
	@Column(nullable = false, updatable = false)
	private String before;

	@NotBlank
	@Size(max=50000)
	@Column(nullable = false, updatable = false)
	private String after;

	public LogIncidentAlarm getLogIncidentAlarm(ModelMapper mapper) {
		return mapper.map(this, LogIncidentAlarm.class);
	}
}