package com.song7749.srcenter.service;

import com.song7749.common.MessageVo;
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

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.*;

import com.song7749.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    @Override
    @Validate(nullable = false)
    public SrDataRequestVo add(SrDataRequestAddDto dto) {

        // 파라메터 검증
        conditionValidate(dto);

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
            String value = (!CollectionUtils.isEmpty(dto.getConditionValue())) ? dto.getConditionValue().get(i) : null;
            String name = dto.getConditionName().get(i);
            DataType type = dto.getConditionType().get(i);
            YN required = dto.getConditionRequired().get(i);
            conditions.add(new SrDataCondition(whereSql, name, key, type, value, required, sdr));
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

    @Override
    public SrDataRequestVo modify(SrDataRequestModifyBeforeConfirmDto dto) {
        // 파라메터 검증
        conditionValidate(dto);

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
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // SQL condition 을 만든다.
        List<SrDataCondition> conditions = new ArrayList<SrDataCondition>();
        for(int i=0;i<dto.getConditionKey().size();i++){
            String key = dto.getConditionKey().get(i);
            String whereSql = dto.getConditionWhereSql().get(i);
            String value = (!CollectionUtils.isEmpty(dto.getConditionValue())) ? dto.getConditionValue().get(i) : null;
            String name = dto.getConditionName().get(i);
            DataType type = dto.getConditionType().get(i);
            YN required = dto.getConditionRequired().get(i);
            conditions.add(new SrDataCondition(whereSql, name, key, type, value, required, sdr));
        }
        // condition 저장
        sdr.setSrDataConditions(conditions);

        // 기본값 설정
        sdr.setConfirmYN(YN.N);

        return srDataRequestRepository
                .saveAndFlush(sdr)
                .getSrDataRequestVo(mapper);
    }

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

    @Override
    public SrDataRequestVo find(SrDataRequestFindDto dto) {
        Optional<SrDataRequest> oSrDataRequest = srDataRequestRepository.findById(dto.getId());
        if(!oSrDataRequest.isPresent()){
            throw new IllegalArgumentException("일치하는 정보가 없습니다.");
        }
        return oSrDataRequest.get().getSrDataRequestVo(mapper);
    }

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
            // servlet request 에서 원하는 value 를 찾아 설정 한다.
            String value = request.getParameter(sdc.getKey());

            // 필수값 이고, Value 가 없을 경우에는 Exception 발생.
            if(sdc.getRequired().equals(YN.Y) && StringUtils.isBlank(value)){
                throw new IllegalArgumentException(sdc.getName() + " 조건이 입력되지 않았습니다. 조건을 입력해 주세요");
            }
            // value 가 입력된 경우에만 where 를 추가 한다.
            if(StringUtils.isNotBlank(value)){
                // 현재 SQL 구문에 Where 포함 값을 추가 한다. (key 변경을 해서 넣는다.)
                sql+= " " + sdc.getWhereSql();
                sql = StringUtils.replacePatten("\\{"+ sdc.getKey().toLowerCase()+"\\}",value, sql.toLowerCase());
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

        return dbClientManager.executeQuery(excuteDto);
    }

    private void conditionValidate(Object dto){
        // 파라메터에 대한 추가 검증
        int beforeLength = 0, loop=0;
        // 필드를 가져온다.
        for(Field f :  dto.getClass().getDeclaredFields()){
            // condition 으로 시작하는 필드만 대상이다.
            String message = "SQL Condition 데이터를 가져올 수 없습니다. null 또는 파라메터 개수가 맞지 않습니다. " + f.getName();
            if(f.getName().startsWith("condition") && !f.getName().startsWith("conditionValue")){
                try {
                    // private 멤버를 잠시 읽기 가능하도록 변경
                    f.setAccessible(true);
                    if(null==f.get(dto)){
                        throw new IllegalArgumentException(message);
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
}