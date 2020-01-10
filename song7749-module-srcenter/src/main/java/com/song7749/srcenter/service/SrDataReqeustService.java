package com.song7749.srcenter.service;

import com.song7749.common.MessageVo;
import com.song7749.srcenter.value.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Class Name : SrDataReqeustService
 * Description : SR Data Request 의 서비스 인터페이스
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  13/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 13/11/2019
 */

public interface SrDataReqeustService {

    /**
     * 객체 추가
     * @param dto
     * @return SrDataRequestVo
     */
    SrDataRequestVo add(SrDataRequestAddDto dto);

    /**
     * 승인 전 수정 기능
     * @param dto
     * @return
     */
    SrDataRequestVo modify(SrDataRequestModifyBeforeConfirmDto dto);

    /**
     * 승인 후 수정 기능
     * @param dto
     * @return
     */
    SrDataRequestVo modify(SrDataRequestModifyAfterConfirmDto dto);

    /**
     * 승인 기능
     * @param dto
     */
    void confirm(SrDataRequestConfirmDto dto);

    /**
     * 삭제 기능
     * @param dto
     */
    void remove(SrDataRequestRemoveDto dto);

    /**
     * 리스트 검색 기능
     * @return
     */
    Page<SrDataRequestVo> find(SrDataRequestFindDto dto, Pageable page);

    /**
     * 단일 객체 검색
     * @param dto
     * @return
     */
    SrDataRequestVo find(SrDataRequestFindDto dto);

    /**
     * 실행을 위한 검색 조건 생성
     * @param dto
     * @return
     */
    SrDataRequestVo searchFromCreate(SrDataRequestRunDto dto, HttpServletRequest request);

    /**
     * 저장되어 있는 request 를 실행한다.
     * @param dto
     * @return
     */
    MessageVo runSql(SrDataRequestRunDto dto, HttpServletRequest request);
}