package com.song7749.dl.base;

public class Dto extends BaseObject{

	private static final long serialVersionUID = 8863605294397638654L;

	/**
	 * Limit. <br/>
	 * 개발자의 실수를 방지하기 위해서 최대 값을 1000으로 제한한다.<br/>
	 */
	private Long limit = 1000L;
	/**
	 * offset.<br/>
	 */
	private Long offset = 0L;
	/**
	 * cache 사용 여부 .<br/>
	 * 캐시를 사용할 경우 true 로 값을 설정 한다 .<br/>
	 */
	private final boolean useCache=false;
	/**
	 * limit 사용 여부 .<br/>
	 */
	private boolean useLimit=true;

	/**
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
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
	 * @param offset the offset to set
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
	 * @param useLimit the useLimit to set
	 */
	public void setUseLimit(boolean useLimit) {
		this.useLimit = useLimit;
	}
}