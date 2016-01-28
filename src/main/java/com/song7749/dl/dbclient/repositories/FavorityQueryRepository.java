package com.song7749.dl.dbclient.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;

/**
 * <pre>
 * Class Name : FavorityQueryRepository.java
 * Description : 즐겨 찾는 쿼리 Repository
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 1. 25.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 1. 25.
*/

public interface FavorityQueryRepository extends Repository<FavorityQuery>{

	/**
	 * 즐겨찾는 쿼리 리스트 조회
	 * @param dto
	 * @return List<FavorityQuery>
	 */
	List<FavorityQuery> findFavorityQueryList(FindFavorityQueryListDTO dto);
}
