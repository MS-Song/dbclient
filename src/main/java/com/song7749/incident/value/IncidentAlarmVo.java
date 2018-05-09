package com.song7749.incident.value;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractVo;
import com.song7749.base.SendMethod;
import com.song7749.base.YN;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람작업 객체")
public class IncidentAlarmVo extends AbstractVo {

	private static final long serialVersionUID = 7599341008290697822L;

	@ApiModelProperty("알람 ID")
	private Long id;

	@ApiModelProperty("알람 제목")
	private String subject;

	@ApiModelProperty("알람 감지 SQL")
	private String beforeSql;

	@ApiModelProperty("알람 실행 SQL")
	private String runSql;

	@ApiModelProperty("알람 전달 방법")
	private SendMethod sendMethod;

	@ApiModelProperty("알람 실행 여부")
	private YN enableYN;

	@ApiModelProperty("알람 승인 여부")
	private YN confirmYN;

	@ApiModelProperty("알람 스케줄")
	private String schedule;

	@ApiModelProperty("알람 생성일")
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date createDate;

	@ApiModelProperty("알람 승인일")
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date confirmDate;

	@ApiModelProperty("마지막 실행 일자")
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date lastRunDate;

	@ApiModelProperty("마지막 에러 메세지")
	private String lastErrorMessage;


	@ApiModelProperty("알람 연결 Database")
	private DatabaseVo databaseVo;

	@ApiModelProperty("알람 등록자")
	private MemberVo resistMemberVo;

	@ApiModelProperty("알람 승인자")
	private MemberVo confirmMemberVo;

	@ApiModelProperty("알람 전송 대상자")
	private List<MemberVo> sendMemberVos;

	public IncidentAlarmVo() {}

	/**
	 * @param id
	 * @param subject
	 * @param beforeSql
	 * @param runSql
	 * @param sendMethod
	 * @param enableYN
	 * @param confirmYN
	 * @param schedule
	 * @param createDate
	 * @param confirmDate
	 * @param lastRunDate
	 * @param lastErrorMessage
	 * @param databaseVo
	 * @param resistMemberVo
	 * @param confirmMemberVo
	 * @param sendMemberVos
	 */
	public IncidentAlarmVo(Long id, String subject, String beforeSql, String runSql, SendMethod sendMethod, YN enableYN,
			YN confirmYN, String schedule, Date createDate, Date confirmDate, Date lastRunDate, String lastErrorMessage,
			DatabaseVo databaseVo, MemberVo resistMemberVo, MemberVo confirmMemberVo, List<MemberVo> sendMemberVos) {
		super();
		this.id = id;
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.runSql = runSql;
		this.sendMethod = sendMethod;
		this.enableYN = enableYN;
		this.confirmYN = confirmYN;
		this.schedule = schedule;
		this.createDate = createDate;
		this.confirmDate = confirmDate;
		this.lastRunDate = lastRunDate;
		this.lastErrorMessage = lastErrorMessage;
		this.databaseVo = databaseVo;
		this.resistMemberVo = resistMemberVo;
		this.confirmMemberVo = confirmMemberVo;
		this.sendMemberVos = sendMemberVos;
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

	public DatabaseVo getDatabaseVo() {
		return databaseVo;
	}

	public void setDatabaseVo(DatabaseVo databaseVo) {
		this.databaseVo = databaseVo;
	}

	public MemberVo getResistMemberVo() {
		return resistMemberVo;
	}

	public void setResistMemberVo(MemberVo resistMemberVo) {
		this.resistMemberVo = resistMemberVo;
	}

	public MemberVo getConfirmMemberVo() {
		return confirmMemberVo;
	}

	public void setConfirmMemberVo(MemberVo confirmMemberVo) {
		this.confirmMemberVo = confirmMemberVo;
	}

	public List<MemberVo> getSendMemberVos() {
		return sendMemberVos;
	}

	public void setSendMemberVos(List<MemberVo> sendMemberVos) {
		this.sendMemberVos = sendMemberVos;
	}

}