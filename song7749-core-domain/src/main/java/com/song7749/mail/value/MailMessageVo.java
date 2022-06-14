package com.song7749.mail.value;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.common.base.AbstractVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : MailMessageVo.java
 * Description : 메일 전송 객체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 28.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 28.
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageVo extends AbstractVo {

	private static final long serialVersionUID = 3171635062081696575L;

	@NotNull
	private String from;

	@NotNull
	private List<String> to;

	private List<String> cc;

	private List<String> bcc;

	@NotBlank
	private String subject;

	@NotBlank
	private String text;

	private List<String> files;
}