package com.song7749.web.srcenter;

import com.song7749.common.MessageVo;
import com.song7749.common.YN;
import com.song7749.incident.value.IncidentAlarmConfirmDto;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.srcenter.service.SrDataReqeustService;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.value.*;
import com.song7749.srcenter.view.ExcelDownloadView;
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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
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
    LoginSession loginSession;

    @Autowired
    SrDataReqeustService srDataReqeustService;

    @ApiOperation(value = "Sr Data 등록"
            ,notes = "Sr Data Request 를 등록 한다. "
            ,response=MessageVo.class)
    @Login({AuthType.DEVELOPER,AuthType.ADMIN})
    @PostMapping("/add")
    public MessageVo add(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestAddDto dto) throws UnsupportedEncodingException {

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.add(dto), "SR Data Request 등록이 완료되었습니다.");
    }

    @ApiOperation(value = "Sr Data 승인 전 수정"
            ,notes = "Sr Data Request 승인 전에 수정하는 기능 "
            ,response=SrDataRequestModifyBeforeConfirmDto.class)
    @Login({AuthType.DEVELOPER,AuthType.ADMIN})
    @PutMapping("/modifyBeforeConfirm")
    public MessageVo modifyBeforeConfirm(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestModifyBeforeConfirmDto dto) throws UnsupportedEncodingException {

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.modify(dto), "SR Data Request 수정이 완료되었습니다.");
    }

    @ApiOperation(value = "Sr Data 승인"
            ,notes = "Sr Data Request 승인 기능 "
            ,response=MessageVo.class)
    @Login({AuthType.ADMIN})
    @PutMapping("/confirm")
    public MessageVo confirm(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestConfirmDto dto) throws UnsupportedEncodingException {

        srDataReqeustService.confirm(dto);
        return new MessageVo(HttpStatus.OK.value(),dto.getConfirmYN().equals(YN.Y) ? "SR data Request 승인이 완료 되었습니다." : "SR data Request 승인이 취소 되었습니다.");
    }


    @ApiOperation(value = "Sr Data 승인 후 수정"
            ,notes = "Sr Data Request 승인 후에 수정하는 기능 "
            ,response=MessageVo.class)
    @Login({AuthType.DEVELOPER,AuthType.ADMIN})
    @PutMapping("/modifyAfterConfirm")
    public MessageVo modifyAfterConfirm(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestModifyAfterConfirmDto dto) throws UnsupportedEncodingException {

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.modify(dto), "SR Data Request 수정이 완료되었습니다.");
    }


    @ApiOperation(value = "Sr Data 등록자 수정"
            ,notes = "Sr Data 등록자를 수정 한다. - 담당자 변경 등으로 다른 사람이 수정할 경우 처리를 위함, 관리자만 가능함"
            ,response=MessageVo.class)
    @Login({AuthType.ADMIN})
    @PutMapping("/modifyResistMember")
    public MessageVo modifyResistMember(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Long srDataRequestId,
            @RequestParam Long resistMemberId){

        return new MessageVo(HttpStatus.OK.value(), 1
                , srDataReqeustService.modify(srDataRequestId,resistMemberId), "등록자 수정이 완료 되었습니다.");
    }

    @ApiOperation(value = "Sr Data 삭제"
            ,notes = "Sr Data Request 승인 삭제 "
            ,response=MessageVo.class)
    @Login({AuthType.ADMIN})
    @DeleteMapping("/remove")
    public MessageVo remove(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestRemoveDto dto) throws UnsupportedEncodingException {

        srDataReqeustService.remove(dto);
        return new MessageVo(HttpStatus.OK.value(),"SR data Request 삭제가 완료 되었습니다.");
    }



    @ApiOperation(value = "SR Data Request List 조회"
            ,notes = "등록된 SR Data Request 리스트를 조회 한다."
            ,response=SrDataRequestVo.class)
    @Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
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
    @Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
    @GetMapping("/one")
    public MessageVo one(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestFindDto dto){

        return new MessageVo(HttpStatus.OK.value(),srDataReqeustService.find(dto));
    }


    @ApiOperation(value = "SR Data Request 실행을 위한 검색 조건 조회"
            ,notes = "등록된 SR Data Request 를 실행을 위한 검색 조건들을 생성하여 전송 "
            ,response=SrDataRequestVo.class)
    @Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
    @GetMapping("/searchFromCreate")
    public MessageVo searchFromCreate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestRunDto dto){

        return new MessageVo(HttpStatus.OK.value(),srDataReqeustService.searchFromCreate(dto,request));
    }


    @ApiOperation(value = "SR Data Request 실행"
            ,notes = "SR Data Request 를 실행 한다."
            ,response=MessageVo.class)
    @Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
    @GetMapping("/runNow")
    public MessageVo run(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestRunDto dto){

        return srDataReqeustService.runSql(dto,request);
    }

    @ApiOperation(value = "SR Data Request 결과를 Excel 다운 로드"
            ,notes = "SR Data Request 를 실행하고, 그 결과를 Excel 다운로드 받는다.")
    @Login({AuthType.NORMAL,AuthType.DEVELOPER,AuthType.ADMIN})
    @GetMapping("/excelDownload")
    public View getExcelDownload(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @Valid @ModelAttribute SrDataRequestRunDto dto) {

        // 엑셀 다운로드 관련 설정을 진행
        dto.setUseLimit(false); // 결과 값 제한 없음
        dto.setExcel(true);     // 엑셀 다운 로드 설정

        MessageVo vo = srDataReqeustService.runSql(dto,request);
        if(CollectionUtils.isEmpty((List)vo.getContents())){
            throw new IllegalArgumentException("결과가 없어 엑셀파일 생성에 실패했습니다. ");
        }

        model.addAttribute("rows",vo.getContents());
        return new ExcelDownloadView();

    }

        @ApiOperation(value = "SR Data Request 승인 요청"
            ,notes = "SR Data Request 승인 요청 한다.<br/> 관리자로 등록된 모든 회원에게 승인 요청 메일이 발송된다."
            ,response=MessageVo.class)
    @Login({AuthType.DEVELOPER,AuthType.ADMIN})
    @PutMapping("/confirmRequest")
    public MessageVo confirmRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @ModelAttribute SrDataRequestConfirmDto dto){
        // 승인 요청 실행
        srDataReqeustService.confirmRequest(dto);
        return new MessageVo(HttpStatus.OK.value(), 1, dto.getConfirmYN().equals(YN.Y) ? "승인이 요청 되었습니다." : "승인 취소 요청 되었습니다.");
    }

}