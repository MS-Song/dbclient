package com.song7749.cache;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.CacheableInterceptor;

public class CacheAbleInterceptorImpl implements CacheableInterceptor{

	Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.CacheInterceptor#preInvokeCachable(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation, java.io.Serializable, java.lang.Object)
     */
    @Override
	public boolean preInvokeCachable(Ehcache cache, MethodInvocation methodInvocation, Serializable key, Object value) {
    	/**
    	 * 호출된 메소드의 실행 여부를 결정하는 기능을 한다.
    	 * 캐시가 생성되어 있지 않을 경우에는 인터셉터가 실행되지 않는다. (key 별로 최초 진입시에는 캐시가 없기 때문에 pre 인터섭터를 실행하지 않는다)
    	 * 캐시 생성 여부는 postInvokeCacheable 에서 결정한다.
    	 */

    	/**
    	 * 호출된 메소드를 실행한다 (매니저,서비스,DAO 의 메소드를 지칭함)
    	 */
    	boolean isCacheable=true;

    	/**
    	 * 파라메터 중에 Cachealbe 인터페이스를 구현한 객체가 있는가 검증한다.
    	 */
    	if(null != methodInvocation.getArguments()){
    		for(Object obj : methodInvocation.getArguments()){
    			/**
    			 * 파라메터가 여러개인 경우에 동작 하기 위해서 Cacheable 를 구현한 객체를 찾아낸다.
    			 * 여러번 검색하는 이유는 객체중에 하나라도 캐시를 사용하겠다는 값을 설정하면 캐시에서 데이터가 나가기 위해 메소드 실행을 막기 위함.
    			 */
    			if(obj instanceof Cacheable){
    				if(((Cacheable) obj).isUseCache() == true){
    					isCacheable=false;
    					break;
    				}
    			}
    		}
    	}

		if(logger.isDebugEnabled()){
			if(isCacheable == false){
				logger.debug(format(cache.getName() + " Use Cache Complete!", "Ehcache Message"));
			}
			else{
				logger.debug(format(cache.getName() + " Not Use Cache Complete!", "Ehcache Message"));
			}
		}
		/**
		 * 인터셉터가 호출하려던 메소드를 가로챈다. (Manager, DAO 등의)
		 * false 이면 호출 하려던 메소드 실행을 안하고 캐시에서 데이터를 리턴하고
		 * true 이면 호출 하려던 메소드를 실행하고 데이터를 리턴한다.
		 */
    	return isCacheable;
    }

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.CacheInterceptor#postInvokeCacheable(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation, java.io.Serializable, java.lang.Object)
     */
    @Override
	public boolean postInvokeCacheable(Ehcache cache, MethodInvocation methodInvocation, Serializable key, Object value) {
		/**
		 * 결과값에 대한 캐시여부를 설정한다.
		 * true 결과 값을 캐시한다.
		 * false 결과 값을 캐시하지 않는다.
		 */
        return true;
    }

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.CacheInterceptor#preInvokeCacheableException(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation, java.io.Serializable, java.lang.Throwable)
     */
    @Override
	public boolean preInvokeCacheableException(Ehcache exceptionCache, MethodInvocation methodInvocation, Serializable key, Throwable t) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.googlecode.ehcache.annotations.CacheInterceptor#postInvokeCacheableException(net.sf.ehcache.Ehcache, org.aopalliance.intercept.MethodInvocation, java.io.Serializable, java.lang.Throwable)
     */
    @Override
	public boolean postInvokeCacheableException(Ehcache exceptionCache, MethodInvocation methodInvocation, Serializable key, Throwable t) {
        return true;
    }

}
