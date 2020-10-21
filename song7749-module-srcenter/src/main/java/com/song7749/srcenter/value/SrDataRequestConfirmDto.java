package com.song7749.srcenter.value;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : SrDataRequestConfirmDto
 * Description : 승인 DTO 객체
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  13/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 13/11/2019
 */
@ApiModel("SR Data Request 승인")
public class SrDataRequestConfirmDto extends AbstractDto {
    private static final long serialVersionUID = -8181113507101232694L;

    @NotNull
    @ApiModelProperty(required = true, position = 1, dataType = "Long", value="ID")
    private Long id;

    @ApiModelProperty(required = false, position = 3, dataType = "YN", value="승인여부 || 동장여부와 승인여부가 결합되어 사용 가능 상태로 표기됨")
    private YN confirmYN;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(required = false, position = 6, hidden = true, dataType = "Date", value="승인일시")
    private Date confirmDate;

    @ApiModelProperty(required=false, position=13, hidden = true, value="승인자")
    private Long confirmMemberId;

    public SrDataRequestConfirmDto() {
    }

    public SrDataRequestConfirmDto(@NotNull Long id, YN confirmYN, Date confirmDate, Long confirmMemberId) {
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