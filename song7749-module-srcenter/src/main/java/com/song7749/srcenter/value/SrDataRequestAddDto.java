package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import com.sun.tools.javac.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <pre>
 * Class Name : SrDataRequestDto
 * Description : Sr Data Request 추가 DTO 객체
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
@ApiModel("SR Data Request 추가")
public class SrDataRequestAddDto extends AbstractDto {

    private static final long serialVersionUID = 8105022311613840511L;

    @NotNull
    @Size(min=8,max=200)
    @ApiModelProperty(value="제목 || 최대한 현업 담당자와 커뮤니케이션이 원활하도록 작성 EX) [서비스명] 구체적인 내용 ", required = true, position = 1, dataType = "String")
    private String subject;

    @NotNull
    @Size(min=8,max=12000)
    @ApiModelProperty(value="SQL || 실행할 SQL 을 작성하고 검색 조건에 매치되는 파라메터를 ${변수명} 형식으로 입력", required = true, position = 2, dataType = "String")
    private String runSql;

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

    @NotNull
    @ApiModelProperty(value="데이터베이스 선택", required = false, position = 7, dataType = "Date")
    private Long databaseId;

    @ApiModelProperty(value="등록자 ID", required=true,position=8,hidden=true)
    private Long memberId;

    @ApiModelProperty(value="검색 조건 명", required=true,position=8,hidden=true)
    private List<String> conditionName;

    @ApiModelProperty(value="SQL 조건 변수명", required=true,position=8,hidden=true)
    private List<String> conditionKey;

    @ApiModelProperty(value="SQL 데이터 타입", required=true,position=8,hidden=true)
    private List<DataType> conditionType;

    @ApiModelProperty(value="SQL 조건 변수의 값", required=true,position=8,hidden=true)
    private List<String> conditionValue;

    public SrDataRequestAddDto() {}

    public SrDataRequestAddDto(boolean useCache, String apiAuthkey, @NotNull @Size(min = 8, max = 200) String subject, @NotNull @Size(min = 8, max = 12000) String runSql, Integer downloadLimit, DownloadLimitType downloadLimitType, Date downloadStartDate, Date downloadEndDate, @NotNull Long databaseId, Long memberId, List<String> conditionName, List<String> conditionKey, List<DataType> conditionType, List<String> conditionValue) {
        super(useCache, apiAuthkey);
        this.subject = subject;
        this.runSql = runSql;
        this.downloadLimit = downloadLimit;
        this.downloadLimitType = downloadLimitType;
        this.downloadStartDate = downloadStartDate;
        this.downloadEndDate = downloadEndDate;
        this.databaseId = databaseId;
        this.memberId = memberId;
        this.conditionName = conditionName;
        this.conditionKey = conditionKey;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
}