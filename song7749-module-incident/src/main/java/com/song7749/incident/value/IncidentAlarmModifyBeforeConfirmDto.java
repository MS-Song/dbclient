package com.song7749.incident.value;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.song7749.common.AbstractDto;
import com.song7749.common.SendMethod;
import com.song7749.common.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람 승인전 수정")
public class IncidentAlarmModifyBeforeConfirmDto extends AbstractDto {

	private static final long serialVersionUID = 8931222738608566508L;

	@ApiModelProperty(required=true, position=0, value="알람ID || 알람의 ID, 수정 불가")
	@NotNull
	private Long id;

	@ApiModelProperty(required=true,position=1,value="알람명칭 || 알람의 제목을 작성 120자 이내로 작성")
	@NotBlank
	@Length(max = 120)
	private String subject;

	@ApiModelProperty(required=true,position=2,value="알람 감지 SQL || Y가 리턴되도록 작성, EX) SELECT 'Y' as enable FROM dual")
	@NotBlank
	@Length(max = 30000)
	private String beforeSql;

	@ApiModelProperty(required=true,position=3,value="본문내용 || 실행 SQL은 표형식 입니다. 상단에 표기할 메세지를 기록하세요. EX) 안녕하세요 XXX 입니다.등")
	@Length(max = 4000)
	private String sendMessage;

	@ApiModelProperty(required=true,position=4,value="알람 내역 SQL ||담당자에게 전송할 내용을 생성하는 SQL.<br>&ltsql&gt SELECT * FROM XX &lt/sql&gt<br>&ltsql&gt 테그가 없으면 Excel 발송")
	@NotBlank
	@Length(max = 30000)
	private String runSql;

	@ApiModelProperty(required=true,position=5,value="알람 방법 || EMAIL or SMS[MMS]")
	@NotNull
	@Enumerated(EnumType.STRING)
	private SendMethod sendMethod;

	@ApiModelProperty(required=true,position=6,value="동작여부 || 현재 실행 중인 경우 실행완료 후 동작 중지")
	@NotNull
	@Enumerated(EnumType.STRING)
	private YN enableYN;

	@ApiModelProperty(required=true,position=7,value="알람 주기 || crontab 양식, * * * * * * (초 분 시 날짜 달 주의날짜) 30초 이내는 실행 불가")
	@NotBlank
	private String schedule;

	@ApiModelProperty(required=true,position=8,value="데이터베이스 || 실행할 데이터베이스를 선택")
	@NotNull
	private Long databaseId;


	@ApiModelProperty(required=true,position=9,value="전송대상자ID || ID 를 , 형식으로 복수 입력. EX) 1,2,3,4")
	@NotNull
	private List<Long> sendMemberIds;

	public IncidentAlarmModifyBeforeConfirmDto() {}

	public IncidentAlarmModifyBeforeConfirmDto(@NotNull Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param subject
	 * @param beforeSql
	 * @param runSql
	 * @param sendMethod
	 * @param enableYN
	 * @param schedule
	 * @param databaseId
	 * @param sendMemberIds
	 */
	public IncidentAlarmModifyBeforeConfirmDto(@NotNull Long id, @NotBlank @Length(max = 120) String subject,
			@NotBlank @Length(max = 12000) String beforeSql, @NotBlank @Length(max = 12000) String runSql,
			@NotNull SendMethod sendMethod, @NotNull YN enableYN, @NotBlank String schedule, @NotNull Long databaseId,
			@NotNull List<Long> sendMemberIds) {
		super();
		this.id = id;
		this.subject = subject;
		this.beforeSql = beforeSql;
		this.runSql = runSql;
		this.sendMethod = sendMethod;
		this.enableYN = enableYN;
		this.schedule = schedule;
		this.databaseId = databaseId;
		this.sendMemberIds = sendMemberIds;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public List<Long> getSendMemberIds() {
		return sendMemberIds;
	}

	public void setSendMemberIds(List<Long> sendMemberIds) {
		this.sendMemberIds = sendMemberIds;
	}
}