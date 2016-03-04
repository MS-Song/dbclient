package com.song7749.cache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.TriggersRemoveInterceptor;

public class TriggersRemoveInterceptorImpl implements TriggersRemoveInterceptor {

	Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.TriggersRemoveInterceptor#preInvokeTriggersRemove(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation, java.io.Serializable)
     */
    @Override
	public boolean preInvokeTriggersRemove(Ehcache cache, MethodInvocation methodInvocation, Serializable key) {
        return sendRemoveCache(cache,methodInvocation);
    }

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.TriggersRemoveInterceptor#preInvokeTriggersRemoveAll(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation)
     */
    @Override
	public boolean preInvokeTriggersRemoveAll(Ehcache cache, MethodInvocation methodInvocation) {
    	return sendRemoveCache(cache,methodInvocation);
    }

    private boolean sendRemoveCache(Ehcache cache, MethodInvocation methodInvocation){
    	return true;
    }
}
