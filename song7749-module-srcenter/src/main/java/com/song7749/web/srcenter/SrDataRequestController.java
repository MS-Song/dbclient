package com.song7749.web.srcenter;

import com.song7749.common.MessageVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.srcenter.service.SrDataReqeustService;
import com.song7749.srcenter.value.*;
import com.song7749.util.LogMessageFormatter;
import com.song7749.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.castor.util.Base64Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

import static com.song7749.util.LogMessageFormatter.format;

/**
 * <pre>
 * Class Name : SrDataRequestController
 * Description : SrDataRequest 처리 컨트롤러
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  21/11/2019		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 21/11/2019
 */

@Api(tags="SR Data Request 관리 및 실행 기능")
@RestController
@RequestMapping("/srDataRequest")
public class SrDataRequestController {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoginSession session;

    @Autowired
    SrDataReqeustService srDataReqeustService;

    @ApiOperation(value = "Sr Data 등록"
            ,notes = "Sr Data Request 를 등록 한다. "
            ,response=MessageVo.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @PostMapping("/add")
    public MessageVo add(HttpServletRequest request,
                         HttpServletResponse response,
                         @Valid @ModelAttribute SrDataRequestAddDto dto) throws UnsupportedEncodingException {

        // 인증 정보 추가
        dto.setMemberId(session.getLogin().getId());

        // SQL 데이터 가공
        dto.setRunSql(
            URLDecoder.decode(
                new String(
                    Base64Decoder.decode(dto.getRunSql())
                        , Charset.forName("UTF-8"))
                    , "UTF-8")
        );
        logger.debug(format("{}", "DECODE URL RUN SQL"),dto.getRunSql());

        // where 절 가공
        if(!CollectionUtils.isEmpty(dto.getConditionWhereSql())){
            // 새로운 배열에 담는다.
            List<String> unwrapList = new ArrayList<String>();
            // 인코딩된 정보를 디코드 한다.
            for(String s : dto.getConditionWhereSql()){
                // 데이터가 없을 경우 예외 처리
                if(StringUtils.isBlank(s)){
                    throw new IllegalArgumentException("Where 구문이 없습니다. Where 조건을 입력하시기 바랍니다. ");
                }
                unwrapList.add(
                    URLDecoder.decode(
                        new String(
                            Base64Decoder.decode(s)
                            , Charset.forName("UTF-8"))
                        , "UTF-8")
                );
            }
            dto.setConditionWhereSql(unwrapList);
        }

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.add(dto), "SR Data Request 등록이 완료되었습니다.");
    }

    @ApiOperation(value = "Sr Data 승인 전 수정"
            ,notes = "Sr Data Request 승인 전에 수정하는 기능 "
            ,response=SrDataRequestModifyBeforeConfirmDto.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @PutMapping("/modifyBeforeConfirm")
    public MessageVo modifyBeforeConfirm(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @Valid @ModelAttribute SrDataRequestModifyBeforeConfirmDto dto) throws UnsupportedEncodingException {

        // 인증 정보 추가
        dto.setMemberId(session.getLogin().getId());

        // SQL 데이터 가공
        dto.setRunSql(
                URLDecoder.decode(
                        new String(
                                Base64Decoder.decode(dto.getRunSql())
                                , Charset.forName("UTF-8"))
                        , "UTF-8")
        );
        logger.debug(format("{}", "DECODE URL RUN SQL"),dto.getRunSql());

        // where 절 가공
        if(!CollectionUtils.isEmpty(dto.getConditionWhereSql())){
            // 새로운 배열에 담는다.
            List<String> unwrapList = new ArrayList<String>();
            // 인코딩된 정보를 디코드 한다.
            for(String s : dto.getConditionWhereSql()){
                unwrapList.add(
                        URLDecoder.decode(
                                new String(
                                        Base64Decoder.decode(s)
                                        , Charset.forName("UTF-8"))
                                , "UTF-8")
                );
            }
            dto.setConditionWhereSql(unwrapList);
        }

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.modify(dto), "SR Data Request 수정이 완료되었습니다.");
    }

    @ApiOperation(value = "Sr Data 승인"
            ,notes = "Sr Data Request 승인 기능 "
            ,response=MessageVo.class)
    @Login({AuthType.ADMIN})
    @PutMapping("/confirm")
    public MessageVo confirm(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @Valid @ModelAttribute SrDataRequestConfirmDto dto) throws UnsupportedEncodingException {

        // 인증 정보 추가
        dto.setConfirmMemberId(session.getLogin().getId());
        srDataReqeustService.confirm(dto);
        return new MessageVo(HttpStatus.OK.value(),"SR data Request 승인이 완료 되었습니다.");
    }


    @ApiOperation(value = "Sr Data 승인 후 수정"
            ,notes = "Sr Data Request 승인 후에 수정하는 기능 "
            ,response=MessageVo.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @PutMapping("/modifyAfterConfirm")
    public MessageVo modifyAfterConfirm(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @Valid @ModelAttribute SrDataRequestModifyAfterConfirmDto dto) throws UnsupportedEncodingException {

        // 인증 정보 추가
        dto.setMemberId(session.getLogin().getId());

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.modify(dto), "SR Data Request 수정이 완료되었습니다.");
    }

    @ApiOperation(value = "Sr Data 삭제"
            ,notes = "Sr Data Request 승인 삭제 "
            ,response=MessageVo.class)
    @Login({AuthType.ADMIN})
    @DeleteMapping("/remove")
    public MessageVo remove(HttpServletRequest request,
                            HttpServletResponse response,
                            @Valid @ModelAttribute SrDataRequestRemoveDto dto) throws UnsupportedEncodingException {

        // 인증 정보 추가
        dto.setRemoveMemberId(session.getLogin().getId());
        srDataReqeustService.remove(dto);
        return new MessageVo(HttpStatus.OK.value(),"SR data Request 삭제가 완료 되었습니다.");
    }

    @ApiOperation(value = "SR Data Request List 조회"
            ,notes = "등록된 SR Data Request 리스트를 조회 한다."
            ,response=SrDataRequestVo.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @GetMapping("/list")
    public Page<SrDataRequestVo> list(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestFindDto dto,
            @PageableDefault(page=0, size=20, direction= Sort.Direction.DESC, sort="id") Pageable page){

        return srDataReqeustService.find(dto,page);
    }

    @ApiOperation(value = "SR Data Request 단일 조회"
            ,notes = "등록된 SR Data Request 단일 항목을 조회 한다."
            ,response=SrDataRequestVo.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @GetMapping("/one")
    public MessageVo one(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestFindDto dto){
        return new MessageVo(HttpStatus.OK.value(),srDataReqeustService.find(dto));
    }

    @ApiOperation(value = "SR Data Request 실행"
            ,notes = "SR Data Request 를 실행 한다."
            ,response=MessageVo.class)
    @Login({AuthType.NORMAL,AuthType.ADMIN})
    @GetMapping("/runNow")
    public MessageVo run(HttpServletRequest request,
                         HttpServletResponse response,
                         @Valid @ModelAttribute SrDataRequestRunDto dto){

        // 세션 정보 입력
        dto.setRunMemberId(session.getLogin().getId());
        return srDataReqeustService.runSql(dto,request);
    }
}