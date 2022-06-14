package com.song7749.log.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.log.type.LogType;
import com.song7749.log.value.LogQueryVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : LogQuery.java
 * Description : 유저가 사용한 쿼리에 대한 로그
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 22.
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(LogType.Constants.QUERY)
public class LogQuery extends Log{

	private static final long serialVersionUID = 240902778616234461L;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String loginId;

	@NotNull
	@Column(nullable=false,updatable=false)
	private Long databaseId;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String host;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String hostAlias;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String schemaName;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String account;

	@Lob
	@Column(nullable=false, updatable=false)
	@NotBlank
	@Size(max=50000)
	private String query;

	public LogQueryVo getLogLoginVo(ModelMapper mapper) {
		return mapper.map(this, LogQueryVo.class);
	}
}