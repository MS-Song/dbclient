package com.song7749.dbclient.drs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.Entities;
import com.song7749.dbclient.drs.type.Charset;
import com.song7749.dbclient.drs.type.DatabaseDriver;
import com.song7749.dbclient.drs.value.DatabaseVo;
import com.song7749.util.crypto.CryptoTwoWayConverter;

/**
 * <pre>
 * Class Name : Database.java
 * Description : Datbase Server 접속 정보 저장
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 16.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 1. 16.
 */

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
public class Database extends Entities {

	private static final long serialVersionUID = 8561337661359215895L;

	@Id
	@Column(name = "database_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Length(max = 120)
	@NotBlank
	private String host;

	@Column(nullable = false)
	@Length(max = 120)
	@NotBlank
	private String hostAlias;

	@Column(nullable = false)
	@Length(max = 120)
	private String schemaName;

	@Column(nullable = false)
	@Length(max = 60)
	@NotBlank
	private String account;

	@Column(nullable = false)
	@Convert(converter=CryptoTwoWayConverter.class)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private DatabaseDriver driver;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Charset charset;

	@Column(nullable = false)
	private String port;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date createDate;

	@Column(nullable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date modifyDate;

	@Transient
	private String name;

	public Database() {}

	/**
	 * @param id
	 */
	public Database(Long id) {
		this.id = id;
	}

	/**
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 */
	public Database(@Length(max = 120) @NotBlank String host, @Length(max = 120) @NotBlank String hostAlias,
			@Length(max = 120) @NotBlank String schemaName, @Length(max = 60) @NotBlank String account,
			@Length(min = 4, max = 255) @NotBlank String password, @NotNull DatabaseDriver driver,
			@NotNull Charset charset, @Length(max = 5) @NotBlank String port) {
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
	}

	/**
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 * @param createDate
	 * @param modifyDate
	 * @param name
	 */
	public Database(@Length(max = 120) @NotBlank String host, @Length(max = 120) @NotBlank String hostAlias,
			@Length(max = 120) @NotBlank String schemaName, @Length(max = 60) @NotBlank String account,
			@Length(min = 4, max = 20) @NotBlank String password, @NotNull DatabaseDriver driver,
			@NotNull Charset charset, @Length(max = 5) @NotBlank String port, String name) {
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
		this.name = name;
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

	public String getHostAlias() {
		return hostAlias;
	}

	public void setHostAlias(String hostAlias) {
		this.hostAlias = hostAlias;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DatabaseDriver getDriver() {
		return driver;
	}

	public void setDriver(DatabaseDriver driver) {
		this.driver = driver;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DatabaseVo getDatabaseVo(ModelMapper mapper) {
		return mapper.map(this, DatabaseVo.class);
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
		Database other = (Database) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}