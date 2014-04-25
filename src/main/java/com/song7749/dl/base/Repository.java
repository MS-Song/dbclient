package com.song7749.dl.base;

import org.hibernate.Criteria;
import org.hibernate.Session;

public interface Repository<T> {
	/**
	 * 세션 획득.<br/>
	 * @return Session
	 */
	Session getSesson();

	/**
	 * create Criteria.
	 * @param clazz
	 * @return
	 */
	Criteria getCriteriaOf(Class<T> clazz);

	/**
	 * entity 저장
	 * @param entity
	 */
	void save(T entity);

	/**
	 * entity 수정
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 삭제
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * 조회
	 * @param entity
	 * @return
	 */
	T find(T entity);
}