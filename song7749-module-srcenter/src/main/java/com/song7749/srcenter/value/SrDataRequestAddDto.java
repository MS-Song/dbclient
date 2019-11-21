package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
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
    @ApiModelProperty(required = true, position = 1, dataType = "String", value="제목 || 최대한 현업 담당자와 커뮤니케이션이 원활하도록 작성 EX) [서비스명] 구체적인 내용 ")
    private String subject;

    @NotNull
    @Size(min=8,max=12000)
    @ApiModelProperty(required = true, position = 2, dataType = "String", value="SQL || 실행할 SQL 을 작성하고 검색 조건에 매치되는 파라메터를 ${변수명} 형식으로 입력")
    private String runSql;

    @ApiModelProperty(required = false, position = 3, dataType = "String", value="다운로드 제한 숫자 || 월/주/일/시간 당 다운로드 허용 횟수")
    private Integer downloadLimit;

    @ApiModelProperty(required = false, position = 4, dataType = "DownloadLimitType", value="다운로드 제한 타입 || 다운로드 제한을 월/주/일/시 로 설정, 타입을 정하지 않으면 해당 기능의 전체 카운트로 셋팅됨 ")
    private DownloadLimitType downloadLimitType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(required = false, position = 5, dataType = "Date", value="다운로드 가능 시작 일시")
    private Date downloadStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(required = false, position = 6, dataType = "Date", value="다운로드 가능 종료 일시")
    private Date downloadEndDate;

    @NotNull
    @ApiModelProperty(required = true, position = 7, dataType = "Date", value="데이터베이스 선택")
    private Long databaseId;

    @ApiModelProperty(required=true, position=8, hidden=true, value="등록자 ID")
    private Long memberId;

    @ApiModelProperty(required=true, position=9, value="Where 구문 || where 절 생성  EX) 상품명 : ANd b.xxxx={keyName}")
    private List<String> conditionWhereSql;

    @ApiModelProperty(required=true, position=9, value="검색 조건 명 || where 에 해당하는 검색 조건의 명칭 EX) 상품명 : ")
    private List<String> conditionName;

    @ApiModelProperty( required=true, position=10, value="SQL 조건 변수명 || where 에 바인딩 시킬 변수명 EX) {keyName} ")
    private List<String> conditionKey;

    @ApiModelProperty( required=true, position=11, value="SQL 데이터 타입 || where 에 바인딩 시킬 변수의 타입 EX) Date")
    private List<DataType> conditionType;

    @ApiModelProperty(required=true, position=12, value="SQL 조건 변수의 값 || where 에 바인딩 될 값 - 실행자가 검색 조건으로 입력")
    private List<String> conditionValue;

    @ApiModelProperty(required=true, position=12, value="SQL 조건 변수의 값 || where 에 바인딩 될 값 - 실행자가 검색 조건으로 입력")
    private List<YN> conditionRequired;

    @ApiModelProperty(required=true, position=13, value="필수값 여부 || 사용자가 value 를 넣지 않으면 where 절에 포함되지 않는다.\\n 필수값 설정시 value 가 없으면 실행하지 않는다.")
    private List<Long> srDataAllowMemberIds;

    public SrDataRequestAddDto() {}


    public SrDataRequestAddDto(@NotNull @Size(min = 8, max = 200) String subject, @NotNull @Size(min = 8, max = 12000) String runSql, Integer downloadLimit, DownloadLimitType downloadLimitType, Date downloadStartDate, Date downloadEndDate, @NotNull Long databaseId, Long memberId, List<String> conditionWhereSql, List<String> conditionName, List<String> conditionKey, List<DataType> conditionType, List<String> conditionValue, List<YN> conditionRequired, List<Long> srDataAllowMemberIds) {
        this.subject = subject;
        this.runSql = runSql;
        this.downloadLimit = downloadLimit;
        this.downloadLimitType = downloadLimitType;
        this.downloadStartDate = downloadStartDate;
        this.downloadEndDate = downloadEndDate;
        this.databaseId = databaseId;
        this.memberId = memberId;
        this.conditionWhereSql = conditionWhereSql;
        this.conditionName = conditionName;
        this.conditionKey = conditionKey;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.conditionRequired = conditionRequired;
        this.srDataAllowMemberIds = srDataAllowMemberIds;
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

    public List<String> getConditionWhereSql() {
        return conditionWhereSql;
    }

    public void setConditionWhereSql(List<String> conditionWhereSql) {
        this.conditionWhereSql = conditionWhereSql;
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

    public List<YN> getConditionRequired() {
        return conditionRequired;
    }

    public void setConditionRequired(List<YN> conditionRequired) {
        this.conditionRequired = conditionRequired;
    }

    public List<Long> getSrDataAllowMemberIds() {
        return srDataAllowMemberIds;
    }

    public void setSrDataAllowMemberIds(List<Long> srDataAllowMemberIds) {
        this.srDataAllowMemberIds = srDataAllowMemberIds;
    }
}