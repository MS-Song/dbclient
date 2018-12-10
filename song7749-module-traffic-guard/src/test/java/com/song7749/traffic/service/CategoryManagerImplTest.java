package com.song7749.traffic.service;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.traffic.domain.Category;
import com.song7749.traffic.repository.CategoryRepository;
import com.song7749.traffic.value.CategoryDeleteDto;
import com.song7749.traffic.value.CategoryFindDto;
import com.song7749.traffic.value.CategorySaveDto;
import com.song7749.traffic.value.CategoryVo;

public class CategoryManagerImplTest  extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryManager categoryManager;

	@Before
	public void setup() {
	}

	@Test
	public void testSaveCategory() throws Exception {
		// give
		CategorySaveDto dto = new CategorySaveDto(
				null,
				"depth1",
				null,
				null);
		// when
		categoryManager.saveCategory(dto);

		// then
		List<Category> list = categoryRepository.findAll();
		assertThat(list.size(),equalTo(1));

		// 자식 카테고리 입력
		// give
		dto = new CategorySaveDto(
				null,
				"depth2",
				null,
				list.get(0).getId());
		// when
		categoryManager.saveCategory(dto);

		// then
		list = categoryRepository.findAll();
		assertThat(list.size(),equalTo(2));

		// 카테고리 삭제 처리
		// give
		CategoryDeleteDto deleteDto =
				new CategoryDeleteDto(list.get(1).getId(), list.get(0).getId());
		// when
		categoryManager.deleteCategory(deleteDto);

		// then
		list = categoryRepository.findAll();
		assertThat(list.size(),equalTo(1));
		assertThat(list.get(0).getChildCategories().size(),equalTo(0));

		// categoryVo 조회
		// give
		// when
		List<CategoryVo> voList= categoryManager.findCategoryList(new CategoryFindDto());
		// then
		logger.trace(format("{}", "Category Vo List"),voList);
		assertThat(voList.size(),equalTo(1));
	}
}