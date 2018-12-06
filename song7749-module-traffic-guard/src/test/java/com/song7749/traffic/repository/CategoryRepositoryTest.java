package com.song7749.traffic.repository;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.UnitTest;
import com.song7749.traffic.domain.Category;

public class CategoryRepositoryTest extends UnitTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * fixture
	 */
	Category c = new Category();

	@Before
	public void setup() {
		c.setName("Depth1");
		categoryRepository.saveAndFlush(c);
	}

	@Test
	public void saveCategory() {
		// give
		Category catagory = new Category();
		catagory.setName("Depth2");
		// when
		categoryRepository.saveAndFlush(catagory);
		// then
		assertThat(catagory.getId(),notNullValue());

		// 상위-하위 등록 및 조회
		// give
		catagory.setParentCategory(c);
		// when
		assertThat(catagory.getParentCategory(),notNullValue());


		// give
		// when
		List<Category> list = categoryRepository.findAll();
		logger.trace(format("{}", "Category List"),list);
		// then
		assertThat(list.size(), equalTo(2));

		// remove sub category
		// give
		Category depth1 = list.get(0);
		Category depth2 = list.get(0).getChildCategories().get(0);


		depth1.getChildCategories().remove(0);
		depth2.setParentCategory(null);

		// when
		categoryRepository.saveAndFlush(depth1);
		categoryRepository.delete(depth2);

		list = categoryRepository.findAll();
		logger.trace(format("{}", "Category List"),list);
		// then
		assertThat(list.size(), equalTo(1));
	}
}