package com.song7749.util;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

public class ProxyUtils {

	/**
	 * 프록시 객체의 원본 클래스를 반환.<br/>
	 * @param bean
	 * @return Object
	 * @throws Exception
	 */
	public static final Object unwrapProxy(Object bean) throws Exception {

		if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {

			Advised advised = (Advised) bean;

			bean = advised.getTargetSource().getTarget();
		}

		return bean;
	}
}
