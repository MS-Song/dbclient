package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
import com.song7749.member.domain.Member;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestModifyAfterConfirmDto
 * Description : 승인 후 수정 가능 DTO 객체
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
@ApiModel("SR Data Request 승인 후 수정")
public class SrDataRequestModifyAfterConfirmDto extends AbstractDto {

    private static final long serialVersionUID = 5898888028458247099L;

    @NotNull
    @ApiModelProperty(required = true, position = 1, dataType = "Long", value="번호")
    private Long id;

    @Size(min=8,max=200)
    @ApiModelProperty(required = true, position = 2, dataType = "String", value="제목 || 최대한 현업 담당자와 커뮤니케이션이 원활하도록 작성 EX) [서비스명] 구체적인 내용 ")
    private String subject;

    @ApiModelProperty(required = false, position = 3, dataType = "String", value="동작상태 || 동장여부와 승인여부가 결합되어 사용 가능 상태로 표기됨")
    private YN enableYN;

    @ApiModelProperty(required = false, position = 3, dataType = "String", value="다운로드 제한 숫자 || 월/주/일/시간 당 다운로드 허용 횟수")
    private Integer downloadLimit;

    @ApiModelProperty(required = false, position = 4, dataType = "DownloadLimitType", value="다운로드 제한 타입 || 다운로드 제한을 월/주/일/시 로 설정, 타입을 정하지 않으면 해당 기능의 전체 카운트로 셋팅됨 ")
    private DownloadLimitType downloadLimitType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(required = false, position = 5, dataType = "Date", value="다운로드 가능 시작 일시")
    private Date downloadStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(required = false, position = 6, dataType = "Date", value="다운로드 가능 종료 일시")
    private Date downloadEndDate;

    @ApiModelProperty(required=false, position=13, value="허용 사용자 || 해당 기능의 사용이 허용된 사용자 선택")
    private List<Long> srDataAllowMemberIds;

    @ApiModelProperty(required=true, position=8, hidden=true, value="수정자 ID")
    private Long memberId;

    public SrDataRequestModifyAfterConfirmDto() {}

    public SrDataRequestModifyAfterConfirmDto(@NotNull Long id, @Size(min = 8, max = 200) String subject, YN enableYN, Integer downloadLimit, DownloadLimitType downloadLimitType, Date downloadStartDate, Date downloadEndDate, List<Long> srDataAllowMemberIds, Long memberId) {
        this.id = id;
        this.subject = subject;
        this.enableYN = enableYN;
        this.downloadLimit = downloadLimit;
        this.downloadLimitType = downloadLimitType;
        this.downloadStartDate = downloadStartDate;
        this.downloadEndDate = downloadEndDate;
        this.srDataAllowMemberIds = srDataAllowMemberIds;
        this.memberId = memberId;
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

    public YN getEnableYN() {
        return enableYN;
    }

    public void setEnableYN(YN enableYN) {
        this.enableYN = enableYN;
    }

    public Integer getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(Integer downloadLimit) {
        this.downloadLimit = downloadLimit;
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

    public List<Long> getSrDataAllowMemberIds() {
        return srDataAllowMemberIds;
    }

    public void setSrDataAllowMemberIds(List<Long> srDataAllowMemberIds) {
        this.srDataAllowMemberIds = srDataAllowMemberIds;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}