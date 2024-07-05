package com.song7749.incident.value;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.common.base.AbstractVo;
import com.song7749.common.base.SendMethod;
import com.song7749.common.base.YN;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.value.MemberVo;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람작업 상세")
public class IncidentAlarmDetailVo extends AbstractVo {

	private static final long serialVersionUID = 7599341008290697822L;

	@ApiModelProperty(value="알람ID",position=1)
	private Long id;

	@ApiModelProperty(value="알람명칭",position=2)
	private String subject;

	@ApiModelProperty(value="알람 감지 SQL",position=3)
	private String beforeSql;

	@ApiModelProperty(value="본문내용",position=4)
	@Length(max = 4000)
	private String sendMessage;

	@ApiModelProperty(value="알람 내역 SQL",position=5)
	private String runSql;

	@ApiModelProperty(value="알람 방법",position=6)
	private SendMethod sendMethod;

	@ApiModelProperty(value="동작여부",position=7)
	private YN enableYN;

	@ApiModelProperty(value="승인여부",position=8)
	private YN confirmYN;

	@ApiModelProperty(value="알람 주기",position=9)
	private String schedule;

	@ApiModelProperty(value="생성일",position=10)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;

	@ApiModelProperty(value="승인일",position=11)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date confirmDate;

	@ApiModelProperty(value="마지막 실행일",position=12)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastRunDate;

	@ApiModelProperty(value="마지막 에러 메세지",position=13)
	private String lastErrorMessage;

	@ApiModelProperty(value="데이터베이스",position=0)
	private DatabaseVo databaseVo;

	@ApiModelProperty(value="등록자",position=15)
	private MemberVo resistMemberVo;

	@ApiModelProperty(value="승인자",position=16)
	private MemberVo confirmMemberVo;

	@ApiModelProperty(value="전송대상자",position=17)
	private List<MemberVo> sendMemberVos;

	public IncidentAlarmDetailVo() {}

	/**
	 * @param id
	 * @param subject
	 * @param beforeSql
	 * @param runSql
	 * @param sendMessage
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
	public IncidentAlarmDetailVo(Long id, String subject, String beforeSql, String runSql,
			@NotBlank @Length(max = 4000) String sendMessage, SendMethod sendMethod, YN enableYN, YN confirmYN,
			String schedule, Date createDate, Date confirmDate, Date lastRunDate, String lastErrorMessage,
			DatabaseVo databaseVo, MemberVo resistMemberVo, MemberVo confirmMemberVo, List<MemberVo> sendMemberVos) {
		this.id = id;
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.runSql = runSql;
		this.sendMessage = sendMessage;
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