package com.song7749.base;

/**
 * <pre>
 * Class Name : Cacheable.java
 * Description : 캐시를 사용하는 객체는 Cacheable 을 구현해야 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 15.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 15.
*/

public interface Cacheable {

	boolean isUseCache();

	void setUseCache(boolean useCache);
}