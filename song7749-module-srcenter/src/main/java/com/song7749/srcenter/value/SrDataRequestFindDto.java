package com.song7749.srcenter.value;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.member.domain.Member;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.type.DownloadLimitType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestFindDto
 * Description : 리스트 검색 기능
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
@ApiModel("SR Data Request 리스트 검색 기능")
public class SrDataRequestFindDto extends AbstractDto implements Specification<SrDataRequest> {

    private static final long serialVersionUID = -819742433467572229L;

    @ApiModelProperty(required = false, position = 1, dataType = "Long", value="ID")
    private Long id;

    @Size(min=8,max=200)
    @ApiModelProperty(required = false, position = 2, dataType = "String", value="제목 || 최대한 현업 담당자와 커뮤니케이션이 원활하도록 작성 EX) [서비스명] 구체적인 내용 ")
    private String subject;

    @Size(min=8,max=12000)
    @ApiModelProperty(required = false, position = 3, dataType = "String", value="SQL || 실행할 SQL 을 작성하고 검색 조건에 매치되는 파라메터를 ${변수명} 형식으로 입력")
    private String runSql;

    @ApiModelProperty(required = false, position = 9, dataType = "YN", value="작동여부 || 동장여부와 승인여부가 결합되어 사용 가능 상태로 표기됨")
    private YN enableYN;

    @ApiModelProperty(required = false, position = 10, dataType = "YN", value="승인여부 || 동장여부와 승인여부가 결합되어 사용 가능 상태로 표기됨")
    private YN confirmYN;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="등록일 검색 시작",position=11)
    private Date fromCreateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="등록일 검색 종료",position=12)
    private Date toCreateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="승인일 검색 시작",position=13)
    private Date fromConfirmDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="승인일 검색 종료",position=14)
    private Date toConfirmDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="마지막 실행일 검색 시작",position=15)
    private Date fromLastRunDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="마지막 실행일 검색 종료",position=16)
    private Date toLastRunDate;

    @ApiModelProperty(required = false, position = 17, dataType = "String", value="에러메세지")
    private String lastErrorMessage;

    @ApiModelProperty(required = false, position = 18, dataType = "Date", value="데이터베이스 선택")
    private Long databaseId;

    @ApiModelProperty(required=false, position=19, dataType = "Integer", value="등록자 ID")
    private Long resistMemberId;

    @ApiModelProperty(required=false, position=20, dataType = "Integer", value="승인자 ID")
    private Long confirmMemberId;

    @ApiModelProperty(required=false, position=21, value="사용허용자 || 해당 기능의 사용이 허용된 사용자 선택")
    private List<Long> srDataAllowMemberIds;

    public SrDataRequestFindDto() {
    }

    public SrDataRequestFindDto(Long id) {
        this.id = id;
    }

    public SrDataRequestFindDto(Long id, @Size(min = 8, max = 200) String subject, @Size(min = 8, max = 12000) String runSql, YN enableYN, YN confirmYN, Date fromCreateDate, Date toCreateDate, Date fromConfirmDate, Date toConfirmDate, Date fromLastRunDate, Date toLastRunDate, String lastErrorMessage, Long databaseId, Long resistMemberId, Long confirmMemberId, List<Long> srDataAllowMemberIds) {
        this.id = id;
        this.subject = subject;
        this.runSql = runSql;
        this.enableYN = enableYN;
        this.confirmYN = confirmYN;
        this.fromCreateDate = fromCreateDate;
        this.toCreateDate = toCreateDate;
        this.fromConfirmDate = fromConfirmDate;
        this.toConfirmDate = toConfirmDate;
        this.fromLastRunDate = fromLastRunDate;
        this.toLastRunDate = toLastRunDate;
        this.lastErrorMessage = lastErrorMessage;
        this.databaseId = databaseId;
        this.resistMemberId = resistMemberId;
        this.confirmMemberId = confirmMemberId;
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

    public YN getConfirmYN() {
        return confirmYN;
    }

    public void setConfirmYN(YN confirmYN) {
        this.confirmYN = confirmYN;
    }

    public Date getFromCreateDate() {
        return fromCreateDate;
    }

    public void setFromCreateDate(Date fromCreateDate) {
        this.fromCreateDate = fromCreateDate;
    }

    public Date getToCreateDate() {
        return toCreateDate;
    }

    public void setToCreateDate(Date toCreateDate) {
        this.toCreateDate = toCreateDate;
    }

    public Date getFromConfirmDate() {
        return fromConfirmDate;
    }

    public void setFromConfirmDate(Date fromConfirmDate) {
        this.fromConfirmDate = fromConfirmDate;
    }

    public Date getToConfirmDate() {
        return toConfirmDate;
    }

    public void setToConfirmDate(Date toConfirmDate) {
        this.toConfirmDate = toConfirmDate;
    }

    public Date getFromLastRunDate() {
        return fromLastRunDate;
    }

    public void setFromLastRunDate(Date fromLastRunDate) {
        this.fromLastRunDate = fromLastRunDate;
    }

    public Date getToLastRunDate() {
        return toLastRunDate;
    }

    public void setToLastRunDate(Date toLastRunDate) {
        this.toLastRunDate = toLastRunDate;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public Long getResistMemberId() {
        return resistMemberId;
    }

    public void setResistMemberId(Long resistMemberId) {
        this.resistMemberId = resistMemberId;
    }

    public Long getConfirmMemberId() {
        return confirmMemberId;
    }

    public void setConfirmMemberId(Long confirmMemberId) {
        this.confirmMemberId = confirmMemberId;
    }

    public List<Long> getSrDataAllowMemberIds() {
        return srDataAllowMemberIds;
    }

    public void setSrDataAllowMemberIds(List<Long> srDataAllowMemberIds) {
        this.srDataAllowMemberIds = srDataAllowMemberIds;
    }

    @Override
    public Predicate toPredicate(Root<SrDataRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();

        if(id != null) {
            p.getExpressions()
                    .add(cb.equal(root.<Long>get("id"), id));
        }

        if(!StringUtils.isEmpty(subject)) {
            p.getExpressions()
                    .add(cb.like(root.<String>get("subject"),  "%" + subject + "%"));
        }

        if(!StringUtils.isEmpty(runSql)) {
            p.getExpressions()
                    .add(cb.like(root.<String>get("runSql"),  "%" + runSql + "%"));
        }

        if(null!=enableYN) {
            p.getExpressions()
                    .add(cb.equal(root.<YN>get("enableYN"), enableYN));
        }

        if(null!=confirmYN) {
            p.getExpressions()
                    .add(cb.equal(root.<YN>get("confirmYN"), confirmYN));
        }

        if(null!=databaseId) {
            p.getExpressions()
                    .add(cb.equal(root.<Database>get("database"), new Database(databaseId)));
        }

        if(null!=resistMemberId) {
            p.getExpressions()
                    .add(cb.equal(root.<Member>get("resistMember"), new Member(resistMemberId)));
        }

        if(null!=confirmMemberId) {
            p.getExpressions()
                    .add(cb.equal(root.<Member>get("confirmMember"), new Member(confirmMemberId)));
        }

        if(null!=srDataAllowMemberIds
                && srDataAllowMemberIds.size()>0) {
            // in 사용을 위해서는 join 필요..
            Join<SrDataRequest, Member> groupPath = root.join("srDataAllowMembers", JoinType.INNER);
            p.getExpressions().add(
                    groupPath.in(srDataAllowMemberIds)
            );
        }

        if(!StringUtils.isEmpty(lastErrorMessage)) {
            p.getExpressions()
                    .add(cb.like(root.<String>get("lastErrorMessage"),  "%" + lastErrorMessage + "%"));
        }

        if(null!=fromCreateDate && null!=toCreateDate) {
            p.getExpressions()
                    .add(cb.between(root.<Date>get("createDate"), fromCreateDate, toCreateDate));
        } else if(null!=fromCreateDate) {
            p.getExpressions()
                    .add(cb.greaterThanOrEqualTo(root.<Date>get("createDate"), fromCreateDate));
        } else if(null!=toCreateDate) {
            p.getExpressions()
                    .add(cb.lessThanOrEqualTo(root.<Date>get("createDate"), toCreateDate));
        }

        if(null!=fromConfirmDate && null!=toConfirmDate) {
            p.getExpressions()
                    .add(cb.between(root.<Date>get("confirmDate"), fromConfirmDate, toConfirmDate));
        } else if(null!=fromConfirmDate) {
            p.getExpressions()
                    .add(cb.greaterThanOrEqualTo(root.<Date>get("confirmDate"), fromConfirmDate));
        } else if(null!=toConfirmDate) {
            p.getExpressions()
                    .add(cb.lessThanOrEqualTo(root.<Date>get("confirmDate"), toConfirmDate));
        }

        if(null!=fromLastRunDate && null!=toLastRunDate) {
            p.getExpressions()
                    .add(cb.between(root.<Date>get("lastRunDate"), fromLastRunDate, toLastRunDate));
        } else if(null!=fromLastRunDate) {
            p.getExpressions()
                    .add(cb.greaterThanOrEqualTo(root.<Date>get("lastRunDate"), fromLastRunDate));
        } else if(null!=toLastRunDate) {
            p.getExpressions()
                    .add(cb.lessThanOrEqualTo(root.<Date>get("lastRunDate"), toLastRunDate));
        }

        return p;
    }
}