package com.song7749.cache;

/**
 * <pre>
 * Class Name : Cacheable.java
 * Description : 캐시 사용 인터페이스 .<br/>
 * 캐시 사용 여부를 결정하는 인터페이스 .<br/>
 *
 *  Modification Information
 *  Modify Date 	Modifier	Comment
 *  -----------------------------------------------
 *  2014. 1. 28.	song7749	신규 작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 1. 28.
 */

public interface Cacheable {
	/**
	 * 캐시 사용여부 .<br/>
	 * @return boolean
	 */
	boolean isUseCache();
	/**
	 * 캐시 사용 여부를 설정한다 .<br/>
	 * true 인 경우에 캐시를 사용한다.<br/>
	 * @param useCache
	 */
	void setUseCache(boolean useCache);
}