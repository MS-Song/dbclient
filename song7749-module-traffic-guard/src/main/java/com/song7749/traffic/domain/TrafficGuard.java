package com.song7749.traffic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.song7749.common.Entities;

@Entity
@DynamicUpdate(true)
class TrafficGuard  extends Entities {

	private static final long serialVersionUID = -1763518466960503595L;

	@Id
	@Column(name="traffic_guard_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false, updatable = true)
	private String name;

	@NotBlank
	@Column(nullable = false, updatable = true)
	private String url;

	@Column(nullable = true, updatable = true)
	private String comment;

	@NotNull
	@Column(nullable = false, updatable = true)
	private Integer sessionWarningCount;

	@NotNull
	@Column(nullable = false, updatable = true)
	private Integer sessionMaxCount;

	@NotNull
	@Column(nullable = false, updatable = true)
	private Integer sessionTimeOut;

	@ManyToOne(targetEntity=TrafficGuard.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false, insertable = true, updatable = true)
	private Category category;

	public TrafficGuard() {}

	/**
	 * @param id
	 */
	public TrafficGuard(Long id) {
		this.id = id;
	}

	/**
	 * @param name
	 * @param url
	 * @param sessionWarningCount
	 * @param sessionMaxCount
	 * @param sessionTimeOut
	 * @param category
	 */
	public TrafficGuard(@NotBlank String name, @NotBlank String url, @NotNull Integer sessionWarningCount,
			@NotNull Integer sessionMaxCount, @NotNull Integer sessionTimeOut, Category category) {
		this.name = name;
		this.url = url;
		this.sessionWarningCount = sessionWarningCount;
		this.sessionMaxCount = sessionMaxCount;
		this.sessionTimeOut = sessionTimeOut;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getSessionWarningCount() {
		return sessionWarningCount;
	}

	public void setSessionWarningCount(Integer sessionWarningCount) {
		this.sessionWarningCount = sessionWarningCount;
	}

	public Integer getSessionMaxCount() {
		return sessionMaxCount;
	}

	public void setSessionMaxCount(Integer sessionMaxCount) {
		this.sessionMaxCount = sessionMaxCount;
	}

	public Integer getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(Integer sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
		TrafficGuard other = (TrafficGuard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}