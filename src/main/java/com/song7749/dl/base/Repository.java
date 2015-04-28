package com.song7749.dl.base;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupSelect;
import com.song7749.util.validate.ValidateGroupUpdate;
import com.song7749.util.validate.annotation.Validate;


/**
 * <pre>
 * Class Name : Repository.java
 * Description : Repository 인터페이스
 * 모든 Repository 는 Repository 인터페이스를 구현해야 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2015. 4. 26.		minsoo		신규작성
 *
 * </pre>
 *
 * @author minsoo
 * @since 2015. 4. 26.
 */
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
	@Validate(nullable=false,VG={ValidateGroupInsert.class})
	void save(T entity);

	/**
	 * entity 수정
	 * @param entity
	 */
	@Validate(nullable=false,VG={ValidateGroupUpdate.class})
	void update(T entity);

	/**
	 * 삭제
	 * @param entity
	 */
	@Validate(nullable=false,VG={ValidateGroupDelete.class})
	void delete(T entity);

	/**
	 * 조회
	 * @param entity
	 * @return
	 */
	@Validate(nullable=false,VG={ValidateGroupSelect.class})
	T find(T entity);
}