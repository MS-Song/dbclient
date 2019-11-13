package com.song7749.srcenter.service;

import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;
import com.song7749.srcenter.domain.SrDataRequest;
import com.song7749.srcenter.repository.SrDataRequestRepository;
import com.song7749.srcenter.value.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    LoginSession loginSession;

    @Override
    public SrDataRequestVo add(SrDataRequestAddDto dto) {
        // 회원 정보를 획득
        Member member = memberRepository.findByLoginId(loginSession.getLogin().getLoginId());
        if(null == member || null == member.getLoginId()){
            throw new IllegalArgumentException("로그인 상태가 아니거나 일치하는 회원이 존재하지 않습니다.");
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
        sdr.setResistMember(member);

        // TODO SQL condition 을 넣는다.




        return null;
    }

    @Override
    public SrDataRequestVo modify(SrDataRequestModifyBeforeConfirmDto dto) {
        return null;
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
}
