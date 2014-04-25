package com.song7749.dl.member.repositories;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupUpdate;
import com.song7749.util.validate.annotation.Validate;

/**
 * <pre>
 * Class Name : MemberRepositoryHibernate.java
 * Description : 회원 관리 Repository 구현체
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 21.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 21.
 */

@Repository("memberRepository")
public class MemberRepositoryHibernate implements MemberRepository{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected SessionFactory dbClientSessionFactory;

	@Override
	public final Session getSesson() {
		return dbClientSessionFactory.getCurrentSession();
	}

	@Override
	public Criteria getCriteriaOf(Class<Member> clazz){
		return getSesson().createCriteria(clazz);
	}

	@Override
	@Validate(VG={ValidateGroupInsert.class})
	public void save(Member member) {
		getSesson().save(member);
	}

	@Override
	@Validate(VG={ValidateGroupUpdate.class})
	public void update(Member member) {
		getSesson().update(member);

	}

	@Override
	public void delete(Member member) {
		getSesson().delete(member);
	}

	@Override
	public Member find(Member member) {
		return (Member)getSesson().byId(Member.class).load(member.getId());
	}

	@Override
	public List<Member> findMemberList(FindMemberListDTO dto) {
		Criteria criteria=getCriteriaOf(Member.class);

		// offset 시작점
		if(null != dto.getOffset()){
			criteria.setFirstResult(dto.getOffset().intValue());
		}
		// 최대 개수
		if(null != dto.getLimit()){
			criteria.setMaxResults(dto.getLimit().intValue());
		}
		return criteria.list();
	}
}