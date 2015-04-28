package com.song7749.dl.base;

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
public abstract class AbstractDto extends BaseObject implements Dto {

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
	 * limit 사용 여부 .<br/>
	 */
	private boolean useLimit = true;

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
}