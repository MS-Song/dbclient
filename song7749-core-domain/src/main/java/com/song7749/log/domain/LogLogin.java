
package com.song7749.log.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.song7749.log.type.LogType;
import com.song7749.log.value.LogLoginVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : LogLogin.java
 * Description : Member Login Log
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	New
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
@DiscriminatorValue(LogType.Constants.LOGIN)
public class LogLogin extends Log {

	private static final long serialVersionUID = -5741549743951708157L;

	@NotBlank
	@Email
	@Column(nullable = false, updatable = false)
	private String loginId;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String cipher;

	public LogLoginVo getLogLoginVo(ModelMapper mapper) {
		return mapper.map(this, LogLoginVo.class);
	}
}