package com.song7749.app.dbclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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