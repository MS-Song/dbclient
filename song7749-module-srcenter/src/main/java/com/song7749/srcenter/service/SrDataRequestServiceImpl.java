package com.song7749.srcenter.service;

import com.song7749.common.MessageVo;
import com.song7749.common.Parameter;
import com.song7749.common.YN;
import com.song7749.common.validate.Validate;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.mail.service.EmailService;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberFindDto;
import com.song7749.srcenter.domain.SrDataCondition;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.repository.SrDataRequestRepository;
import com.song7749.srcenter.repository.SrDataRequestRunningCacheRepository;
import com.song7749.srcenter.task.SrDataRequestConfirmRequestTask;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.value.*;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.song7749.util.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.castor.util.Base64Decoder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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
    private LoginSession loginSession;

    @Autowired
    private SrDataRequestRepository srDataRequestRepository;

    @Autowired
    private SrDataRequestRunningCacheRepository srDataRequestRunningCacheRepository;

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DBclientManager dbClientManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment environment;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Validate(nullable = false)
    @Transactional
    @Override
    public SrDataRequestVo add(SrDataRequestAddDto dto) {

        // 파라메터 검증
        conditionValidate(dto);
        // SQL 데이터 디코드
        decodeSQL(dto);

        // 모델 mapper 를 사용한 자동매핑 후에 매핑되지 않는 것들을 처리 한다.
        SrDataRequest sdr = mapper.map(dto, SrDataRequest.class);

        logger.debug("Login Session : {}",loginSession.getLogin());

        // 회원 정보를 획득
        Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
        }
        // 등록 회원 정보 입력
        sdr.setResistMember(oMember.get());

        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다. 요청자를 반드시 넣어 주시기 바랍니다.");
        }
        // 허용 회원 정보 입력
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // database 정보 획득
        Optional<Database> oDatabase = databaseRepository.findById(dto.getDatabaseId());
        if(!oDatabase.isPresent()){
            throw new IllegalArgumentException("Database 정보가 일치하지 않습니다.");
        }
        // Database 정보 입력
        sdr.setDatabase(oDatabase.get());

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

        // 수정 가능 상태 정의 -- Admin 회원 인가?
        boolean isEditable  = AuthType.ADMIN.equals(loginSession.getLogin().getAuthType());
        // 작성자 본인인가?
        isEditable = isEditable || sdr.getResistMember().getId().equals(loginSession.getLogin().getId());

        // 수정 가능 상태 확인
        if(isEditable==false){
            throw new IllegalArgumentException("작성자 본인만 수정 가능 합니다.");
        }

        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다. 요청자를 반드시 넣어 주시기 바랍니다.");
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

        // 수정 로그를 기록 한다.

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
            throw new IllegalArgumentException("허용된 대상자가 없습니다. 요청자를 반드시 넣어 주시기 바랍니다.");
        }
        // 회원 정보 추가
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // 로그 기록

        return srDataRequestRepository
                .saveAndFlush(sdr)
                .getSrDataRequestVo(mapper);
    }

    @Transactional
    @Override
    public SrDataRequestVo modify(Long srDataRequestId, Long resistMemberId){
        // 회원 조회
        Optional<Member> oMember = memberRepository.findById(resistMemberId);
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
        }

        // SR 조회
        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(srDataRequestId);
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }

        // 로그 기록을 위해 기존 정보를 저장
        SrDataRequest sr = oSrDataRequest.get();
        Member beforeMember = sr.getResistMember();
        Member afterMember  = oMember.get();

        // 업데이트
        sr.setResistMember(afterMember);
        srDataRequestRepository.saveAndFlush(sr);

        // 로그 기록
        return sr.getSrDataRequestVo(mapper);
    };

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
        Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
        }
        sdr.setConfirmMember(oMember.get());

        // 승인일 지정
        sdr.setConfirmDate(new Date());

        // 로그 기록

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
        // 로그 기록

        srDataRequestRepository.delete(optionalSrDataRequest.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SrDataRequestVo> find(SrDataRequestFindDto dto, Pageable page) {
        // user 세션 등록
        dto.setRunMemberId(loginSession.getLogin().getId());

        // 일반 사용자의 경우 사용 가능 Y, 승인 Y 값만 조회 가능 하다.
        if(AuthType.NORMAL.equals(loginSession.getLogin().getAuthType())){
            dto.setEnableYN(YN.Y);
            dto.setConfirmYN(YN.Y);

            // 일반 사용자는 허용 사용자를 고정 한다.
            dto.setSrDataAllowMemberIds(Arrays.asList(dto.getRunMemberId()));
        }

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
        // user 세션 등록
        dto.setRunMemberId(loginSession.getLogin().getId());

        // 일반 사용자의 경우 사용 가능 Y, 승인 Y 값만 조회 가능 하다.
        if(AuthType.NORMAL.equals(loginSession.getLogin().getAuthType())){
            dto.setEnableYN(YN.Y);
            dto.setConfirmYN(YN.Y);
            // 일반 사용자는 허용 사용자를 고정 한다.
            dto.setSrDataAllowMemberIds(Arrays.asList(dto.getRunMemberId()));
        }

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
                    Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
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

        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }
        // 객체 타입 변경
        SrDataRequest srDataRequest = oSrDataRequest.get();

        // 일반 사용자의 경우 사용 가능 Y, 승인 Y 값만 조회 가능 하다.
        if(AuthType.NORMAL.equals(loginSession.getLogin().getAuthType())){
            logger.debug("Normal User Run Status  - enableYN : {} , confirmYN : {}",srDataRequest.getEnableYN(),srDataRequest.getConfirmYN());
            // 2개의 조건이 모두 Y 가 아닌 경우..
            if(!
                    (YN.Y.equals(srDataRequest.getEnableYN())
                    && YN.Y.equals(srDataRequest.getConfirmYN()))){
                throw new IllegalArgumentException("일치하는 정보가 없습니다.");
            }
        }

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        // 실행 가능한 기간 인가?
        if(null!=srDataRequest.getDownloadStartDate()){
            // 시작 시간 비교
            String start = sdf.format(srDataRequest.getDownloadStartDate());
            try {
                Date startDate = sdf.parse(start);
                logger.debug("download start date : {}, now date : {}",startDate,now);
                if(now.before(startDate)){
                    throw new IllegalArgumentException("다운로드 시작일 전입니다. 다운로드 가능일 변경을 원하시면 개발자에게 문의 하시기 바랍니다. 시작일 : " + start);
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException("다운로드 시작일에 문제가 있습니다. 관리자에게 문의 하시기 바랍니다");
            }
        }
        if(null!=srDataRequest.getDownloadEndDate()){
            // 종료 시간 비교
            String end = sdf.format(srDataRequest.getDownloadEndDate());
            try {
                Date endDate = sdf.parse(end);
                logger.debug("download end date : {}, now date : {}",endDate,now);
                if(now.before(endDate)){
                    throw new IllegalArgumentException("다운로드 가능일이 지났습니다. 다운로드 가능일 변경을 원하시면 개발자에게 문의 하시기 바랍니다. 종료일 : " + end);
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException("다운로드 시작일에 문제가 있습니다. 관리자에게 문의 하시기 바랍니다");
            }
        }

        // 다운로드 횟수 제어 기능 -- 엑셀이 아닌 경우 100개가 한계이기에... 엑셀 인 경우에만 제어 할 것인가..?
        if(dto.isExcel()) {

            // 회원 정보를 넣는다.
            dto.setRunMemberId(loginSession.getLogin().getId());
            // 선행 작업이 있는지 학인 한다.
            List<SrDataRunningInfoCacheVo> list = srDataRequestRunningCacheRepository.get(dto.getId());

            // 실행 가능 횟수를 확인 한다.
            logger.debug("Running SR Request Count : {}, download limit : {} ",list.size(),srDataRequest.getDownloadLimit());
            int count = 0;
            for (SrDataRunningInfoCacheVo infoVo : list) {
                // 진행 중인 작업이 정의 되어 있는 최근 시간 이라면 카운트를 증가 시킨다.
                logger.debug("SR Running Request 대상 확인 - 실행시간 : {}, 대상시간 : {}",infoVo.getStartTime(),System.currentTimeMillis() - srDataRequest.getDownloadLimitType().getTime());
                logger.debug("SR Running Request 대상 확인 - 대상 여부 : {}",infoVo.getStartTime() < System.currentTimeMillis() - srDataRequest.getDownloadLimitType().getTime());
                // 실행 중인  작업들이 현재 시간에서 제한 시간을 뺀 것보다 크면.
                if (infoVo.getStartTime() > System.currentTimeMillis() - srDataRequest.getDownloadLimitType().getTime()) {
                    count++;
                }
            }

            logger.debug("Running SR Request Count by time : {}",count);

            // 정의 되어 있는 횟수 이상인가 확인 -- 0 인 경우에는 무제한
            if (srDataRequest.getDownloadLimit() !=0 && count >= srDataRequest.getDownloadLimit()) {
                throw new IllegalArgumentException("이미 진행 중인 작업이 " + count + "개 있습니다. 잠시 후 다시 이용하시기 바랍니다. ("+srDataRequest.getDownloadLimitType().getDesc() + ")");
            }
            // 작업 추가
            List<SrDataRunningInfoCacheVo> cacheList = new ArrayList<>();
            cacheList.addAll(list);
            cacheList.add(new SrDataRunningInfoCacheVo(dto.getRunMemberId(),System.currentTimeMillis()));
            srDataRequestRunningCacheRepository.put(dto.getId(),cacheList);
        }

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
        Optional<Member> oMember = memberRepository.findById(loginSession.getLogin().getId());
        if(!oMember.isPresent()){
            throw new IllegalArgumentException("실행 회원의 정보를 찾을 수 없습니다.");
        }

        // 최종 결과를 실행 한다.
        ExecuteQueryDto excuteDto = new ExecuteQueryDto(srDataRequest.getDatabase().getId(), oMember.get().getLoginId());
        // DTO 설정
        excuteDto.setQuery(sql);
        // 한정자가 존재 하는 경우 -- 페이지에서 볼때
        excuteDto.setUseLimit(dto.isUseLimit());
        if(dto.isUseLimit()){
            excuteDto.setLimit(dto.getLimit());
            excuteDto.setOffset(dto.getOffset());
        }

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
            vo.setMessage(excuteDto.getQuery());
        }


        // 실행이 완료되면 작업 하나를 삭제 처리 한다.
        if(dto.isExcel()) {
            List<SrDataRunningInfoCacheVo> list = srDataRequestRunningCacheRepository.get(dto.getId());
            logger.debug("SR Request Running count : {} ",list.size());

            // 삭제 대상을 선별 한다.
            List<SrDataRunningInfoCacheVo> removeList = new ArrayList<>();
            for (SrDataRunningInfoCacheVo infoVo : list) {
                // 이미 유효 시간이 지난 데이터들을 제거 한다.
                logger.debug("SR Running Request Remove 대상 : {}", System.currentTimeMillis() - infoVo.getStartTime() > srDataRequest.getDownloadLimitType().getTime());
                // 현재 시간에서 실행 시간을 빼고, 범위 보다 크면, 삭제 대상 이다.
                if (System.currentTimeMillis() - infoVo.getStartTime() > srDataRequest.getDownloadLimitType().getTime()) {
                    removeList.add(infoVo);
                }
            }
            // 유효 시간이 지난 데이터 삭제 및 방금 실행이 완료된 데이터를 삭제 한다.
            if(list.size()>0){
                list.removeAll(removeList);
                srDataRequestRunningCacheRepository.put(dto.getId(),list);
            }
        }
        return vo;
    }

    @Transactional(readOnly = true)
    @Override
    public void confirmRequest(SrDataRequestConfirmDto dto){
        // 해당 SR 을 조회 한다.
        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }

        // 관리자 회원에게 메일 발송 하기 위해 조회
        MemberFindDto memberFindDto = new MemberFindDto();
        memberFindDto.setAuthType(AuthType.ADMIN);
        Pageable page = PageRequest.of(0, 100);//,Direction.DESC,"id");
        Page<Member> pMember = memberRepository.findAll(memberFindDto,page);

        // 메일을 발송 한다.
        taskScheduler.getScheduledExecutor().execute(
            new SrDataRequestConfirmRequestTask(
                oSrDataRequest.get(),
                pMember.getContent(),
                emailService,
                environment,
               dto.getConfirmYN()
            )
        );
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