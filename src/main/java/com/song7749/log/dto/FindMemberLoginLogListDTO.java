package com.song7749.log.dto;

import java.util.Date;

import com.song7749.dl.base.AbstractDto;

/**
 * <pre>
 * Class Name : FindMemberLoginLogListDTO.java
 * Description : 회원 로그인 로그 조회 DTO
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public class FindMemberLoginLogListDTO extends AbstractDto {

	private static final long serialVersionUID = 1877059968781803796L;

	private String id;

	private String ip;

	private Date startDate;

	private Date endDate;

	public FindMemberLoginLogListDTO() {}

	public FindMemberLoginLogListDTO(String id, String ip, Date startDate,
			Date endDate) {
		this.id = id;
		this.ip = ip;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}