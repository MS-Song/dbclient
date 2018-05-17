package com.song7749.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <pre>
 * Class Name : AbstractDto.java
 * Description : 모든 조회성 DTO 는 Abstract DTO를 상속 받아야 한다.
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
public abstract class AbstractDto extends BaseObject implements Dto, Cacheable {

	private static final long serialVersionUID = 8863605294397638654L;

	private boolean useCache = false;

	private String apiAuthkey;

	public AbstractDto() {
	}

	public AbstractDto(String apiAuthkey) {
		this.apiAuthkey = apiAuthkey;
	}

	public AbstractDto(boolean useCache, String apiAuthkey) {
		this.useCache = useCache;
		this.apiAuthkey = apiAuthkey;
	}

	@Override
	public boolean isUseCache() {
		return useCache;
	}

	@Override
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public String getApiAuthkey() {
		return apiAuthkey;
	}

	public void setApiAuthkey(String apiAuthkey) {
		this.apiAuthkey = apiAuthkey;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}