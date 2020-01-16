package com.song7749.srcenter.repository;

import com.song7749.srcenter.value.SrDataRunningInfoCacheVo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class Name : SrDataRequestRunningCacheRepository
 * Description : 실행 중인 SR Request 를 저장하고 동시 실행을 방지하고자 저장하는 저장소
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  16/01/2020		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 16/01/2020
 */

@Repository
public class SrDataRequestRunningCacheRepository {

    @Cacheable(cacheNames="com.song7749.srcenter.cache", key="#id")
    public List<SrDataRunningInfoCacheVo> get(Long id){
        return new ArrayList<SrDataRunningInfoCacheVo>();
    }

    @CachePut(cacheNames="com.song7749.srcenter.cache", key="#id")
    public List<SrDataRunningInfoCacheVo> put(Long id, List<SrDataRunningInfoCacheVo> list){
        return list;
    }
}
