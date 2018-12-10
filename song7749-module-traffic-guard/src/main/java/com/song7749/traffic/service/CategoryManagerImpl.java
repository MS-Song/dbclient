package com.song7749.traffic.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.common.validate.Validate;
import com.song7749.traffic.domain.Category;
import com.song7749.traffic.repository.CategoryRepository;
import com.song7749.traffic.value.CategoryDeleteDto;
import com.song7749.traffic.value.CategoryFindDto;
import com.song7749.traffic.value.CategorySaveDto;
import com.song7749.traffic.value.CategoryVo;

@Service
public class CategoryManagerImpl implements CategoryManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	@Override
	public void saveCategory(CategorySaveDto dto) {
		// 카테고리 ID가 있는 경우에는 수정
		if(null!=dto.getId()) {
			logger.trace(format("{}", "Category Modify"),dto);
			Optional<Category> oCategory = categoryRepository.findById(dto.getId());
			if(oCategory.isPresent()) {
				Category c = oCategory.get();
				// 일반 설정
				c.setName(dto.getName());
				c.setDesc(dto.getDesc());
				categoryRepository.saveAndFlush(c);
				// 부모 값이 존재 하는 경우
				if(null!=dto.getParentCategoryId()) {
					logger.trace(format("{}", "Category Update parent"),dto);
					Optional<Category> oParentCategory = categoryRepository.findById(dto.getParentCategoryId());
					// 부모값 셋팅
					if(oParentCategory.isPresent()) {
						c.setParentCategory(oParentCategory.get());
						categoryRepository.saveAndFlush(c);
					} else {
						throw new IllegalArgumentException("존재하지 않는 부모 카테고리 ID 입니다.");
					}
				}
			} else {
				throw new IllegalArgumentException("존재하지 않는 카테고리 ID 입니다.");
			}
		} else { // 없을 경우에는 신규 입력
			logger.trace(format("{}", "Category Add New"),dto);
			Category c = new Category();
			// 일반 설정
			c.setName(dto.getName());
			c.setDesc(dto.getDesc());
			categoryRepository.saveAndFlush(c);

			// 부모 값이 존재 하는 경우
			if(null!=dto.getParentCategoryId()) {
				logger.trace(format("{}", "Category Update parent"),dto);
				Optional<Category> oParentCategory = categoryRepository.findById(dto.getParentCategoryId());
				// 부모값 셋팅
				if(oParentCategory.isPresent()) {
					c.setParentCategory(oParentCategory.get());
					categoryRepository.saveAndFlush(c);
				} else {
					throw new IllegalArgumentException("존재하지 않는 부모 카테고리 ID 입니다.");
				}
			}
		}
	}

	@Validate
	@Transactional
	@Override
	public void deleteCategory(CategoryDeleteDto dto) {
		// 카테고리를 조회 한다.
		Optional<Category> oCategory = categoryRepository.findById(dto.getId());
		if(!oCategory.isPresent()) {
			throw new IllegalArgumentException("존재하지 않는 카테고리 ID 입니다.");
		}

		// 부모가 존재 하는 경우에는 부모에서 먼저 제거 한다.
		if(null!=dto.getParentCategoryId()) {
			Optional<Category> oParentCategory = categoryRepository.findById(dto.getParentCategoryId());
			if(oParentCategory.isPresent()) {
				oParentCategory.get().getChildCategories().remove(oCategory.get());
				oCategory.get().setParentCategory(null);
				// 부모에서 자식 제거 한 부분 저장
				categoryRepository.saveAndFlush(oParentCategory.get());
			} else {
				throw new IllegalArgumentException("존재하지 않는 부모 카테고리 ID 입니다.");
			}
		}
		// 카테고리 삭제
		categoryRepository.delete(oCategory.get());
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public List<CategoryVo> findCategoryList(CategoryFindDto dto) {
		return categoryRepository
				.findAll().parallelStream().map( t -> {
					return mapper.map(t, CategoryVo.class);
				}
			).collect(Collectors.toList());
	}
}
