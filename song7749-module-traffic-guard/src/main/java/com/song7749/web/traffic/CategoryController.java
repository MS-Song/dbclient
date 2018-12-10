package com.song7749.web.traffic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.common.MessageVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.traffic.service.CategoryManager;
import com.song7749.traffic.value.CategoryDeleteDto;
import com.song7749.traffic.value.CategoryFindDto;
import com.song7749.traffic.value.CategorySaveDto;
import com.song7749.traffic.value.CategoryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="카테고리 생성 관리 컨트롤러")
@RestController
@RequestMapping("/category")
public class CategoryController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginSession session;

	@Autowired
	CategoryManager categoryManager;

	@ApiOperation(value = "카테고리 정보 저장"
			,notes = "카테고리 정보를 저장 한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PostMapping("/save")
	public MessageVo save(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute CategorySaveDto dto) {

		categoryManager.saveCategory(dto);
		return new MessageVo(HttpStatus.OK.value(), "카테고리가 저장되었습니다.");
	}

	@ApiOperation(value = "카테고리 정보 삭제"
			,notes = "카테고리 정보를 삭제 한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@DeleteMapping("/delete")
	public MessageVo delete(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute CategoryDeleteDto dto) {

		categoryManager.deleteCategory(dto);
		return new MessageVo(HttpStatus.OK.value(), "카테고리가 삭제되었습니다.");
	}

	@ApiOperation(value = "카테고리 정보 조회"
			,notes = "카테고리 정보를 조회 한다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@GetMapping("/list")
	public List<CategoryVo> list(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute CategoryFindDto dto) {

		return categoryManager.findCategoryList(dto);
	}
}