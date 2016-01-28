package com.song7749.dl.dbclient.service;

import java.util.List;

import com.song7749.dl.dbclient.dto.DeleteFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.FindFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.dto.ModifyFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.SaveFavorityQueryDTO;
import com.song7749.dl.dbclient.vo.FavorityQueryVO;

/**
 * <pre>
 * Class Name : FavorityQueryManager.java
 * Description : 즐겨 찾는 쿼리 매니저
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
public interface FavorityQueryManager {

	/**
	 * 즐겨 찾는 쿼리 저장
	 * @param dto
	 */
	void saveFavorityQuery(SaveFavorityQueryDTO dto);


	/**
	 * 즐겨 찾는 쿼리 수정
	 * @param dto
	 */
	void modifyFavorityQuery(ModifyFavorityQueryDTO dto);


	/**
	 * 즐겨 찾는 쿼리 삭제
	 * @param dto
	 */
	void deleteFavorityQuery(DeleteFavorityQueryDTO dto);


	/**
	 * 즐겨 찾는 쿼리 조회
	 * @param dto
	 * @return FavorityQueryVO
	 */
	FavorityQueryVO findFavorityQuery(FindFavorityQueryDTO dto);

	/**
	 * 즐겨 찾는 쿼리 리스트 조회
	 * @param dto
	 * @return List<FavorityQueryVO>
	 */
	List<FavorityQueryVO> findFavorityQueryVOList(FindFavorityQueryListDTO dto);
}