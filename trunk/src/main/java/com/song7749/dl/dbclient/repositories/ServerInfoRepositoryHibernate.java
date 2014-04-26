package com.song7749.dl.dbclient.repositories;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupUpdate;
import com.song7749.util.validate.annotation.Validate;

@Repository("serverInfoRepository")
public class ServerInfoRepositoryHibernate implements ServerInfoRepository{

	@Resource
	protected SessionFactory dbClientSessionFactory;

	@Override
	public Session getSesson() {
		return dbClientSessionFactory.getCurrentSession();
	}

	@Override
	public Criteria getCriteriaOf(Class<ServerInfo> clazz) {
		return getSesson().createCriteria(clazz);
	}

	@Override
	@Validate(VG={ValidateGroupInsert.class})
	public void save(ServerInfo serverInfo) {
		getSesson().save(serverInfo);
	}

	@Override
	@Validate(VG={ValidateGroupUpdate.class})
	public void update(ServerInfo serverInfo) {
		getSesson().update(serverInfo);
	}

	@Override
	@Validate(VG={ValidateGroupDelete.class})
	public void delete(ServerInfo serverInfo) {
		getSesson().delete(serverInfo);
	}

	@Override
	public ServerInfo find(ServerInfo serverInfo) {
		return null;
	}

	@Override
	public List<ServerInfo> findServerInfoList(FindServerInfoListDTO dto) {
		Criteria criteria=getCriteriaOf(ServerInfo.class);

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