package com.song7749.dbclient.service;

import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.common.validate.Validate;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.MemberDatabase;
import com.song7749.dbclient.domain.MemberSaveQuery;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.repository.MemberDatabaseRepository;
import com.song7749.dbclient.repository.MemberSaveQueryRepository;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryModifyDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.service.LoginSession;

/**
 * <pre>
 * Class Name : DBClientMemberManager.java
 * Description : DB Client 내의 회원 관련 기능 관리
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 2. 25.
 */
@Service
public class DBClientMemberManagerImpl implements DBClientMemberManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberSaveQueryRepository memberSQRepository;

	@Autowired
	MemberDatabaseRepository memberDatabaseRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	LoginSession loginSession;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	@Override
	public MemberSaveQueryVo addMemberSaveQuery(MemberSaveQueryAddDto dto) {

		Optional<Database> oDB = databaseRepository.findById(dto.getDatabaseId());
		Database db = null;
		if(oDB.isPresent()) {
			db = oDB.get();
		} else {
			throw new IllegalArgumentException("Database 정보가 없습니다.");
		}

		Optional<Member> oMember = memberRepository.findById(dto.getMemberId());
		Member member = null;
		if(oMember.isPresent()) {
			member = oMember.get();
		} else {
			throw new IllegalArgumentException("회원 정보가 없습니다.");
		}

		MemberSaveQuery msq = new MemberSaveQuery(
				dto.getMemo(), dto.getQuery(), member, db);

		memberSQRepository.saveAndFlush(msq);

		return msq.getMemberSaveQueryVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberSaveQueryVo modifyMemberSaveQuery(MemberSaveQueryModifyDto dto) {

		Optional<MemberSaveQuery> oMemberSQ = memberSQRepository.findById(dto.getId());
		MemberSaveQuery msq = null;

		if(oMemberSQ.isPresent()) {
			msq=oMemberSQ.get();
		} else {
			throw new IllegalArgumentException("저장된 쿼리 정보가 없습니다.");
		}

		if(!dto.getMemberId().equals(msq.getMember().getId())) {
			throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
		}

		mapper.map(dto,msq);
		memberSQRepository.saveAndFlush(msq);

		return msq.getMemberSaveQueryVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public void removeMemberSaveQuery(MemberSaveQueryRemoveDto dto) {
		Optional<MemberSaveQuery> oMemberSQ = memberSQRepository.findById(dto.getId());
		MemberSaveQuery msq = null;

		if(oMemberSQ.isPresent()) {
			msq=oMemberSQ.get();
		} else {
			throw new IllegalArgumentException("저장된 쿼리 정보가 없습니다.");
		}

		if(!dto.getMemberId().equals(msq.getMember().getId())) {
			throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
		}

		memberSQRepository.deleteById(dto.getId());
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<MemberSaveQueryVo> findMemberSaveQueray(MemberSaveQueryFindDto dto, Pageable page) {

		MemberSaveQuery msq = new MemberSaveQuery();
		msq.setDatabase(new Database(dto.getDatabaseId()));
		msq.setMember(new Member(dto.getMemberId()));
		msq.setId(dto.getMemberSaveQueryId());

		Example<MemberSaveQuery> ex = Example.of(msq);

		Page<MemberSaveQuery> pMemberSaveQuery = memberSQRepository.findAll(ex, page);

		return  pMemberSaveQuery.map(
			new Function<MemberSaveQuery, MemberSaveQueryVo>() {
				@Override
				public MemberSaveQueryVo apply(MemberSaveQuery t) {
					return mapper.map(t, MemberSaveQueryVo.class);
				}
			}
		);
	}

	@Validate
	@Transactional
	@Override
	public MemberDatabaseVo addOrModifyMemberDatabase(MemberDatabaseAddOrModifyDto dto) {
		// ID 가 있는 경우에는 삭제를 시도 한다.
		if(null!=dto.getId()) {
			memberDatabaseRepository.deleteById(dto.getId());
			return new MemberDatabaseVo();
		} else {
			Optional<Database> oDB = databaseRepository.findById(dto.getDatabaseId());
			Database db = null;
			if(oDB.isPresent()) {
				db = oDB.get();
			} else {
				throw new IllegalArgumentException("Database 정보가 없습니다.");
			}

			Optional<Member> oMember = memberRepository.findById(dto.getMemberId());
			Member member = null;
			if(oMember.isPresent()) {
				member = oMember.get();
			} else {
				throw new IllegalArgumentException("회원 정보가 없습니다.");
			}

			return memberDatabaseRepository
					.saveAndFlush(new MemberDatabase(db, member))
					.getMemberDatabaseVo(mapper);
		}
	}

	/**
	 * Member Database 의 return 값이
	 * 회원의 권한에 따라 MemberDatabaseVo 또는  DatabaseVo 로 나눠지기 때문에
	 * 기초 데이터 조회 Method 를 별도로 분리 한다.
	 * @param dto
	 * @param page
	 * @return
	 */
	private Page<MemberDatabase> findMemberDatabase(MemberDatabaseFindDto dto, Pageable page){
		MemberDatabase md = new MemberDatabase();
		md.setId(dto.getId());
		md.setMember(new Member(dto.getMemberId()));
		md.setDatabase(new Database(dto.getDatabaseId()));

		Example<MemberDatabase> ex = Example.of(md);
		return memberDatabaseRepository.findAll(ex, page);
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<MemberDatabaseVo> findMemberDatabaseList(MemberDatabaseFindDto dto, Pageable page) {
		return  findMemberDatabase(dto,page).map(
			new Function<MemberDatabase, MemberDatabaseVo>() {
				@Override
				public MemberDatabaseVo apply(MemberDatabase t) {
					MemberDatabaseVo vo = mapper.map(t, MemberDatabaseVo.class);
					vo.setDatabaseVo(t.getDatabase().getDatabaseVo(mapper));
					return vo;
				}
			}
		);
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public Page<DatabaseVo> findDatabaseListByMemberAllow(MemberDatabaseFindDto dto, Pageable page) {
		return  findMemberDatabase(dto,page).map(
			new Function<MemberDatabase, DatabaseVo>() {
				@Override
				public DatabaseVo apply(MemberDatabase t) {
					return t.getDatabase().getDatabaseVo(mapper);
				}
			}
		);
	}
}