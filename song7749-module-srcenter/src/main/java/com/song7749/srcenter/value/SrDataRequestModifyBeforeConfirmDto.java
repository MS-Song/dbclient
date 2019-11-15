package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestModifyBeforeConfirmDto
 * Description : 승인 전 상태 수정 DTO 객체
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
public class SrDataRequestModifyBeforeConfirmDto extends AbstractDto {

    private static final long serialVersionUID = 4902108636402278902L;

    @NotNull
    @ApiModelProperty(value="ID", required = true, position = 1, dataType = "Long")
    private Long id;

    @Size(min=8,max=200)
    @ApiModelProperty(value="제목 || 최대한 현업 담당자와 커뮤니케이션이 원활하도록 작성 EX) [서비스명] 구체적인 내용 ", required = true, position = 1, dataType = "String")
    private String subject;

    @Size(min=8,max=12000)
    @ApiModelProperty(value="SQL || 실행할 SQL 을 작성하고 검색 조건에 매치되는 파라메터를 ${변수명} 형식으로 입력", required = true, position = 2, dataType = "String")
    private String runSql;

    @ApiModelProperty(value="작동여부 || 동장여부와 승인여부가 결합되어 사용 가능 상태로 표기됨", required = true, position = 2, dataType = "String")
    private YN enableYN;

    @ApiModelProperty(value="다운로드 제한 숫자 || 월/주/일/시간 당 다운로드 허용 횟수", required = false, position = 3, dataType = "String")
    private Integer downloadLimit;

    @ApiModelProperty(value="다운로드 제한 타입 || 다운로드 제한을 월/주/일/시 로 설정, 타입을 정하지 않으면 해당 기능의 전체 카운트로 셋팅됨 ", required = false, position = 4, dataType = "DownloadLimitType")
    private DownloadLimitType downloadLimitType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="다운로드 가능 시작 일시", required = false, position = 5, dataType = "Date")
    private Date downloadStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="다운로드 가능 종료 일시", required = false, position = 6, dataType = "Date")
    private Date downloadEndDate;

    @ApiModelProperty(value="데이터베이스 선택", required = false, position = 7, dataType = "Date")
    private Long databaseId;

    @ApiModelProperty(value="검색 조건 명", required=true,position=8,hidden=true)
    private List<String> conditionName;

    @ApiModelProperty(value="SQL 조건 변수명", required=true,position=8,hidden=true)
    private List<String> conditionKey;

    @ApiModelProperty(value="SQL 데이터 타입", required=true,position=8,hidden=true)
    private List<DataType> conditionType;

    @ApiModelProperty(value="SQL 조건 변수의 값", required=true,position=8,hidden=true)
    private List<String> conditionValue;

    @ApiModelProperty(value="SQL 조건 변수의 값", required=true,position=8,hidden=true)
    private List<Long> srDataAllowMemberIds;

    public SrDataRequestModifyBeforeConfirmDto() { }

    public SrDataRequestModifyBeforeConfirmDto(@NotNull Long id, @Size(min = 8, max = 200) String subject, @Size(min = 8, max = 12000) String runSql, YN enableYN, Integer downloadLimit, DownloadLimitType downloadLimitType, Date downloadStartDate, Date downloadEndDate, Long databaseId, List<String> conditionName, List<String> conditionKey, List<DataType> conditionType, List<String> conditionValue, List<Long> srDataAllowMemberIds) {
        this.id = id;
        this.subject = subject;
        this.runSql = runSql;
        this.enableYN = enableYN;
        this.downloadLimit = downloadLimit;
        this.downloadLimitType = downloadLimitType;
        this.downloadStartDate = downloadStartDate;
        this.downloadEndDate = downloadEndDate;
        this.databaseId = databaseId;
        this.conditionName = conditionName;
        this.conditionKey = conditionKey;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.srDataAllowMemberIds = srDataAllowMemberIds;
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

    public String getRunSql() {
        return runSql;
    }

    public void setRunSql(String runSql) {
        this.runSql = runSql;
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

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public List<String> getConditionName() {
        return conditionName;
    }

    public void setConditionName(List<String> conditionName) {
        this.conditionName = conditionName;
    }

    public List<String> getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(List<String> conditionKey) {
        this.conditionKey = conditionKey;
    }

    public List<DataType> getConditionType() {
        return conditionType;
    }

    public void setConditionType(List<DataType> conditionType) {
        this.conditionType = conditionType;
    }

    public List<String> getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(List<String> conditionValue) {
        this.conditionValue = conditionValue;
    }

    public List<Long> getSrDataAllowMemberIds() {
        return srDataAllowMemberIds;
    }

    public void setSrDataAllowMemberIds(List<Long> srDataAllowMemberIds) {
        this.srDataAllowMemberIds = srDataAllowMemberIds;
    }
}