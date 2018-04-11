package com.song7749.dbclient.drs.value;

import java.util.List;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : DatabaseFindDto.java
 * Description : Database 정보 조회 Dto
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 26.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 26.
*/

@ApiModel("database 정보 조회")
public class DatabaseFindDto extends AbstractDto {

	private static final long serialVersionUID = -3593255203717899616L;

	@ApiModelProperty("Database id")
	private Long id;

	@ApiModelProperty(value="Database ids, internal", hidden=true)
	private List<Long> ids;

	@ApiModelProperty("Database host")
	private String host;


	public DatabaseFindDto() {}

	/**
	 * @param apikey
	 */
	public DatabaseFindDto(String apikey) {
		super(apikey);
	}

	/**
	 * @param id
	 * @param host
	 */
	public DatabaseFindDto(Long id) {
		this.id = id;
	}

	/**
	 * @param apikey
	 * @param id
	 * @param host
	 */
	public DatabaseFindDto(String apikey, Long id) {
		super(apikey);
		this.id = id;
	}

	/**
	 * @param apikey
	 * @param ids
	 * @param host
	 */
	public DatabaseFindDto(String apikey, List<Long> ids) {
		super(apikey);
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}