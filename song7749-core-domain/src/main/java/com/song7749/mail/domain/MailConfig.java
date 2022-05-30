package com.song7749.mail.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.mail.type.EmailProtocol;
import com.song7749.util.crypto.CryptoTwoWayConverter;

/**
 * <pre>
 * Class Name : MailSmtpConfig.java
 * Description : email 발송 환경 설정
 *
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

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
public class MailConfig {

	@Id
	@Column(name="mail_config_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String host;

	@NotNull
	@Column(nullable = false)
	private Integer port;

	@Column(nullable = true)
	private String username;

	@Column(nullable = true)
	@Convert(converter=CryptoTwoWayConverter.class)
	private String password;


	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmailProtocol protocol = EmailProtocol.smtp;

	@NotBlank
	@Column(nullable = false)
	private String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";

	@NotNull
	@Column(nullable = false)
	private Boolean auth = true;

	@NotNull
	@Column(nullable = false)
	private Boolean enableSSL = true;

	@NotNull
	@Column(nullable = false)
	private Boolean starttls = true;

	@NotNull
	@Column(nullable = false)
	private Boolean socketFactoryFallback = false;

	@NotNull
	@Column(nullable = false)
	private Boolean quitwait = false;

	@NotNull
	@Column(nullable = false)
	private Boolean debug = false;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(nullable = true,  updatable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	public MailConfig() {}

	/**
	 * @param id
	 */
	public MailConfig(Long id) {
		this.id = id;
	}

	/**
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public MailConfig(@NotBlank String host, @NotNull Integer port, @NotBlank String username,
			@NotBlank String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}



	/**
	 * @param id
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param protocol
	 * @param socketFactoryClass
	 * @param auth
	 * @param enableSSL
	 * @param starttls
	 * @param socketFactoryFallback
	 * @param quitwait
	 * @param debug
	 * @param createDate
	 * @param modifyDate
	 */
	public MailConfig(Long id, @NotBlank String host, @NotNull Integer port, @NotBlank String username,
			@NotBlank String password, @NotNull EmailProtocol protocol, @NotBlank String socketFactoryClass,
			@NotNull Boolean auth, @NotNull Boolean enableSSL, @NotNull Boolean starttls,
			@NotNull Boolean socketFactoryFallback, @NotNull Boolean quitwait, @NotNull Boolean debug, Date createDate,
			Date modifyDate) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.protocol = protocol;
		this.socketFactoryClass = socketFactoryClass;
		this.auth = auth;
		this.enableSSL = enableSSL;
		this.starttls = starttls;
		this.socketFactoryFallback = socketFactoryFallback;
		this.quitwait = quitwait;
		this.debug = debug;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmailProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(EmailProtocol protocol) {
		this.protocol = protocol;
	}

	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}

	public void setSocketFactoryClass(String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public Boolean getEnableSSL() {
		return enableSSL;
	}

	public void setEnableSSL(Boolean enableSSL) {
		this.enableSSL = enableSSL;
	}

	public Boolean getStarttls() {
		return starttls;
	}

	public void setStarttls(Boolean starttls) {
		this.starttls = starttls;
	}

	public Boolean getSocketFactoryFallback() {
		return socketFactoryFallback;
	}

	public void setSocketFactoryFallback(Boolean socketFactoryFallback) {
		this.socketFactoryFallback = socketFactoryFallback;
	}

	public Boolean getQuitwait() {
		return quitwait;
	}

	public void setQuitwait(Boolean quitwait) {
		this.quitwait = quitwait;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailConfig other = (MailConfig) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}