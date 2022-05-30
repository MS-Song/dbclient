package com.song7749.srcenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.common.base.Entities;
import com.song7749.common.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.domain.Member;
import com.song7749.member.value.MemberVo;
import com.song7749.srcenter.type.DownloadLimitType;
import com.song7749.srcenter.value.SrDataConditionVo;
import com.song7749.srcenter.value.SrDataRequestVo;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <pre>
 * Class Name : SrDataRequest
 * Description : SR 데이터 요청에 대한 모델
*
*  Modification Information
*  Modify Date 		Modifier	            Comment
*  -----------------------------------------------
*  11/11/2019		song7749@gmail.com	    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 11/11/2019
 *
 *
 */

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
public class SrDataRequest extends Entities {

	private static final long serialVersionUID = -932046749163739343L;

	@Id
	@Column(name = "sr_data_request_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Length(max = 200)
	@Column(nullable = false)
	private String subject;

	@Lob
	@NotBlank
	@Length(max = 50000)
	@Column(nullable = false)
	private String runSql;

	@NotNull
	@Column(nullable = false)
	private Integer downloadLimit;

	@NotNull
	@Column(nullable = false)
	private Integer downloadCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private DownloadLimitType downloadLimitType;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date downloadStartDate;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date downloadEndDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private YN enableYN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private YN confirmYN;

	@CreationTimestamp
	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastRunDate;

	@Column(nullable = true)
	@Length(max = 1000)
	private String lastErrorMessage;

	@ManyToOne(targetEntity = Database.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "database_id", nullable = true, insertable = true, updatable = true)
	private Database database;

	@NotNull
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "resist_member_id", nullable = false, insertable = true, updatable = true)
	private Member resistMember;

	@ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "confirm_member_id", nullable = true, insertable = true, updatable = true)
	private Member confirmMember;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "srDataRequest", orphanRemoval = true)
	private List<SrDataCondition> srDataConditions;

	@NotNull
	@ManyToMany(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "allow_member_id", nullable = false, insertable = true, updatable = true)
	private List<Member> srDataAllowMembers;

	/**
	 * 기본 생성자
	 */
	public SrDataRequest() {
	}

	/**
	 * 객체를 생성하기 위한 최소의 값을 정의한 생성자
	 *
	 * @param subject
	 * @param runSql
	 * @param downloadLimit
	 * @param downloadCount
	 * @param downloadLimitType
	 * @param downloadStartDate
	 * @param downloadEndDate
	 * @param enableYN
	 * @param database
	 * @param resistMember
	 * @param srDataAllowMembers
	 */
	public SrDataRequest(@NotBlank @Length(max = 200) String subject, @NotBlank @Length(max = 50000) String runSql,
			@NotNull Integer downloadLimit, @NotNull Integer downloadCount, DownloadLimitType downloadLimitType,
			Date downloadStartDate, Date downloadEndDate, @NotNull YN enableYN, Database database,
			@NotNull Member resistMember, List<Member> srDataAllowMembers) {
		this.subject = subject;
		this.runSql = runSql;
		this.downloadLimit = downloadLimit;
		this.downloadCount = downloadCount;
		this.downloadLimitType = downloadLimitType;
		this.downloadStartDate = downloadStartDate;
		this.downloadEndDate = downloadEndDate;
		this.enableYN = enableYN;
		this.database = database;
		this.resistMember = resistMember;
		this.srDataAllowMembers = srDataAllowMembers;
	}

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRunSql() {
		return runSql;
	}

	public void setRunSql(String runSql) {
		this.runSql = runSql;
	}

	public Integer getDownloadLimit() {
		return downloadLimit;
	}

	public void setDownloadLimit(Integer downloadLimit) {
		this.downloadLimit = downloadLimit;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public DownloadLimitType getDownloadLimitType() {
		return downloadLimitType;
	}

	public void setDownloadLimitType(DownloadLimitType downloadLimitType) {
		this.downloadLimitType = downloadLimitType;
	}

	public Date getDownloadStartDate() {
		return downloadStartDate;
	}

	public void setDownloadStartDate(Date downloadStartDate) {
		this.downloadStartDate = downloadStartDate;
	}

	public Date getDownloadEndDate() {
		return downloadEndDate;
	}

	public void setDownloadEndDate(Date downloadEndDate) {
		this.downloadEndDate = downloadEndDate;
	}

	public YN getEnableYN() {
		return enableYN;
	}

	public void setEnableYN(YN enableYN) {
		this.enableYN = enableYN;
	}

	public YN getConfirmYN() {
		return confirmYN;
	}

	public void setConfirmYN(YN confirmYN) {
		this.confirmYN = confirmYN;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	public String getLastErrorMessage() {
		return lastErrorMessage;
	}

	public void setLastErrorMessage(String lastErrorMessage) {
		this.lastErrorMessage = lastErrorMessage;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Member getResistMember() {
		return resistMember;
	}

	public void setResistMember(Member resistMember) {
		this.resistMember = resistMember;
	}

	public Member getConfirmMember() {
		return confirmMember;
	}

	public void setConfirmMember(Member confirmMember) {
		this.confirmMember = confirmMember;
	}

	public List<SrDataCondition> getSrDataConditions() {
		return srDataConditions;
	}

	public void setSrDataConditions(List<SrDataCondition> srDataConditions) {
		this.srDataConditions = srDataConditions;
	}

	public List<Member> getSrDataAllowMembers() {
		return srDataAllowMembers;
	}

	public void setSrDataAllowMembers(java.util.List<Member> srDataAllowMembers) {
		this.srDataAllowMembers = srDataAllowMembers;
	}

	public SrDataRequestVo getSrDataRequestVo(ModelMapper mapper) {
		// VO 로 변환
		SrDataRequestVo vo = mapper.map(this, SrDataRequestVo.class);
		// 회원 객체 변환
		if (null != this.getResistMember())
			vo.setResistMemberVo(mapper.map(this.getResistMember(), MemberVo.class));
		if (null != this.getConfirmMember())
			vo.setConfirmMemberVo(mapper.map(this.getConfirmMember(), MemberVo.class));
		// 데이터베이스 객체 변환
		if (null != this.getDatabase())
			vo.setDatabaseVo(mapper.map(this.getDatabase(), DatabaseVo.class));

		// 검색 조건 변환
		List<SrDataConditionVo> conditioVos = new ArrayList<>();
		for (SrDataCondition sdr : this.getSrDataConditions()) {
			conditioVos.add(sdr.getSrDataConditionVo(mapper));
		}
		vo.setSrDataConditionVos(conditioVos);
		// 승인 회원 리스트
		List<Long> memberIds = new ArrayList<>();
		for (Member m : this.getSrDataAllowMembers()) {
			memberIds.add(m.getId());
		}
		vo.setSrDataAllowMemberIds(memberIds);
		return vo;
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
		SrDataRequest other = (SrDataRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}