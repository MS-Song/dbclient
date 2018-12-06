package com.song7749.chakra.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import com.song7749.common.Entities;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;

/**
 * <pre>
 * Class Name : ChakraConfig.java
 * Description : ckakra 환경 설정
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 11. 1.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 11. 1.
*/

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "target_database_id", name = "UK_TARGET_DATABASE") })
public class ChakraConfig  extends Entities {

	private static final long serialVersionUID = -3470680743249449745L;

	@Id
	@Column(name = "chakra_config_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(targetEntity=Database.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "chakra_database_id", nullable = false, insertable = true, updatable = false)
	private Database chakraDatabase;

	@NotNull
	@ManyToOne(targetEntity=Database.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "target_database_id", nullable = false, insertable = true, updatable = false)
	private Database targetDatabase;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private YN autoSyncYN;

	@Lob
	@Length(max = 12000)
	@Column(nullable = true)
	private String errorMessage;

	public ChakraConfig() {}


	/**
	 * @param chakraDatabase
	 * @param targetDatabase
	 * @param autoSyncYN
	 * @param errorMessage
	 */
	public ChakraConfig(@NotNull Database chakraDatabase, @NotNull Database targetDatabase, @NotNull YN autoSyncYN) {
		this.chakraDatabase = chakraDatabase;
		this.targetDatabase = targetDatabase;
		this.autoSyncYN = autoSyncYN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Database getChakraDatabase() {
		return chakraDatabase;
	}


	public void setChakraDatabase(Database chakraDatabase) {
		this.chakraDatabase = chakraDatabase;
	}


	public Database getTargetDatabase() {
		return targetDatabase;
	}


	public void setTargetDatabase(Database targetDatabase) {
		this.targetDatabase = targetDatabase;
	}


	public YN getAutoSyncYN() {
		return autoSyncYN;
	}


	public void setAutoSyncYN(YN autoSyncYN) {
		this.autoSyncYN = autoSyncYN;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChakraConfig other = (ChakraConfig) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}