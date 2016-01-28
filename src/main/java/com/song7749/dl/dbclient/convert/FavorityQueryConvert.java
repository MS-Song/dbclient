package com.song7749.dl.dbclient.convert;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.song7749.dl.dbclient.dto.FindFavorityQueryDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.dl.dbclient.vo.FavorityQueryVO;


/**
 * <pre>
 * Class Name : FavorityQueryConvert.java
 * Description : 즐겨 찾는 쿼리 entity 를 VO 객체로 변환 한다.
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2016. 1. 27.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2016. 1. 27.
 */
public class FavorityQueryConvert {

	/**
	 * FavorityQuery to FavorityQueryVO
	 * @param favorityQuery
	 * @return FavorityQueryVO
	 * @throws UnsupportedEncodingException
	 */
	public static FavorityQueryVO convert(FavorityQuery favorityQuery){
		if(null==favorityQuery){
			return null;
		}
		return new FavorityQueryVO(
				favorityQuery.getFavorityQuerySeq(),
				favorityQuery.getId(),
				favorityQuery.getMemo(),
				favorityQuery.getQuery(),
				favorityQuery.getInputDate());
	}

	/**
	 * FavorityQueryList to FavorityQueryVOList
	 * @param favorityQueryList
	 * @return List<FavorityQueryVO>
	 */
	public static List<FavorityQueryVO> convert(List<FavorityQuery> favorityQueryList) {
		if(null==favorityQueryList){
			return null;
		}
		List<FavorityQueryVO> list = new ArrayList<FavorityQueryVO>();
		for (FavorityQuery favorityQuery : favorityQueryList) {
			list.add(convert(favorityQuery));
		}
		return list;
	}

	/**
	 * FavorityQuery to FavorityQuery
	 * @param dto
	 * @return FavorityQuery
	 */
	public static FavorityQuery convert(FindFavorityQueryDTO dto) {
		if(null==dto){
			return null;
		}
		return new FavorityQuery(dto.getFavorityQuerySeq());
	}
}