package com.song7749.srcenter.domain;

import com.song7749.common.Entities;
import com.song7749.common.YN;
import com.song7749.dbclient.domain.Database;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * <pre>
 * Class Name : SrDataRequest
 * Description : SR 데이터 요청에 대한 모델
*
*  Modification Information
*  Modify Date 		Modifier	            Comment
*  -----------------------------------------------
*  11/11/2019		song7749@gmail.com	    NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 11/11/2019
*
*
*/
public class SrDataRequest extends Entities {

    private static final long serialVersionUID = -932046749163739343L;

    @Id
    @Column(name="sr_data_request_id", nullable=false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 200)
    @Column(nullable = false)
    private String subject;

    @Lob
    @NotBlank
    @Length(max = 12000)
    @Column(nullable = false)
    private String runSql;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private YN enableYN;

    @Enumerated(EnumType.STRING)
    @Column(nullable=true)
    private YN confirmYN;

    @CreationTimestamp
    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmDate;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRunDate;

    @ManyToOne(targetEntity= Database.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "database_id", nullable = true, insertable = true, updatable = true)
    private Database database;



}
