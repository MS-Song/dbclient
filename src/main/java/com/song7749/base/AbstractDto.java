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

	private String apikey;

	public AbstractDto() {
	}

	public AbstractDto(String apikey) {
		this.apikey = apikey;
	}

	public AbstractDto(boolean useCache, String apikey) {
		this.useCache = useCache;
		this.apikey = apikey;
	}

	@Override
	public boolean isUseCache() {
		return useCache;
	}

	@Override
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
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