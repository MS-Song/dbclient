package com.song7749.dsr.domain;

import java.util.Date;
import java.util.List;

import com.song7749.base.Entities;
import com.song7749.base.YN;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.Member;

/**
 * <pre>
 * Class Name : DataServiceRequest.java
 * Description : 데이터 서비스 리퀘스트 도메인 모델
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 6. 20.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 6. 20.
*/

//@Entity
//@SelectBeforeUpdate(true)
//@DynamicUpdate(true)
public class DataServiceRequest extends Entities {

	private static final long serialVersionUID = 7673485122385650291L;

//	@Id
//	@Column(name="data_service_request_id", nullable=false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String subject;

	private String sql;

	private YN enableYN;

	private List<DataServiceParameter> parameters;

	private Database database;

	private Member resistMember;

	private List<Member> useMembers;

	private Date createDate;

	private Date updateDate;
}