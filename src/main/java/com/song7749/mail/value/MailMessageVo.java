package com.song7749.mail.value;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.base.AbstractVo;

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
public class MailMessageVo extends AbstractVo{

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

	public MailMessageVo() {}

	/**
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param text
	 * @param files
	 */
	public MailMessageVo(String from, List<String> to, List<String> cc, List<String> bcc, String subject, String text,
			List<String> files) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.text = text;
		this.files = files;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}
}