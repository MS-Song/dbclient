package com.song7749.util;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * <pre>
 * Class Name : ProxyUtils.java
 * Description : Proxy 객체의 원본 객체를 가져온다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 14.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 14.
*/
public class ProxyUtils {

	/**
	 * 프록시 객체의 원본 객체를 반환.
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static final Object unwrapProxy(Object bean) throws Exception {
		if(AopUtils.isAopProxy(bean) && bean instanceof Advised){
			Advised advised = (Advised) bean;
			bean = advised.getTargetSource().getTarget();
		}
		return bean;
	}
}
