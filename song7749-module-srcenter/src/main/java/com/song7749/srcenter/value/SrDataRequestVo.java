package com.song7749.srcenter.value;

import com.song7749.common.AbstractVo;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.member.domain.Member;
import com.song7749.member.value.MemberVo;
import com.song7749.srcenter.type.DownloadLimitType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestVo
 * Description : SR Data Request 정보 반환 객체
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

@ApiModel("SR DATA REQUEST MODEL")
public class SrDataRequestVo extends AbstractVo {
    private static final long serialVersionUID = -1230258023827961792L;

    @ApiModelProperty(value="No", position = 1, dataType = "INT")
    private Long id;

    @ApiModelProperty(value="제목", position = 2, dataType = "String")
    private String subject;

    @ApiModelProperty(value="Run SQL", position = 3, dataType = "String")
    private String runSql;

    @ApiModelProperty(value="다운로드 제한", position = 4, dataType = "INT")
    private Integer downloadLimit;

    @ApiModelProperty(value="다운로드 회수", position = 5, dataType = "INT")
    private Integer downloadCount;

    @ApiModelProperty(value="다운로드 제한 기간", position = 6, dataType = "DownloadLimitType")
    private DownloadLimitType downloadLimitType;

    @ApiModelProperty(value="다운로드 가능 기간", position = 7, dataType = "Date")
    private Date downloadStartDate;

    @ApiModelProperty(value="다운로드 가능 기간", position = 8, dataType = "Date")
    private Date downloadEndDate;

    @ApiModelProperty(value="동작성태", position = 9, dataType = "YN")
    private YN enableYN;

    @ApiModelProperty(value="승인상태", position = 10, dataType = "YN")
    private YN confirmYN;

    @ApiModelProperty(value="생성일", position = 11, dataType = "Date")
    private Date createDate;

    @ApiModelProperty(value="승인일", position = 12, dataType = "Date")
    private Date confirmDate;

    @ApiModelProperty(value="마지막 실행일", position = 13, dataType = "Date")
    private Date lastRunDate;

    @ApiModelProperty(value="마지막 에러메세지", position = 14, dataType = "String")
    private String lastErrorMessage;

    @ApiModelProperty(value="Database", position = 15, dataType = "DatabaseVo")
    private DatabaseVo databaseVo;

    @ApiModelProperty(value="등록자", position = 16, dataType = "MemberVo")
    private MemberVo resistMemberVo;

    @ApiModelProperty(value="승인자", position = 17, dataType = "MemberVo")
    private MemberVo confirmMemberVo;

    @ApiModelProperty(value="SQL 조건", position = 18, dataType = "SrDataConditionVo")
    private List<SrDataConditionVo> srDataConditionVos;

    private List<Long> srDataAllowMemberIds;

    public SrDataRequestVo() { }

    public SrDataRequestVo(Long id, String subject, String runSql, Integer downloadLimit, Integer downloadCount, DownloadLimitType downloadLimitType, Date downloadStartDate, Date downloadEndDate, YN enableYN, YN confirmYN, Date createDate, Date confirmDate, Date lastRunDate, String lastErrorMessage, DatabaseVo databaseVo, MemberVo resistMemberVo, MemberVo confirmMemberVo, List<SrDataConditionVo> srDataConditionVos, List<Long> srDataAllowMemberIds) {
        this.id = id;
        this.subject = subject;
        this.runSql = runSql;
        this.downloadLimit = downloadLimit;
        this.downloadCount = downloadCount;
        this.downloadLimitType = downloadLimitType;
        this.downloadStartDate = downloadStartDate;
        this.downloadEndDate = downloadEndDate;
        this.enableYN = enableYN;
        this.confirmYN = confirmYN;
        this.createDate = createDate;
        this.confirmDate = confirmDate;
        this.lastRunDate = lastRunDate;
        this.lastErrorMessage = lastErrorMessage;
        this.databaseVo = databaseVo;
        this.resistMemberVo = resistMemberVo;
        this.confirmMemberVo = confirmMemberVo;
        this.srDataConditionVos = srDataConditionVos;
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

    public Integer getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(Integer downloadLimit) {
        this.downloadLimit = downloadLimit;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
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

    public List<SrDataConditionVo> getSrDataConditionVos() {
        return srDataConditionVos;
    }

    public void setSrDataConditionVos(List<SrDataConditionVo> srDataConditionVos) {
        this.srDataConditionVos = srDataConditionVos;
    }

    public List<Long> getSrDataAllowMemberIds() {
        return srDataAllowMemberIds;
    }

    public void setSrDataAllowMemberIds(List<Long> srDataAllowMemberIds) {
        this.srDataAllowMemberIds = srDataAllowMemberIds;
    }
}