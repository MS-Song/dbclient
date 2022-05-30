package com.song7749.common.base;

/**
 * <pre>
 * Class Name : Cacheable.java
 * Description : Cache 를 사용하기 위해서 DTO는 Cacheable 을 구현 해야 한다.
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