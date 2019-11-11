package com.song7749.incident.domain;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.Entities;
import com.song7749.common.SendMethod;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.member.domain.Member;

/**
 * <pre>
 * Class Name : IncidentAlarm.java
 * Description : 시스템에서 발생한 alarm 을 사용자에게 전송해주는 역할을 담당
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 4. 26.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 4. 26.
*/

/**
 * @author song7749@gmail.com
 *
 */
@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
public class IncidentAlarm extends Entities {

	private static final long serialVersionUID = 2059247726910761027L;

	@Id
	@Column(name="incident_alarm_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Length(max = 120)
	@Column(nullable = false)
	private String subject;

	@Lob
	@NotBlank
	@Length(max = 12000)
	@Column(nullable = false)
	private String beforeSql;

	@Lob
	@NotBlank
	@Length(max = 12000)
	@Column(nullable = false)
	private String runSql;

	@Length(max = 4000)
	@Column(nullable = true)
	private String sendMessage;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private SendMethod sendMethod;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private YN enableYN;

	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private YN confirmYN;

	@NotBlank
	@Column(nullable = false)
	private String schedule;

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

	@ManyToOne(targetEntity=Database.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "database_id", nullable = true, insertable = true, updatable = true)
	private Database database;

	@NotNull
	@ManyToOne(targetEntity=Member.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "resist_member_id", nullable = false, insertable = true, updatable = false)
	private Member resistMember;

	@ManyToOne(targetEntity=Member.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "confirm_member_id", nullable = true, insertable = true, updatable = true)
	private Member confirmMember;

	@NotNull
	@ManyToMany(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "send_member_id", nullable = false, insertable = true, updatable = true)
	private List<Member> sendMembers;

	@Transient
	private boolean test=false;

	@Transient
	private Member testSendMember;

	public IncidentAlarm() {}

	/**
	 * @param id
	 */
	public IncidentAlarm(Long id) {
		this.id = id;
	}

	/**
	 * @param subject
	 * @param beforeSql
	 * @param runSql
	 * @param sendMethod
	 * @param enableYN
	 * @param schedule
	 * @param database
	 * @param resistMember
	 * @param sendMembers
	 */
	public IncidentAlarm(@NotBlank @Length(max = 60) String subject, @NotBlank String beforeSql,
			@NotBlank String runSql, @NotNull SendMethod sendMethod, @NotNull YN enableYN, @NotBlank String schedule,
			Database database, @NotNull Member resistMember, @NotNull List<Member> sendMembers) {
		super();
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.runSql = runSql;
		this.sendMethod = sendMethod;
		this.enableYN = enableYN;
		this.schedule = schedule;
		this.database = database;
		this.resistMember = resistMember;
		this.sendMembers = sendMembers;
	}

	/**
	 * @param id
	 * @param confirmYN
	 * @param confirmDate
	 * @param confirmMember
	 */
	public IncidentAlarm(Long id, YN confirmYN, Date confirmDate, Member confirmMember) {
		this.id = id;
		this.confirmYN = confirmYN;
		this.confirmDate = confirmDate;
		this.confirmMember = confirmMember;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBeforeSql() {
		return beforeSql;
	}

	public void setBeforeSql(String beforeSql) {
		this.beforeSql = beforeSql;
	}

	public String getRunSql() {
		return runSql;
	}

	public void setRunSql(String runSql) {
		this.runSql = runSql;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public SendMethod getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(SendMethod sendMethod) {
		this.sendMethod = sendMethod;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
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

	public List<Member> getSendMembers() {
		return sendMembers;
	}

	public void setSendMembers(List<Member> sendMembers) {
		this.sendMembers = sendMembers;
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

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public Member getTestSendMember() {
		return testSendMember;
	}

	public void setTestSendMember(Member testSendMember) {
		this.testSendMember = testSendMember;
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
		IncidentAlarm other = (IncidentAlarm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}