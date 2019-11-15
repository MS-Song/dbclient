package com.song7749.srcenter.service;

import com.song7749.common.YN;
import com.song7749.common.validate.Validate;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.srcenter.domain.SrDataCondition;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.repository.SrDataRequestRepository;
import com.song7749.srcenter.type.DataType;
import com.song7749.srcenter.value.*;

import java.lang.reflect.Field;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;

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
            String value = dto.getConditionValue().get(i);
            String name = dto.getConditionName().get(i);
            DataType type = dto.getConditionType().get(i);
            conditions.add(new SrDataCondition(name, key, type, value, sdr));
        }
        // condition 저장
        sdr.setSrDataConditions(conditions);

        // 등록 디폴트 셋팅
        sdr.setEnableYN(YN.Y);
        sdr.setConfirmYN(YN.N);
        sdr.setDownloadCount(0);

        srDataRequestRepository.saveAndFlush(sdr);
        return mapper.map(sdr,SrDataRequestVo.class);
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

        // 허용된 회원 정보 조회
        List<Member> srDataAllowMembers = memberRepository.findAllById(dto.getSrDataAllowMemberIds());
        if(CollectionUtils.isEmpty(srDataAllowMembers)) {
            throw new IllegalArgumentException("허용된 대상자가 없습니다.");
        }

        // 객체 값 복사
        SrDataRequest sdr = optionalSrDataRequest.get();
        mapper.map(dto, sdr);

        logger.info("{}",sdr);

        // 리스트 설정
        sdr.setSrDataAllowMembers(srDataAllowMembers);

        // SQL condition 을 만든다.
        List<SrDataCondition> conditions = new ArrayList<SrDataCondition>();
        for(int i=0;i<dto.getConditionKey().size();i++){
            String key = dto.getConditionKey().get(i);
            String value = dto.getConditionValue().get(i);
            String name = dto.getConditionName().get(i);
            DataType type = dto.getConditionType().get(i);
            conditions.add(new SrDataCondition(name, key, type, value, sdr));
        }
        // condition 저장
        sdr.setSrDataConditions(conditions);

        // 기본값 설정
        sdr.setConfirmYN(YN.N);
        srDataRequestRepository.saveAndFlush(sdr);
        return mapper.map(sdr,SrDataRequestVo.class);
    }

    @Override
    public SrDataRequestVo modify(SrDataRequestModifyAfterConfirmDto dto) {
        return null;
    }

    @Override
    public void confirm(SrDataRequestConfirmDto dto) {

    }

    @Override
    public void remove(SrDataRequestRemoveDto dto) {

    }

    @Override
    public Page<SrDataRequestVo> find(SrDataRequestFindDto dto, Pageable page) {
        return null;
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
