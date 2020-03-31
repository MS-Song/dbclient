package com.song7749.srcenter.value;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.song7749.common.AbstractDto;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.member.domain.Member;
import com.song7749.srcenter.domain.SrDataRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(required = false, position = 2, dataType = "String", value="제목")
    private String subject;

    @ApiModelProperty(required = false, position = 3, dataType = "String", value="runSQL")
    private String runSql;

    @ApiModelProperty(required = false, position = 9, dataType = "YN", value="동작상태")
    private YN enableYN;

    @ApiModelProperty(required = false, position = 10, dataType = "YN", value="승인상태")
    private YN confirmYN;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="생성일 검색 시작",position=11)
    private Date fromCreateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value="생성일 검색 종료",position=12)
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

    @ApiModelProperty(required=false, position=21, value="허용 사용자")
    private List<Long> srDataAllowMemberIds;

    @ApiModelProperty(required=false, position=22, hidden = true, value="실행자")
    private Long runMemberId;

    public SrDataRequestFindDto() {}

    public SrDataRequestFindDto(Long id) {
        this.id = id;
    }

    public SrDataRequestFindDto(Long id, String subject, String runSql, YN enableYN, YN confirmYN, Date fromCreateDate, Date toCreateDate, Date fromConfirmDate, Date toConfirmDate, Date fromLastRunDate, Date toLastRunDate, String lastErrorMessage, Long databaseId, Long resistMemberId, Long confirmMemberId, List<Long> srDataAllowMemberIds, Long runMemberId) {
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
        this.runMemberId = runMemberId;
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

    public Long getRunMemberId() {
        return runMemberId;
    }

    public void setRunMemberId(Long runMemberId) {
        this.runMemberId = runMemberId;
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

            // SQL 데이터는 방화벽에서 xss 로 차단됨으로, 디코드해서 사용해야 한다.
            String convRunSQL=null;
            try {
                convRunSQL =
                        URLDecoder.decode(
                                new String(
                                		Base64.getDecoder().decode(runSql)
                                        , Charset.forName("UTF-8"))
                                , "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("run SQL 의 복호화 실패!! 관리자에게 문의 하세요");
            }

            p.getExpressions()
                    .add(cb.like(root.<String>get("runSql"),  "%" + convRunSQL + "%"));
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