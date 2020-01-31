package com.song7749.srcenter.value;

import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class Name : SrDataRunningInfoCacheVo
 * Description : 진행중인 작업에 대한 상새 내용
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
public class SrDataRunningInfoCacheVo implements Serializable {

    private static final long serialVersionUID = 3616308756144002170L;

    private Long memberId;

    private Long startTime;

    public SrDataRunningInfoCacheVo() {
    }

    public SrDataRunningInfoCacheVo(Long memberId, Long startTime) {
        this.memberId = memberId;
        this.startTime = startTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SrDataRunningInfoCacheVo that = (SrDataRunningInfoCacheVo) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, startTime);
    }
}