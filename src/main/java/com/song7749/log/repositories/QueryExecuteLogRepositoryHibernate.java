package com.song7749.log.repositories;

import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.le;
import static org.hibernate.criterion.Restrictions.like;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.song7749.log.dto.FindQueryExecuteLogListDTO;
import com.song7749.log.entities.QueryExecuteLog;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupSelect;
import com.song7749.util.validate.annotation.Validate;


@Repository("QueryExecuteLogRepository")
public class QueryExecuteLogRepositoryHibernate implements  QueryExecuteLogRepository{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected SessionFactory dbClientLogSessionFactory;

	@Override
	public Session getSesson() {
		return dbClientLogSessionFactory.getCurrentSession();
	}

	@Override
	public Criteria getCriteriaOf(Class<QueryExecuteLog> clazz) {
		return getSesson().createCriteria(clazz);
	}

	@Override
	@Validate(VG={ValidateGroupInsert.class})
	public void save(QueryExecuteLog queryExecuteLog) {
		getSesson().save(queryExecuteLog);

	}

	@Override
	public void update(QueryExecuteLog queryExecuteLog) {
		throw new IllegalAccessError("Log 를 변경 할 수 없습니다.");
	}

	@Override
	public void delete(QueryExecuteLog queryExecuteLog) {
		throw new IllegalAccessError("Log 를 변경 할 수 없습니다.");
	}

	@Override
	@Validate(VG={ValidateGroupSelect.class})
	public QueryExecuteLog find(QueryExecuteLog queryExecuteLog) {
		return (QueryExecuteLog)getSesson().byId(QueryExecuteLog.class).load(queryExecuteLog.getQueryExeucteSeq());
	}

	@Override
	public List<QueryExecuteLog> findQueryExecuteLogList(
			FindQueryExecuteLogListDTO dto) {
		Criteria criteria=getCriteriaOf(QueryExecuteLog.class);

		if(null!=dto.getId()){
			criteria.add(eq("id",dto.getId()));
		}
		if(null!=dto.getIp()){
			criteria.add(eq("ip",dto.getIp()));
		}
		if(null!=dto.getHost()){
			criteria.add(eq("host",dto.getHost()));
		}
		if(null!=dto.getHostAlias()){
			criteria.add(eq("hostAlias",dto.getHostAlias()));
		}
		if(null!=dto.getSchemaName()){
			criteria.add(eq("schemaName",dto.getSchemaName()));
		}
		if(null!=dto.getAccount()){
			criteria.add(eq("account",dto.getAccount()));
		}

		if(null!=dto.getQuery()){
			criteria.add(like("query",dto.getQuery()));
		}

		if(null!=dto.getStartDate() && null!=dto.getEndDate()){
			criteria.add(between("executeDate",dto.getStartDate(),dto.getEndDate()));
		} else if(null!=dto.getStartDate()){
			criteria.add(ge("executeDate",dto.getStartDate()));
		} else if(null!=dto.getEndDate()){
			criteria.add(le("executeDate",dto.getEndDate()));
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