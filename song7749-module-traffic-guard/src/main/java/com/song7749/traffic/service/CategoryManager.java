package com.song7749.traffic.service;

import java.util.List;

import com.song7749.traffic.value.CategoryDeleteDto;
import com.song7749.traffic.value.CategoryFindDto;
import com.song7749.traffic.value.CategorySaveDto;
import com.song7749.traffic.value.CategoryVo;

public interface CategoryManager {

	public void saveCategory(CategorySaveDto dto);

	public void deleteCategory(CategoryDeleteDto dto);

	/**
	 * 카테고리 저장구조는 다중 depth 구조이나, 조회는 1depth 만 조회 되도록 제한함.
	 * 향후 다중 뎁스를 지원할 예정임.
	 *
	 * @param dto
	 * @return
	 */
	public List<CategoryVo> findCategoryList(CategoryFindDto dto);
}