package com.song7749.incident.value;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.song7749.common.AbstractDto;
import com.song7749.common.SendMethod;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.member.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람조회")
public class IncidentAlarmFindDto extends AbstractDto implements Specification<IncidentAlarm> {

	private static final long serialVersionUID = 4844417615852185847L;

	@ApiModelProperty(value="알람 ID",position=1)
	private Long id;

	@ApiModelProperty(value="알람 제목",position=2)
	private String subject;

	@ApiModelProperty(value="알람 감지 SQL",position=3)
	private String beforeSql;

	@ApiModelProperty(value="알람 실행 SQL",position=4)
	private String runSql;

	@ApiModelProperty(value="알람 전달 방법",position=5)
	private SendMethod sendMethod;

	@ApiModelProperty(value="알람 실행 여부",position=6)
	private YN enableYN;

	@ApiModelProperty(value="알람 승인 여부",position=7)
	private YN confirmYN;

	@ApiModelProperty(value="알람 스케줄",position=8)
	private String schedule;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 등록일 검색 시작",position=9)
	private Date fromCreateDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 등록일 검색 종료",position=10)
	private Date toCreateDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 승인일 검색 시작",position=11)
	private Date fromConfirmDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 승인일 검색 종료",position=12)
	private Date toConfirmDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 마지막 실행일 검색 시작",position=13)
	private Date fromLastRunDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value="알람 마지막 실행일 검색 종료",position=14)
	private Date toLastRunDate;

	@ApiModelProperty(value="알람 마지막 에러 메세지",position=15)
	private String lastErrorMessage;

	@ApiModelProperty(value="알람 연결 Database",position=16)
	private Long databaseId;

	@ApiModelProperty(value="알람 등록자ID",position=17)
	private Long resistMemberId;

	@ApiModelProperty(value="알람 승인자ID",position=18)
	private Long confirmMemberId;

	@ApiModelProperty(value="전송 대상자ID",position=19)
	private List<Long> sendMemberIds;

	public IncidentAlarmFindDto() {}

	public IncidentAlarmFindDto(Long id) {
		this.id = id;
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

	public Date getFromCreateDate() {
		return fromCreateDate;
	}

	public void setFromCreateDate(Date fromCreateDate) {
		this.fromCreateDate = fromCreateDate;
	}

	public Date getToCreateDate() {
		return toCreateDate;
	}

	public void setToCreateDate(Date toCreateDate) {
		this.toCreateDate = toCreateDate;
	}

	public Date getFromConfirmDate() {
		return fromConfirmDate;
	}

	public void setFromConfirmDate(Date fromConfirmDate) {
		this.fromConfirmDate = fromConfirmDate;
	}

	public Date getToConfirmDate() {
		return toConfirmDate;
	}

	public void setToConfirmDate(Date toConfirmDate) {
		this.toConfirmDate = toConfirmDate;
	}

	public Date getFromLastRunDate() {
		return fromLastRunDate;
	}

	public void setFromLastRunDate(Date fromLastRunDate) {
		this.fromLastRunDate = fromLastRunDate;
	}

	public Date getToLastRunDate() {
		return toLastRunDate;
	}

	public void setToLastRunDate(Date toLastRunDate) {
		this.toLastRunDate = toLastRunDate;
	}

	public String getLastErrorMessage() {
		return lastErrorMessage;
	}

	public void setLastErrorMessage(String lastErrorMessage) {
		this.lastErrorMessage = lastErrorMessage;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public Long getResistMemberId() {
		return resistMemberId;
	}

	public void setResistMemberId(Long resistMemberId) {
		this.resistMemberId = resistMemberId;
	}

	public Long getConfirmMemberId() {
		return confirmMemberId;
	}

	public void setConfirmMemberId(Long confirmMemberId) {
		this.confirmMemberId = confirmMemberId;
	}

	public List<Long> getSendMemberIds() {
		return sendMemberIds;
	}

	public void setSendMemberIds(List<Long> sendMemberIds) {
		this.sendMemberIds = sendMemberIds;
	}

	@Override
	public Predicate toPredicate(
			Root<IncidentAlarm> root,
			CriteriaQuery<?> query,
			CriteriaBuilder cb) {

		Predicate p = cb.conjunction();


		if(id != null) {
			p.getExpressions()
				.add(cb.equal(root.<Long>get("id"), id));
		}

		if(!StringUtils.isEmpty(subject)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("subject"),  "%" + subject + "%"));
		}

		if(!StringUtils.isEmpty(beforeSql)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("beforeSql"),  "%" + beforeSql + "%"));
		}

		if(!StringUtils.isEmpty(runSql)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("runSql"),  "%" + runSql + "%"));
		}

		if(null!=sendMethod) {
			p.getExpressions()
				.add(cb.equal(root.<SendMethod>get("sendMethod"), sendMethod));
		}

		if(null!=enableYN) {
			p.getExpressions()
				.add(cb.equal(root.<YN>get("enableYN"), enableYN));
		}

		if(null!=confirmYN) {
			p.getExpressions()
				.add(cb.equal(root.<YN>get("confirmYN"), confirmYN));
		}

		if(null!=databaseId) {
			p.getExpressions()
				.add(cb.equal(root.<Database>get("database"), new Database(databaseId)));
		}

		if(null!=resistMemberId) {
			p.getExpressions()
				.add(cb.equal(root.<Member>get("resistMember"), new Member(resistMemberId)));
		}

		if(null!=confirmMemberId) {
			p.getExpressions()
				.add(cb.equal(root.<Member>get("confirmMember"), new Member(confirmMemberId)));
		}

		if(null!=sendMemberIds
				&& sendMemberIds.size()>0) {
			// in 사용을 위해서는 join 필요..
			Join<IncidentAlarm, Member> groupPath = root.join("sendMembers", JoinType.INNER);
			p.getExpressions().add(
				groupPath.in(sendMemberIds)
			);
		}

		if(!StringUtils.isEmpty(schedule)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("schedule"),  "%" + schedule + "%"));
		}

		if(!StringUtils.isEmpty(lastErrorMessage)) {
			p.getExpressions()
				.add(cb.like(root.<String>get("lastErrorMessage"),  "%" + lastErrorMessage + "%"));
		}

		if(null!=fromCreateDate && null!=toCreateDate) {
			p.getExpressions()
				.add(cb.between(root.<Date>get("createDate"), fromCreateDate, toCreateDate));
		} else if(null!=fromCreateDate) {
			p.getExpressions()
				.add(cb.greaterThanOrEqualTo(root.<Date>get("createDate"), fromCreateDate));
		} else if(null!=toCreateDate) {
			p.getExpressions()
				.add(cb.lessThanOrEqualTo(root.<Date>get("createDate"), toCreateDate));
		}

		if(null!=fromConfirmDate && null!=toConfirmDate) {
			p.getExpressions()
				.add(cb.between(root.<Date>get("confirmDate"), fromConfirmDate, toConfirmDate));
		} else if(null!=fromConfirmDate) {
			p.getExpressions()
				.add(cb.greaterThanOrEqualTo(root.<Date>get("confirmDate"), fromConfirmDate));
		} else if(null!=toConfirmDate) {
			p.getExpressions()
				.add(cb.lessThanOrEqualTo(root.<Date>get("confirmDate"), toConfirmDate));
		}

		if(null!=fromLastRunDate && null!=toLastRunDate) {
			p.getExpressions()
				.add(cb.between(root.<Date>get("lastRunDate"), fromLastRunDate, toLastRunDate));
		} else if(null!=fromLastRunDate) {
			p.getExpressions()
				.add(cb.greaterThanOrEqualTo(root.<Date>get("lastRunDate"), fromLastRunDate));
		} else if(null!=toLastRunDate) {
			p.getExpressions()
				.add(cb.lessThanOrEqualTo(root.<Date>get("lastRunDate"), toLastRunDate));
		}

		return p;
	}
}