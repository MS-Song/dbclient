package com.song7749.log.value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.common.base.AbstractDto;
import com.song7749.log.domain.LogQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogQueryAddDto  extends AbstractDto {

	private static final long serialVersionUID = -60340203267080918L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@NotBlank
	private String loginId;

	@NotNull
	private Long databaseId;

	@NotBlank
	private String host;

	@NotBlank
	private String hostAlias;

	@NotBlank
	private String schemaName;

	@NotBlank
	private String account;

	@NotBlank
	@Size(max = 50000)
	private String query;

	public LogQuery getLogQuery(ModelMapper mapper) {
		return mapper.map(this, LogQuery.class);
	}
}