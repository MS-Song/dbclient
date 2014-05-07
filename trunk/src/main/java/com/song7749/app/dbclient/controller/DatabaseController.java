package com.song7749.app.dbclient.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.FindTableDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.service.ServerInfoManager;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.dl.dbclient.vo.TableVO;

@Controller
@RequestMapping("/database")
public class DatabaseController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ServerInfoManager serverInfoManager;

	@RequestMapping(value="/serverList.json",method=RequestMethod.GET)
	public void getServerList(
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(new FindServerInfoListDTO());

		model.addAttribute("serverInfo", infoList);
	}

	@RequestMapping(value="/schemaList.json",method=RequestMethod.GET)
	public void getSchemaList(
			@RequestParam(value="server",required=true) String host,
			HttpServletRequest request,
			ModelMap model){
		// 테스트 데이터 설정
		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host));
		model.addAttribute("serverInfo", infoList);
	}

	@RequestMapping(value="/tableList.json",method=RequestMethod.GET)
	public void getTableList(
			@RequestParam(value="server",required=true) String host,
			@RequestParam(value="schema",required=true) String  schemaName,
			@RequestParam(value="account",required=true) String  account,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<TableVO> tableList=null;
		if(null!=list & list.size()>0){
			tableList=serverInfoManager.findTableVOList(new FindTableDTO(list.get(0).getServerInfoSeq()));
		}

		logger.debug("tableList : {}",tableList);
		model.addAttribute("tableList", tableList);
	}

	@RequestMapping(value="/fieldList.json",method=RequestMethod.GET)
	public void getFieldList(
			@RequestParam(value="server",required=true) String host,
			@RequestParam(value="schema",required=true) String  schemaName,
			@RequestParam(value="account",required=true) String  account,
			@RequestParam(value="table",required=true) String  tableName,
			HttpServletRequest request,
			ModelMap model){


		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<FieldVO> fieldList=null;
		if(null!=list & list.size()>0){
			fieldList=serverInfoManager.findTableFieldVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName));
		}

		logger.debug("fieldList : {}",fieldList);
		model.addAttribute("fieldList", fieldList);
	}

	@RequestMapping(value="/indexList.json",method=RequestMethod.GET)
	public void getIndexList(
			@RequestParam(value="server",required=true) String host,
			@RequestParam(value="schema",required=true) String  schemaName,
			@RequestParam(value="account",required=true) String  account,
			@RequestParam(value="table",required=true) String  tableName,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<IndexVO> indexList=null;
		if(null!=list & list.size()>0){
			indexList=serverInfoManager.findTableIndexVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName));
		}

		logger.debug("indexList : {}",indexList);
		model.addAttribute("indexList", indexList);
	}

	@RequestMapping(value="/saveDatabases.json",method=RequestMethod.POST)
	public void saveDatabases(
			@RequestParam(value="serverInfoSeq[]",required=true) Integer[] serverInfoSeq,
			@RequestParam(value="host[]",required=true) String[] host,
			@RequestParam(value="schemaName[]",required=true) String[]  schemaName,
			@RequestParam(value="account[]",required=true) String[]  account,
			@RequestParam(value="password[]",required=true) String[]  password,
			@RequestParam(value="driver[]",required=true) DatabaseDriver[]  driver,
			@RequestParam(value="charset[]",required=true) String[]  charset,
			@RequestParam(value="port[]",required=true) String[]  port,
			HttpServletRequest request,
			ModelMap model){


		List<SaveServerInfoDTO> saveList = new ArrayList<SaveServerInfoDTO>();
		List<ModifyServerInfoDTO> modifyList = new ArrayList<ModifyServerInfoDTO>();
		for (int i = 0; i < host.length; i++) {
			// 생성해야 하는 경우
			if(serverInfoSeq.length==0 || null==serverInfoSeq[i]){
				saveList.add(new SaveServerInfoDTO(
						host[i],
						schemaName[i],
						account[i],
						password[i],
						driver[i],
						charset[i],
						port[i]));
			} else {
			// 수정해야 하는 경우
				modifyList.add(new ModifyServerInfoDTO(
						serverInfoSeq[i],
						host[i],
						schemaName[i],
						account[i],
						password[i],
						driver[i],
						charset[i],
						port[i]));
			}
		}

		logger.debug("saveList : {}",saveList);
		logger.debug("modifyList : {}",modifyList);

		if(saveList.size()>0){
			serverInfoManager.saveServerInfoFacade(saveList);
		}
		if(modifyList.size()>0){
			serverInfoManager.modifyServerInfoFacade(modifyList);
		}

		model.clear();
		model.addAttribute("message", "서버 정보가 저장되었습니다.");
	}


	@RequestMapping(value="/executeQuery.json",method=RequestMethod.POST)
	public void executeQuery(
			@RequestParam(value="server",required=true) String host,
			@RequestParam(value="schema",required=true) String  schemaName,
			@RequestParam(value="account",required=true) String  account,
			@RequestParam(value="autoCommit",required=true) boolean  autoCommit,
			@RequestParam(value="query",required=true) String  query,
			HttpServletRequest request,
			ModelMap model){

		String decodedQuery=null;
		try {
			decodedQuery=URLDecoder.decode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			new IllegalArgumentException("query 데이터의 디코딩 실패. 쿼리 내용에 디코딩이 안되는 문자열이 존재합니다");
		}

		Long startTime = System.currentTimeMillis();

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		// reference 를 이용해서 실행시간, query 시간을 측정한다.
		List<Map<String,String>> resultList=null;
		if(null!=list & list.size()>0){
			ExecuteResultListDTO dto = new ExecuteResultListDTO(list.get(0).getServerInfoSeq(),
					host,
					schemaName,
					account,
					autoCommit,
					decodedQuery);
			resultList=serverInfoManager.executeResultList(dto);
		}

		Long processTime = System.currentTimeMillis() - startTime;

		logger.debug("resultList : {}",resultList);
		logger.debug("processTime : {}",processTime);
		logger.debug("rowCount : {}",resultList.size());
		logger.debug("query : {}",query);

		model.addAttribute("resultList", resultList);
		model.addAttribute("processTime", processTime);
		model.addAttribute("rowCount", resultList.size());
		model.addAttribute("query", query);

	}

	@RequestMapping(value={"/getExcel.xls","/getExcel.csv"},
			produces= {"application/vnd.ms-excel;charset=UTF-8", "text/csv;charset=UTF-8"},
			method=RequestMethod.POST)
	@ResponseBody
	public String getExcel(
			@RequestHeader(value="User-Agent") String clientBrowser,
			HttpServletRequest request,
			HttpServletResponse response){

		Calendar cal=Calendar.getInstance();
		String titleName = "excel"+ cal.getTimeInMillis();

//		try {
//			titleName = new String(titleName.getBytes("KSC5601"), "8859_1");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

		if(clientBrowser.indexOf("MSIE 5.5")>-1 || clientBrowser.indexOf("MSIE 6.0") > -1 ){
		  response.setHeader("Content-Type", "doesn/matter;");
		  response.setHeader("Content-Disposition", "filename="+titleName+".xls");
		}else{
		  response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
		  response.setHeader("Content-Disposition", "attachment; filename="+titleName+".xls");
		}

		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");



		return "<html><table><tr><td> 엑셀셈플데이터 </td></tr></table></html>";

	}
}