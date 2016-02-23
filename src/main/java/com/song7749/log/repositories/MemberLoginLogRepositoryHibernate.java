package com.song7749.log.repositories;

import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.le;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.song7749.log.dto.FindMemberLoginLogListDTO;
import com.song7749.log.entities.MemberLoginLog;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.annotation.Validate;


@Repository("MemberLoginLogRepository")
public class MemberLoginLogRepositoryHibernate implements MemberLoginLogRepository{

	Logger logger = LoggerFactory.getLogger(getClass());


	@Resource
	protected SessionFactory dbClientLogSessionFactory;


	@Override
	public Session getSesson() {
		return dbClientLogSessionFactory.getCurrentSession();
	}

	@Override
	public Criteria getCriteriaOf(Class<MemberLoginLog> clazz) {
		return getSesson().createCriteria(clazz);
	}

	@Override
	@Validate(VG={ValidateGroupInsert.class})
	public void save(MemberLoginLog memberLoginLog) {
		getSesson().save(memberLoginLog);

	}

	@Override
	public void update(MemberLoginLog memberLoginLog) {
		throw new IllegalAccessError("Log 를 변경 할 수 없습니다.");

	}

	@Override
	public void delete(MemberLoginLog memberLoginLog) {
		throw new IllegalAccessError("Log 를 변경 할 수 없습니다.");

	}

	@Override
	public MemberLoginLog find(MemberLoginLog memberLoginLog) {
		return (MemberLoginLog)getSesson().byId(MemberLoginLog.class).load(memberLoginLog.getMemberLoginLogSeq());
	}

	@Override
	public List<MemberLoginLog> findMemberLoginLogList(
			FindMemberLoginLogListDTO dto) {

		Criteria criteria=getCriteriaOf(MemberLoginLog.class);

		if(null!=dto.getId()){
			criteria.add(eq("id",dto.getId()));
		}
		if(null!=dto.getIp()){
			criteria.add(eq("ip",dto.getIp()));
		}
		if(null!=dto.getStartDate() && null!=dto.getEndDate()){
			criteria.add(between("loginDate",dto.getStartDate(),dto.getEndDate()));
		} else if(null!=dto.getStartDate()){
			criteria.add(ge("loginDate",dto.getStartDate()));
		} else if(null!=dto.getEndDate()){
			criteria.add(le("loginDate",dto.getEndDate()));
		}
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