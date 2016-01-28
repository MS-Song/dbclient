package com.song7749.dl.dbclient.repositories;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupSelect;
import com.song7749.util.validate.ValidateGroupUpdate;
import com.song7749.util.validate.annotation.Validate;


@Repository("favorityQueryRepository")
public class FavorityQueryRepositoryHibernate implements FavorityQueryRepository {

	@Resource
	protected SessionFactory dbClientSessionFactory;

	@Override
	public Session getSesson() {
		return dbClientSessionFactory.getCurrentSession();
	}

	@Override
	public Criteria getCriteriaOf(Class<FavorityQuery> clazz) {
		return getSesson().createCriteria(clazz);
	}

	@Override
	@Validate(VG={ValidateGroupInsert.class})
	public void save(FavorityQuery favorityQuery) {
		getSesson().save(favorityQuery);
	}

	@Override
	@Validate(VG={ValidateGroupUpdate.class})
	public void update(FavorityQuery favorityQuery) {
		getSesson().update(favorityQuery);
	}

	@Override
	@Validate(VG={ValidateGroupDelete.class})
	public void delete(FavorityQuery favorityQuery) {
		getSesson().delete(favorityQuery);

	}

	@Override
	@Validate(VG={ValidateGroupSelect.class})
	public FavorityQuery find(FavorityQuery favorityQuery) {
		return 	(FavorityQuery)getSesson().byId(FavorityQuery.class).load(favorityQuery.getFavorityQuerySeq());
	}

	@Override
	public List<FavorityQuery> findFavorityQueryList(FindFavorityQueryListDTO dto) {
		Criteria criteria=getCriteriaOf(FavorityQuery.class);

		if(null != dto.getFavorityQuerySeq()){
			criteria.add(eq("favorityQuerySeq",dto.getFavorityQuerySeq()));
		}

		if(null != dto.getId()){
			criteria.add(eq("id",dto.getId()));
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