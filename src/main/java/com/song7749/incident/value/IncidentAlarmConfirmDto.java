package com.song7749.incident.value;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractDto;
import com.song7749.base.YN;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("알람승인")
public class IncidentAlarmConfirmDto  extends AbstractDto {

	private static final long serialVersionUID = 4873331161290514134L;

	@ApiModelProperty(value="알람 ID")
	@NotNull
	private Long id;

	@ApiModelProperty(value="승인여부")
	@NotNull
	@Enumerated(EnumType.STRING)
	private YN confirmYN;

	@ApiModelProperty(value="승인일자")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date confirmDate;

	@ApiModelProperty(value="승인자ID")
	@NotNull
	private Long confirmMemberId;

	public IncidentAlarmConfirmDto() {}

	/**
	 * @param id
	 * @param confirmYN
	 * @param confirmDate
	 * @param memberId
	 */
	public IncidentAlarmConfirmDto(@NotNull Long id, @NotNull YN confirmYN, @NotNull Date confirmDate,
			@NotNull Long confirmMemberId) {
		this.id = id;
		this.confirmYN = confirmYN;
		this.confirmDate = confirmDate;
		this.confirmMemberId = confirmMemberId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public YN getConfirmYN() {
		return confirmYN;
	}

	public void setConfirmYN(YN confirmYN) {
		this.confirmYN = confirmYN;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Long getConfirmMemberId() {
		return confirmMemberId;
	}

	public void setConfirmMemberId(Long confirmMemberId) {
		this.confirmMemberId = confirmMemberId;
	}
}