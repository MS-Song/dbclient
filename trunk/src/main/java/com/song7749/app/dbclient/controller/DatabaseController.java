package com.song7749.app.dbclient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.song7749.dl.dbclient.vo.ServerInfoVO;

@Controller
@RequestMapping("/database")
public class DatabaseController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/serverList.json",method=RequestMethod.GET)
	public void getServerList(
			HttpServletRequest request,
			ModelMap model){

		// 테스트 데이터 설정
		List<ServerInfoVO> infoList = new ArrayList<ServerInfoVO>();
		infoList.add(new ServerInfoVO(1, "local-database", "eldanawa", "root", "1234", "mysql", "utf-8","3306"));
		infoList.add(new ServerInfoVO(1, "local-database", "dbBilling", "root", "1234", "mysql", "utf-8","3306"));

		model.addAttribute("serverInfo", infoList);
	}

	@RequestMapping(value="/schemaList.json",method=RequestMethod.GET)
	public void getSchemaList(
			@RequestParam(value="server",required=true) String host,
			HttpServletRequest request,
			ModelMap model){
		// 테스트 데이터 설정
		List<ServerInfoVO> infoList = new ArrayList<ServerInfoVO>();
		infoList.add(new ServerInfoVO(1, "local-database", "eldanawa", "root", "1234", "mysql", "utf-8","3306"));
		infoList.add(new ServerInfoVO(1, "local-database", "dbBilling", "root", "1234", "mysql", "utf-8","3306"));

		model.addAttribute("serverInfo", infoList);
	}

	@RequestMapping(value="/tableList.json",method=RequestMethod.GET)
	public void getTableList(
			@RequestParam(value="host",required=true) String host,
			@RequestParam(value="schema",required=true) String  schema,
			@RequestParam(value="account",required=true) String  account,
			HttpServletRequest request,
			ModelMap model){

	}

	@RequestMapping(value="/saveDatabases.json",method=RequestMethod.POST)
	public void saveDatabases(
			@RequestParam(value="host[]",required=true) String[] hostArray,
			@RequestParam(value="schemaName[]",required=true) String[]  schemaNameArray,
			@RequestParam(value="account[]",required=true) String[]  accountArray,
			@RequestParam(value="password[]",required=true) String[]  passwordArray,
			@RequestParam(value="driver[]",required=true) String[]  driverArray,
			@RequestParam(value="charset[]",required=true) String[]  charsetArray,
			@RequestParam(value="port[]",required=true) String[]  portArray,
			HttpServletRequest request,
			ModelMap model){

		model.clear();
		model.addAttribute("message", "서버 정보가 저장되었습니다.");
	}
}