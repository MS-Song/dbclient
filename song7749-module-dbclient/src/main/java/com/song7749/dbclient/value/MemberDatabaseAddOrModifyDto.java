package com.song7749.dbclient.value;

import com.song7749.common.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MemberDatabaseAddOrModifyDto.java
 * Description : 회원과 Database 간의 연결 수정
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 10.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 3. 10.
 */
@ApiModel("회원 데이터 베이스 권한 추가 또는 삭제")
public class MemberDatabaseAddOrModifyDto extends AbstractDto {

	private static final long serialVersionUID = -6283249657510420187L;


	@ApiModelProperty(required=true,value="MemberDatabaseId")
	private Long id;

	@ApiModelProperty(required = true, value = "DatabaseId")
	private Long databaseId;

	@ApiModelProperty(required = true, value = "MmeberId")
	private Long memberId;

	public MemberDatabaseAddOrModifyDto() {}

	/**
	 * @param id
	 */
	public MemberDatabaseAddOrModifyDto(Long id) {
		this.id = id;
	}

	/**
	 * @param databaseId
	 * @param memberId
	 */
	public MemberDatabaseAddOrModifyDto(Long databaseId, Long memberId) {
		super();
		this.databaseId = databaseId;
		this.memberId = memberId;
	}



	/**
	 * @param id
	 * @param databaseId
	 * @param memberId
	 */
	public MemberDatabaseAddOrModifyDto(Long id, Long databaseId, Long memberId) {
		this.id = id;
		this.databaseId = databaseId;
		this.memberId = memberId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}