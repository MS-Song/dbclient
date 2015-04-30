package com.song7749.app.dbclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mangofactory.swagger.annotations.ApiIgnore;

/**
 * <pre>
 * Class Name : IndexController.java
 * Description : dbClient 메인 페이지 조회
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 15.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 15.
*/
@ApiIgnore
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping
	public String root(
			HttpServletRequest request,
			ModelMap model) {

		return "redirect:/index.html";

	}

	@RequestMapping(value={"index.html"},method=RequestMethod.GET)
	public String index(
			HttpServletRequest request,
			ModelMap model) {

		return "/index";
	}
}