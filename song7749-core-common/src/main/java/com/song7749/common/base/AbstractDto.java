package com.song7749.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : AbstractDto.java
 * Description : 모든 조회 DTO 는 AbstractDto 를 구현해야 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 15.		song7749@gmail.com		New
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 1. 15.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto extends BaseObject implements Dto, Cacheable {

	private static final long serialVersionUID = 8863605294397638654L;

	private boolean useCache = false;
	private String apiAuthkey;
	
	/**
	 * @param useCache
	 */
	public AbstractDto(boolean useCache) {
		this.useCache = useCache;
	}

	/**
	 * @param apiAuthkey
	 */
	public AbstractDto(String apiAuthkey) {
		this.apiAuthkey = apiAuthkey;
	}
}