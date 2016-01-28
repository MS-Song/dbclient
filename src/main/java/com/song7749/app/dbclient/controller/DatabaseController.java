package com.song7749.app.dbclient.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.song7749.app.dbclient.view.GenericExcelView;
import com.song7749.dl.base.ResponseResult;
import com.song7749.dl.dbclient.dto.DeleteFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.dto.FindFavorityQueryListDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.FindTableDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.dl.dbclient.service.FavorityQueryManager;
import com.song7749.dl.dbclient.service.ServerInfoManager;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.FavorityQueryVO;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.dl.dbclient.vo.TableVO;
import com.song7749.dl.login.exception.AuthorityUserException;
import com.song7749.dl.login.service.LoginManager;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * <pre>
 * Class Name : DatabaseController.java
 * Description : 데이터 베이스 컨트롤러
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

@Api(value = "database", description = "database 관련 기능",position=1)
@Controller
@RequestMapping("/database")
public class DatabaseController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ServerInfoManager serverInfoManager;

	@Autowired
	FavorityQueryManager favorityQueryManager;

	@Autowired
	LoginManager loginManager;

	@Resource(name="genericExcelView")
	GenericExcelView genericExcelView;


	@ApiOperation(value = "데이터 베이스 서버 리스트 조회"
					,notes = "등록되어 있는 Database 서버 리스트를 조회 한다."
					,response=ServerInfoVO.class)
	@RequestMapping(value="/serverList",method=RequestMethod.GET)
	public void getServerList(
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(new FindServerInfoListDTO());

		logger.trace("serverList {}",infoList);
		model.addAttribute("serverInfo", infoList);
	}

	@ApiOperation(value = "데이터 베이스 스키마, SID 조회"
			,notes = "등록되어 있는 Database 서버 의 스키마(Mysql), SID(Oracle) 을 조회 한다."
			,response=ServerInfoVO.class)
	@RequestMapping(value="/schemaList",method=RequestMethod.GET)
	public void getSchemaList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			HttpServletRequest request,
			ModelMap model){
		// 테스트 데이터 설정
		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host));

		logger.trace("serverList {}",infoList);
		model.addAttribute("serverInfo", infoList);
	}

	@ApiOperation(value = "데이터베이스 테이블 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 을 조회 한다."
			,response=TableVO.class)
	@RequestMapping(value="/tableList",method=RequestMethod.GET)
	public void getTableList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<TableVO> tableList=null;
		if(null!=list & list.size()>0){
			tableList=serverInfoManager.findTableVOList(new FindTableDTO(list.get(0).getServerInfoSeq()));
		}

		logger.trace("tableList : {}",tableList);
		model.addAttribute("tableList", tableList);
	}

	@ApiOperation(value = "데이터베이스 테이블  필드 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 의 필드를 조회 한다."
			,response=FieldVO.class)
	@RequestMapping(value="/fieldList",method=RequestMethod.GET)
	public void getFieldList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String account,
			@RequestParam(value="table",required=true)
			@ApiParam	String tableName,
			HttpServletRequest request,
			ModelMap model){


		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<FieldVO> fieldList=null;
		if(null!=list & list.size()>0){
			fieldList=serverInfoManager.findTableFieldVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName));
		}

		logger.trace("fieldList : {}",fieldList);
		model.addAttribute("fieldList", fieldList);
	}


	@ApiOperation(value = "데이터베이스 테이블 인덱스 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 의 인덱스 리스트 조회."
			,response=IndexVO.class)
	@RequestMapping(value="/indexList",method=RequestMethod.GET)
	public void getIndexList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String account,
			@RequestParam(value="table",required=true)
			@ApiParam	String tableName,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account));

		List<IndexVO> indexList=null;
		if(null!=list & list.size()>0){
			indexList=serverInfoManager.findTableIndexVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName));
		}

		logger.trace("indexList : {}",indexList);
		model.addAttribute("indexList", indexList);
	}

	@ApiOperation(value = "데이터베이스 서버 정보 저장"
			,notes = "데이터베이스 서버 정보를 저장한다.<br/>"
					+ "여러 개의 서버를 동시에 입력할 수 있으며, 모든 필드의 값을 row 별로 동일하게 입력해야 한다.<br/>"
					+ "정보를 Row 별로 빠짐없이 넣지 않으면 입력되지 않는다.<br/><br/>"
					+ "serverInfoSeq[] 를 넣지 않으면 입력, 있으면 업데이트 한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/saveDatabases",method=RequestMethod.POST)
	public void saveDatabases(
			@RequestParam(value="serverInfoSeq[]",required=true)
			@ApiParam	Integer[] serverInfoSeq,
			@RequestParam(value="host[]",required=true)
			@ApiParam	String[] host,
			@RequestParam(value="hostAlias[]",required=false)
			@ApiParam	String[] hostAlias,
			@RequestParam(value="schemaName[]",required=true)
			@ApiParam	String[]  schemaName,
			@RequestParam(value="account[]",required=true)
			@ApiParam	String[]  account,
			@RequestParam(value="password[]",required=true)
			@ApiParam	String[]  password,
			@RequestParam(value="driver[]",required=true)
			@ApiParam	DatabaseDriver[]  driver,
			@RequestParam(value="charset[]",required=true)
			@ApiParam	String[]  charset,
			@RequestParam(value="port[]",required=true)
			@ApiParam	String[]  port,
			HttpServletRequest request,
			ModelMap model){


		List<SaveServerInfoDTO> saveList = new ArrayList<SaveServerInfoDTO>();
		List<ModifyServerInfoDTO> modifyList = new ArrayList<ModifyServerInfoDTO>();
		for (int i = 0; i < host.length; i++) {
			// 생성해야 하는 경우
			if(serverInfoSeq.length==0 || null==serverInfoSeq[i]){
				saveList.add(new SaveServerInfoDTO(
						host[i],
						hostAlias[i],
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
						hostAlias[i],
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

		// 추가
		if(saveList.size()>0){
			serverInfoManager.saveServerInfoFacade(saveList);
		}
		// 수정
		if(modifyList.size()>0){
			serverInfoManager.modifyServerInfoFacade(modifyList);
		}

		model.clear();
		model.addAttribute("message", "서버 정보가 저장되었습니다.");
	}

	@ApiOperation(value = "서버 정보를 삭제한다"
			,notes = "등록된 서버 정보를 삭제 한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/deleteDatabases",method=RequestMethod.DELETE)
	public void saveDatabases(
			@RequestParam(value="serverInfoSeq",required=true)
			@ApiParam	Integer serverInfoSeq,
			HttpServletRequest request,
			ModelMap model){

		DeleteServerInfoDTO dto = new DeleteServerInfoDTO(serverInfoSeq);
		serverInfoManager.deleteServerInfo(dto);

		model.clear();
		model.addAttribute("message", serverInfoSeq+" 번 정보가 삭제되었습니다.");

	}

	@ApiOperation(value = "데이터 베이스 쿼리 실행"
			,notes = "입력된 database query 를 실행 한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/executeQuery",method=RequestMethod.POST)
	public void executeQuery(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String  account,
			@RequestParam(value="autoCommit",required=true)
			@ApiParam("commit 가능 여부")	boolean  autoCommit,
			@RequestParam(value="query",required=true)
			@ApiParam("Database Query SQL")	String  query,
			@RequestParam(value="htmlAllow",required=true)
			@ApiParam("결과물에 HTML 테그를 허용할 것인가 여부")	boolean  htmlAllow,
			HttpServletRequest request,
			ModelMap model){



		if(null==host || host.trim().length()<1){
			throw new IllegalArgumentException("선택된 서버가 없습니다. Database 선택메뉴에서 서버를 선택하세요");
		}

		if(null==schemaName || schemaName.trim().length()<1){
			throw new IllegalArgumentException("선택된 Database 가 없습니다. Database 선택메뉴에서 Shema 를 선택하세요");
		}

		if(null==account  || account.trim().length()<1){
			throw new IllegalArgumentException("선택된 계정이 없습니다. Database 선택메뉴에서 계정을 선택하세요");
		}

		if(null==query || query.trim().length()<1){
			throw new IllegalArgumentException("입력된 쿼리가 없습니다. 쿼리 입력후에 실행하시기 바랍니다.");
		}

		String decodedQuery=null;
		try {
			decodedQuery=URLDecoder.decode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("query 데이터의 디코딩 실패. 쿼리 내용에 디코딩이 안되는 문자열이 존재합니다");
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
					decodedQuery,
					htmlAllow);
			resultList=serverInfoManager.executeResultList(dto);
		}

		Long processTime = System.currentTimeMillis() - startTime;

		logger.trace("resultList : {}",resultList);
		logger.trace("processTime : {}",processTime);
		logger.trace("rowCount : {}",resultList.size());
		logger.trace("query : {}",query);

		model.addAttribute("resultList", resultList);
		model.addAttribute("processTime", processTime);
		model.addAttribute("rowCount", resultList.size());
		model.addAttribute("query", query);

	}

	@ApiOperation(value = "데이터 베이스 드라이버 조회"
			,notes = "데이터 베이스 드라이버를 조회한다."
			,response=DatabaseDriver.class)
	@RequestMapping(value="/getDatabaseDriver",method=RequestMethod.GET)
	public void getDatabaseDriver(
			ModelMap model){
		model.addAttribute("databaseDriverList", DatabaseDriver.values());
	}


	@ApiOperation(value = "즐겨찾는 쿼리 입력"
			,notes = "자주 사용하는 쿼리를 저장하기 위한 컨트롤러"
			,response=ResponseResult.class)
	@RequestMapping(value="/saveFavoritiesQuery",method=RequestMethod.POST)
	public void saveFavoritiesQuery(
			@RequestParam(value="memo",required=true)
			@ApiParam	String memo,
			@RequestParam(value="query",required=true)
			@ApiParam	String  query,
			HttpServletRequest request,
			ModelMap model){

		String id = loginManager.getLoginID(request);
		if(null==id){
			throw new AuthorityUserException("로그인 뒤에 사용 가능 합니다.");
		}

		favorityQueryManager.saveFavorityQuery(new SaveFavorityQueryDTO(
			id,
			memo,
			query,
			new Date()));

		model.clear();
		model.addAttribute("message", "쿼리가 저장되었습니다.");
	}

	@ApiOperation(value = "즐겨찾는 쿼리 삭제"
			,notes = "자주 사용하는 쿼리를 삭제하기 위한 컨트롤러"
			,response=ResponseResult.class)
	@RequestMapping(value="/removeFavoritiesQuery",method=RequestMethod.DELETE)
	public void removeFavoritiesQuery(
			@RequestParam(value="favorityQuerySeq",required=true)
			@ApiParam	Integer favorityQuerySeq,
			HttpServletRequest request,
			ModelMap model){

		String id = loginManager.getLoginID(request);
		if(null==id){
			throw new AuthorityUserException("로그인 뒤에 사용 가능 합니다.");
		}

		favorityQueryManager.deleteFavorityQuery(
				new DeleteFavorityQueryDTO(favorityQuerySeq, id));

		model.clear();
		model.addAttribute("message", "쿼리가 삭제되었습니다.");
	}

	@ApiOperation(value = "즐겨찾는 쿼리 조회"
			,notes = "자주 사용하는 쿼리 리스트를 조회 한다."
			,response=FavorityQuery.class)
	@RequestMapping(value="/findFavoritiesQuery",method=RequestMethod.GET)
	public void findFavoritiesQuery(
			@RequestParam(value="limit",required=false)
			@ApiParam	Long limit,
			@RequestParam(value="offset",required=false)
			@ApiParam	Long offset,
			HttpServletRequest request,
			ModelMap model){

		String id = loginManager.getLoginID(request);
		if(null==id){
			throw new AuthorityUserException("로그인 뒤에 사용 가능 합니다.");
		}

		FindFavorityQueryListDTO dto = new FindFavorityQueryListDTO(id);
		dto.setLimit(limit);
		dto.setOffset(offset);

		List<FavorityQueryVO> fqList = favorityQueryManager.findFavorityQueryVOList(dto );

		logger.trace("FavorityQueryVOList {}",fqList);
		model.addAttribute("favorityQueryList", fqList);
	}

//	@ApiOperation(value = "엑셀 다운로드"
//			,notes = "Request parameter 를 기반으로 엑셀파일을 생성하여 다운로드 한다."
//					+ "타이틀은 엑셀 컬럼 제목이며, 타이틀 배열 개수만큼 밸류를 row 로 생성한다."
//			,response=ResponseResult.class)
//	@RequestMapping(value={"/getExcel.xls"},
//			produces= {"application/vnd.ms-excel;charset=UTF-8", "text/csv;charset=UTF-8"},
//			method=RequestMethod.POST)
//	@Login(type=LoginResponseType.MESSAGE,value={AuthType.ADMIN,AuthType.NORMAL})
	//	public View getExcel(
//			@RequestParam(value="titles[]",required=true)
//			@ApiParam	String[] titles,
//			@RequestParam(value="values[]",required=true)
//			@ApiParam	String[] values,
//			ModelMap model,
//			HttpServletRequest request,
//			HttpServletResponse response){
//
//		if(null==titles || titles.length==0){
//			throw new IllegalArgumentException("excel 출력할 데이터가 없습니다.");
//		}
//
//		List<String> colNameList = new ArrayList<String>();
//		colNameList.addAll(Arrays.asList(titles));
//
//
//		List<List<String>> colValueList = new ArrayList<List<String>>();
//		if(null!=values && values.length>0){
//			List<String> subList = new ArrayList<String>();
//			for(int i=0;i<values.length;i++){
//				if(i>0 && i%titles.length==0){
//					colValueList.add(subList);
//					subList = new ArrayList<String>();
//				}
//				subList.add(values[i]);
//			}
//			colValueList.add(subList);
//		}
//
//
//		model.addAttribute("excelName", "dbClient"+System.currentTimeMillis());
//		model.addAttribute("colName",colNameList);
//		model.addAttribute("colValue",colValueList);
//		return genericExcelView;
//	}

}