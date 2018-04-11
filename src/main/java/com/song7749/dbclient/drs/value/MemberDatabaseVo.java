package com.song7749.dbclient.drs.value;

import com.song7749.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 데이터베이스 권한 정보")
public class MemberDatabaseVo extends AbstractVo {

	private static final long serialVersionUID = -4797869753619806357L;

	@ApiModelProperty("데이터베이스 권한정보 ID")
	private Long id;

	@ApiModelProperty("권한이 부여된 데이터 베이스 정보")
	private DatabaseVo databaseVo;

	public MemberDatabaseVo() {}

	/**
	 * @param id
	 * @param databaseVo
	 */
	public MemberDatabaseVo(Long id, DatabaseVo databaseVo) {
		this.id = id;
		this.databaseVo = databaseVo;
	}

	public Long getId() {
		return id;
	}

	public DatabaseVo getDatabaseVo() {
		return databaseVo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDatabaseVo(DatabaseVo databaseVo) {
		this.databaseVo = databaseVo;
	}
}