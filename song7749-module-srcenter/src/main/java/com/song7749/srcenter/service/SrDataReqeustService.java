package com.song7749.srcenter.service;

import javax.servlet.http.HttpServletRequest;

import com.song7749.common.base.MessageVo;
import com.song7749.srcenter.value.SrDataRequestAddDto;
import com.song7749.srcenter.value.SrDataRequestConfirmDto;
import com.song7749.srcenter.value.SrDataRequestFindDto;
import com.song7749.srcenter.value.SrDataRequestModifyAfterConfirmDto;
import com.song7749.srcenter.value.SrDataRequestModifyBeforeConfirmDto;
import com.song7749.srcenter.value.SrDataRequestRemoveDto;
import com.song7749.srcenter.value.SrDataRequestRunDto;
import com.song7749.srcenter.value.SrDataRequestVo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
     * 등록자를 변경 한다.
     * 담당자 본인만 수정 가능하기에 관리자가 담당자를 변경 가능하도록 처리 한다.
     * @param srDataRequestId
     * @param resistMemberId
     * @return
     */
    SrDataRequestVo modify(Long srDataRequestId, Long resistMemberId);

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

    /**
     * Confirm 요청 메일을 발송 한다.
     * @param dto
     */
    void confirmRequest(SrDataRequestConfirmDto dto);

}