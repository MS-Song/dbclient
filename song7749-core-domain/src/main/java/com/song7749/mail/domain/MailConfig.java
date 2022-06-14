package com.song7749.mail.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.mail.type.EmailProtocol;
import com.song7749.util.crypto.CryptoTwoWayConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


	@Builder.Default
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmailProtocol protocol = EmailProtocol.smtp;

	@Builder.Default
	@NotBlank
	@Column(nullable = false)
	private String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean auth = true;

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean enableSSL = true;

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean starttls = true;

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean socketFactoryFallback = false;

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean quitwait = false;

	@Builder.Default
	@NotNull
	@Column(nullable = false)
	private Boolean debug = false;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@Column(nullable = true,  updatable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;

	/**
	 * @param id
	 */
	public MailConfig(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailConfig other = (MailConfig) obj;
		return Objects.equals(id, other.id);
	}
}