package com.song7749.srcenter.service;

import com.song7749.common.MessageVo;
import com.song7749.common.Parameter;
import com.song7749.common.YN;
import com.song7749.common.validate.Validate;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.srcenter.domain.SrDataCondition;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.repository.SrDataRequestRepository;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.value.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

import com.song7749.util.StringUtils;
import oracle.net.aso.e;
import org.apache.commons.lang3.ArrayUtils;
import org.castor.util.Base64Decoder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * <pre>
 * Class Name : SrDataRequestServiceImpl
 * Description : 서비스 구현체
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

@Service
public class SrDataRequestServiceImpl implements SrDataReqeustService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ModelMapper mapper;

    @Autowired
    private SrDataRequestRepository srDataRequestRepository;

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DBclientManager dbClientManager;

    @Validate(nullable = false)
    @Transactional
    @Override
    public SrDataRequestVo add(SrDataRequestAddDto dto) {

        // request 데이터 선 가공

        // 파라메터 검증
        conditionValidate(dto);
        // SQL 데이터 디코드
        decodeSQL(dto);

        // 회원 정보를 획득
        Optional<Member> oMember = memberRepository.findById(dto.getMemberId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
        }
        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다.");
        }

        // database 정보 획득
        Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
        if(!oDatabase.isPresent()){
            throw new IllegalArgumentException("Database 정보가 일치하지 않습니다.");
        }

        // 모델 mapper 를 사용한 자동매핑 후에 매핑되지 않는 것들을 처리 한다.
        SrDataRequest sdr = mapper.map(dto, SrDataRequest.class);

        // 조회된 객체 삽입
        sdr.setDatabase(oDatabase.get());
        sdr.setResistMember(oMember.get());
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // SQL condition 을 만든다.
        List<SrDataCondition> conditions = new ArrayList<SrDataCondition>();
        for(int i=0;i<dto.getConditionKey().size();i++){
            String key = dto.getConditionKey().get(i);
            String whereSql = dto.getConditionWhereSql().get(i);
            String whereSqlKey = dto.getConditionWhereSqlKey().get(i);
            String name = dto.getConditionName().get(i);
            String value = dto.getConditionValue().get(i);
            DataType type = dto.getConditionType().get(i);
            YN required = dto.getConditionRequired().get(i);
            conditions.add(new SrDataCondition(whereSql, whereSqlKey, name, key, type, value, required, sdr));
        }
        // condition 저장
        sdr.setSrDataConditions(conditions);

        // 등록 디폴트 셋팅
        sdr.setEnableYN(YN.Y);
        sdr.setConfirmYN(YN.N);
        sdr.setDownloadCount(0);

        return srDataRequestRepository
                .saveAndFlush(sdr)
                .getSrDataRequestVo(mapper);
    }

    @Transactional
    @Override
    public SrDataRequestVo modify(SrDataRequestModifyBeforeConfirmDto dto) {
        // 파라메터 검증
        conditionValidate(dto);
        // SQL 데이터 디코드
        decodeSQL(dto);

        // 기 입력된 데이터를 로딩
        Optional<SrDataRequest> optionalSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!optionalSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }


        // 객체 값 복사
        SrDataRequest sdr = optionalSrDataRequest.get();
        mapper.map(dto, sdr);

        // 작성자 본인이 아니면 수정을 차단 한다.
//        if(!sdr.getResistMember().getId().equals(dto.getMemberId())){
//            throw new IllegalArgumentException("작성자 본인만 수정 가능 합니다.");
//        }

        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다.");
        }
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // database 정보 획득
        Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
        if(!oDatabase.isPresent()){
            throw new IllegalArgumentException("Database 정보가 일치하지 않습니다.");
        }
        sdr.setDatabase(oDatabase.get());

        // 기존 입력 condition 을 모두 제거 한다.
        sdr.getSrDataConditions().clear();

        // SQL condition 을 만든다.
        List<SrDataCondition> conditions = new ArrayList<SrDataCondition>();
        for(int i=0;i<dto.getConditionKey().size();i++){
            String key = dto.getConditionKey().get(i);
            String whereSql = dto.getConditionWhereSql().get(i);
            String whereSqlKey = dto.getConditionWhereSqlKey().get(i);
            String value = dto.getConditionValue().get(i);
            String name = dto.getConditionName().get(i);
            DataType type = dto.getConditionType().get(i);
            YN required = dto.getConditionRequired().get(i);
            conditions.add(new SrDataCondition(whereSql, whereSqlKey, name, key, type, value, required, sdr));
        }
        // condition 저장 - set 을 할 경우 영속성 오류가 발생하니, addAll 을 통해 객체 참조를 유지 한다.
        sdr.getSrDataConditions().addAll(conditions);

        // 기본값 설정
        sdr.setConfirmYN(YN.N);

        return srDataRequestRepository
                .saveAndFlush(sdr)
                .getSrDataRequestVo(mapper);
    }

    @Transactional
    @Override
    public SrDataRequestVo modify(SrDataRequestModifyAfterConfirmDto dto) {
        // 기 입력된 데이터를 로딩
        Optional<SrDataRequest> optionalSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!optionalSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }
        // 객체 값 복사
        SrDataRequest sdr = optionalSrDataRequest.get();
        mapper.map(dto, sdr);

        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다.");
        }
        // 회원 정보 추가
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        return srDataRequestRepository
                .saveAndFlush(sdr)
                .getSrDataRequestVo(mapper);
    }

    @Transactional
    @Override
    public void confirm(SrDataRequestConfirmDto dto) {
        // 기 입력된 데이터를 로딩
        Optional<SrDataRequest> optionalSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!optionalSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }

        // 객체 값 복사
        SrDataRequest sdr = optionalSrDataRequest.get();
        mapper.map(dto, sdr);

        // 승인자 설정
        Optional<Member> oMember = memberRepository.findById(dto.getConfirmMemberId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
        }
        sdr.setConfirmMember(oMember.get());

        // 승인일 지정
        sdr.setConfirmDate(new Date());

        // 업데이트
        srDataRequestRepository.saveAndFlush(sdr);
    }

    @Transactional
    @Override
    public void remove(SrDataRequestRemoveDto dto) {
        // 기 입력된 데이터를 로딩
        Optional<SrDataRequest> optionalSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!optionalSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }

        // 작성자 본인만 삭제 가능 하도록 처리
        if(!optionalSrDataRequest.get().getResistMember().getId().equals(dto.getRemoveMemberId())){
            logger.error("try create id : {} , delete id : {}", optionalSrDataRequest.get().getResistMember().getId(), dto.getId());
            throw new IllegalArgumentException("작성자 본인만 삭제 할 수 있습니다.");
        }

        srDataRequestRepository.delete(optionalSrDataRequest.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SrDataRequestVo> find(SrDataRequestFindDto dto, Pageable page) {
        Page<SrDataRequest> pages = srDataRequestRepository.findAll(dto, page);
        return pages.map(new Function<SrDataRequest, SrDataRequestVo>() {
            @Override
            public SrDataRequestVo apply(SrDataRequest srDataRequest) {
                return srDataRequest.getSrDataRequestVo(mapper);
            }
        });
    }

    @Transactional(readOnly = true)
    @Override
    public SrDataRequestVo find(SrDataRequestFindDto dto) {
        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }
        return oSrDataRequest.get().getSrDataRequestVo(mapper);
    }

    @Transactional(readOnly = true)
    @Override
    public SrDataRequestVo searchFromCreate(SrDataRequestRunDto dto, HttpServletRequest request) {
        SrDataRequestVo srDataRequestVo = find(new SrDataRequestFindDto(dto.getId()));
        List<SrDataConditionVo> conditionVos = srDataRequestVo.getSrDataConditionVos();

        for(SrDataConditionVo vo : conditionVos){
            // 배열 타입의 경우 데이터를 정리해서 넣는다.
            if(vo.getType().equals(DataType.ARRAY)){
                // 값이 있어야하고, 파싱이 가능한 상태인 경우에만 입력해야 한다.
                if(StringUtils.isNotBlank(vo.getValue())){
                    // 입력 값을 | 로 분리 한다.
                    String[] values = vo.getValue().split("|");
                    // 값이 있는 경우
                    if(ArrayUtils.isNotEmpty(values)){
                        // 파라메터를 생성 한다.
                        List<Parameter> parameters = new ArrayList<>();
                        for(String value : values){
                            // 값이 있는 경우
                            if(StringUtils.isNotBlank(value)){
                                // ^ 로 분리 한다.
                                String[] param = value.split("^");
                                if(ArrayUtils.isNotEmpty(param)  && param.length > 1){
                                    parameters.add(new Parameter(param[0],param[1]));
                                }
                            }
                        }
                        // 파라메터 셋팅
                        vo.setValues(parameters);
                    }
                }
            } // 배열 타입 종료
            // SQL 타입인 경우
            else if(vo.getType().equals(DataType.SQL)){
                // 값이 존재 하는 경우에...
                if(StringUtils.isNotBlank(vo.getValue())){

                    // 실행 회원의 정보 조회
                    Optional<Member> oMember = memberRepository.findById(dto.getRunMemberId());
                    if(!oMember.isPresent()){
                        throw new IllegalArgumentException("실행 회원의 정보를 찾을 수 없습니다.");
                    }

                    // 최종 결과를 실행 한다.
                    ExecuteQueryDto excuteDto = new ExecuteQueryDto(srDataRequestVo.getDatabaseVo().getId(), oMember.get().getLoginId());
                    // DTO 설정
                    excuteDto.setQuery(vo.getValue());
                    // 기본값 설정
                    excuteDto.setUseLimit(false);
                    excuteDto.setHtmlAllow(false);
                    excuteDto.setAutoCommit(false);
                    excuteDto.setUseCache(false);
                    try{
                        excuteDto.setIp(request.getRemoteAddr());
                    } catch (Exception e){
                        throw new IllegalArgumentException("접속자 IP 획득에 실패 했습니다.");
                    }
                    try{
                        MessageVo message = null;
                        try{
                            message = dbClientManager.executeQuery(excuteDto);
                        } catch(Exception e){
                            throw new IllegalArgumentException(e.getMessage());
                        }

                        logger.debug("데이터 조회 결과  : {}",message);
                        // 데이터가 있는 경우에만..
                        if(null!=message.getContents()
                                && message.getContents() instanceof List){

                            List<Map<String,String>> contents = (List<Map<String,String>>)message.getContents();

                            List<Parameter> parameters = new ArrayList<>();
                            for(Map<String,String> data : contents){
                                parameters.add(new Parameter(data.get("KEY"), data.get("VALUE")));
                            }
                            vo.setValues(parameters);
                        }
                    } catch(Exception e){
                        throw new IllegalArgumentException("Parameter Type SQL 인 경우 Select 구문을 KEY, VALUE 로 입력해야 합니다.(대문자) \n  또는 다음 메세지를 참조하세요 : "+e.getMessage());
                    }
                }
            }
        }

        return srDataRequestVo;
    }

    @Transactional(readOnly = true)
    @Override
    public MessageVo runSql(SrDataRequestRunDto dto, HttpServletRequest request) {
        // 대상 요청을 조회 한다.
        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }
        SrDataRequest srDataRequest = oSrDataRequest.get();

        // SQL 을 가져와서, Conditions loop 를 실행하며, 완성된 SQL 을 생성한다.
        String sql = srDataRequest.getRunSql();

        // 쿼리 문자열을 조건으로 변경 한다.
        for(SrDataCondition sdc : srDataRequest.getSrDataConditions()){
            // whereKey 를 치환 한다.
            String whereSqlKey = sdc.getWhereSqlKey().replace("{", "").replace("}", "");
            // 값에 해당하는 키를 찾는다.
            String key = sdc.getKey().replace("{", "").replace("}", "");
            // 서블릿에서 해당 값이 있는지 찾는다.
            String value = request.getParameter(key);

            // 필수값 이고, Value 가 없을 경우에는 Exception 발생.
            if(sdc.getRequired().equals(YN.Y) && StringUtils.isBlank(value)){
                throw new IllegalArgumentException(sdc.getName() + " 조건이 입력되지 않았습니다. 조건을 입력해 주세요");
            }

            // value 가 입력된 경우에만 where 를 추가 한다.
            if(StringUtils.isNotBlank(value)){
                // where 를 해당 위치로 치환 한다.
                sql = StringUtils.replacePatten("\\{"+ whereSqlKey.toLowerCase()+"\\}",sdc.getWhereSql().toLowerCase(), sql.toLowerCase());
                // value 를 치환한다.
                sql = StringUtils.replacePatten("\\{"+ key.toLowerCase()+"\\}",value, sql.toLowerCase());
            } else { // value 가 추가되지 않을 경우에는 whereKey 를 제거 한다.
                sql = StringUtils.replacePatten("\\{"+ whereSqlKey.toLowerCase()+"\\}","", sql.toLowerCase());
            }
        }

        // 실행 회원의 정보 조회
        Optional<Member> oMember = memberRepository.findById(dto.getRunMemberId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("실행 회원의 정보를 찾을 수 없습니다.");
        }

        // 최종 결과를 실행 한다.
        ExecuteQueryDto excuteDto = new ExecuteQueryDto(srDataRequest.getDatabase().getId(), oMember.get().getLoginId());
        // DTO 설정
        excuteDto.setQuery(sql);
        // 한정자가 존재 하는 경우 -- 페이지에서 볼때
        if(dto.isUseLimit()){
            excuteDto.setLimit(dto.getLimit());
            excuteDto.setOffset(dto.getOffset());
        }
        excuteDto.setUseLimit(dto.isUseLimit());

        // 기본값 설정
        excuteDto.setHtmlAllow(false);
        excuteDto.setAutoCommit(false);
        excuteDto.setUseCache(false);
        try{
            excuteDto.setIp(request.getRemoteAddr());
        } catch (Exception e){
            throw new IllegalArgumentException("접속자 IP 획득에 실패 했습니다.");
        }

        MessageVo vo = dbClientManager.executeQuery(excuteDto);
        if(dto.isDebug()){
            vo.setMessage(sql);
        }
        return vo;
    }

    private void conditionValidate(Object dto){
        // 파라메터에 대한 추가 검증
        int beforeLength = 0, loop=0;
        // 필드를 가져온다.
        for(Field f :  dto.getClass().getDeclaredFields()){
            // condition 으로 시작하는 필드만 대상이다.
            String message = "SQL Condition 데이터를 가져올 수 없습니다. null 또는 파라메터 개수가 맞지 않습니다. " + f.getName();
            if(f.getName().startsWith("condition")){
                try {
                    // private 멤버를 잠시 읽기 가능하도록 변경
                    f.setAccessible(true);

                    // value 는 선택 값인데, 1개인 경우 null 로 취급 됨으로, 예외 처리를 진행 한다.
                    if(beforeLength==1
                            && f.getName().startsWith("conditionValue")){
                        if(CollectionUtils.isEmpty((List)f.get(dto))){
                            ((List)f.get(dto)).add(new String());
                        };
                    }

                    boolean ckeckLoop = loop!=0;
                    boolean checkSize = f.get(dto) instanceof List;
                    checkSize = checkSize && beforeLength != ((List)f.get(dto)).size();

                    logger.debug("check size  before : {}, check size after : {}", beforeLength, ((List)f.get(dto)).size());

                    if(ckeckLoop && checkSize){
                        throw new IllegalArgumentException(message);
                    }
                    // 이전 길이 저장
                    beforeLength=((List)f.get(dto)).size();

                } catch (IllegalAccessException e) {
                    logger.error(message);
                    throw new IllegalArgumentException(message);
                }
                loop++;
            }
        }
    }

    /**
     * SQL 의 경우 방화벽 이슈로 인해 front 에서 encode 하여 전달 함으로, decode 하여 처리 한다.
     * @param dto
     */
    private void decodeSQL(Object dto) {
        for(Field f :  dto.getClass().getDeclaredFields()){
            // 필드를 읽기 가능 상태로 변경 한다.
            f.setAccessible(true);
            // 실행 쿼리에 대한 decode
            if(f.getName().equals("runSql")){
                try {
                    // dto 내의 데이터를 획득 한다.
                    String encode = (String) f.get(dto);
                    String decode = URLDecoder.decode(
                            new String(
                                    Base64Decoder.decode(encode)
                                    , Charset.forName("UTF-8"))
                            , "UTF-8");
                    f.set(dto, decode);

                    logger.debug("encode 대상 데이터 : {}, 변환 완료 데이터 : {}", encode,decode);
                } catch(Exception e){
                    throw new IllegalArgumentException(f.getName()+" 데이터가 입력되지 않았습니다. ");
                }
            }
            // condition 인 경우
            else if(f.getName().equals("conditionWhereSql")
                    || f.getName().equals("conditionValue")){

                List<String> conditions  = null;
                try{
                    conditions  = (List)f.get(dto);
                } catch(Exception e){
                    throw new IllegalArgumentException(f.getName()+" 데이터 변환에 문제가 발생 했습니다.");
                }

                logger.debug("{} 의 encode 대상 데이터 : {}", f.getName(), conditions);

                List<String> decodes = new ArrayList<String>();
                for(String encode : conditions){
                    // dto 내의 데이터를 획득 한다.
                    try {
                        logger.debug("{} 의 데이터 : {}", f.getName(),encode);
                        String decode = null;
                        if(StringUtils.isNotEmpty(encode)){
                            decode = URLDecoder.decode(
                                new String(
                                    Base64Decoder.decode(encode)
                                    , Charset.forName("UTF-8"))
                                , "UTF-8");
                        }
                        decodes.add(decode);
                    } catch(Exception e){
                        throw new IllegalArgumentException(f.getName()+" 데이터가 입력되지 않았습니다. ");
                    }
                }

                try {
                    f.set(dto, decodes);
                } catch(Exception e){
                    throw new IllegalArgumentException(f.getName()+" 데이터를 입력에 실패했습니다.");
                }
            }
        }
    }
}