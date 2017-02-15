package com.song7749.dl.base;

import com.song7749.cache.Cacheable;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : AbstractDto.java
 * Description : 모돈 DTO 는 AbstractDto 를 구현해야 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 4. 28.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 4. 28.
 */
@ApiModel("BASE DTO")
public abstract class AbstractDto extends BaseObject implements Dto , Cacheable{

	private static final long serialVersionUID = 8863605294397638654L;

	/**
	 * Limit. <br/>
	 * 개발자의 실수를 방지하기 위해서 최대 값을 100으로 제한한다.<br/>
	 */
	@ApiModelProperty("최대 조회 개수")
	private Long limit = 100L;
	/**
	 * offset.<br/>
	 */
	@ApiModelProperty("조회 시작 Offset")
	private Long offset = 0L;

	/**
	 * limit 사용 여부 .<br/>
	 */
	@ApiModelProperty("Result 수를 지정할 것인가?")
	private boolean useLimit = true;

	@ApiModelProperty("캐시를 사용할 것인가?")
	private boolean useCache = false;

	/**
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Long limit) {
		this.limit = limit;
	}

	/**
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	/**
	 * @return the useLimit
	 */
	public boolean isUseLimit() {
		return useLimit;
	}

	/**
	 * @param useLimit
	 *            the useLimit to set
	 */
	public void setUseLimit(boolean useLimit) {
		this.useLimit = useLimit;
	}

	@Override
	public boolean isUseCache() {
		return useCache;
	}

	@Override
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
}