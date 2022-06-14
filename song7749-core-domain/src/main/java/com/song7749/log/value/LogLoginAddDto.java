package com.song7749.log.value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.common.base.AbstractDto;
import com.song7749.log.domain.LogLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogLoginAddDto extends AbstractDto {

	private static final long serialVersionUID = 1207184553943521808L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@Email
	@NotBlank
	private String loginId;

	@NotBlank
	private String cipher;

	public LogLogin getLogLogin(ModelMapper mapper) {
		return mapper.map(this, LogLogin.class);
	}
}
