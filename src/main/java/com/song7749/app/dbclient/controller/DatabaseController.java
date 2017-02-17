package com.song7749.app.dbclient.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.FindTableDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveFavorityQueryDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.entities.FavorityQuery;
import com.song7749.dl.dbclient.service.FavorityQueryManager;
import com.song7749.dl.dbclient.service.ServerInfoManager;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.DatabaseDdlVO;
import com.song7749.dl.dbclient.vo.FavorityQueryVO;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.FunctionVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ProcedureVO;
import com.song7749.dl.dbclient.vo.SequenceVO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.dl.dbclient.vo.TableVO;
import com.song7749.dl.dbclient.vo.TriggerVO;
import com.song7749.dl.dbclient.vo.ViewVO;
import com.song7749.dl.login.annotations.Login;
import com.song7749.dl.login.exception.AuthorityUserException;
import com.song7749.dl.login.service.LoginManager;
import com.song7749.dl.login.type.LoginResponseType;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.service.MemberManager;
import com.song7749.dl.member.type.AuthType;
import com.song7749.dl.member.vo.MemberDatabaseVO;
import com.song7749.dl.member.vo.MemberVO;
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

	@Autowired
	MemberManager memberManager;

	@Resource(name="genericExcelView")
	GenericExcelView genericExcelView;

	@ApiOperation(value = "데이터 베이스 서버 조회"
			,notes = "serverInfoSeq 를 이용해서 1개의 정보만 조회 한다."
			,response=ServerInfoVO.class)
	@RequestMapping(value="/server",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getServer(
			@RequestParam(value="serverInfoSeq",required=true)
			@ApiParam	Integer serverInfoSeq,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		ServerInfoVO infoList = serverInfoManager.findServerInfo(new FindServerInfoDTO(serverInfoSeq,useCache));

		model.addAttribute("server", infoList);
	}

	@ApiOperation(value = "데이터 베이스 서버 리스트 조회"
					,notes = "등록되어 있는 Database 서버 리스트를 조회 한다."
					,response=ServerInfoVO.class)
	@RequestMapping(value="/serverList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getServerList(
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		// 회원이 사용 가능한 서버리스트를 조회하기 위해 로그인 정보를 가져온다.
		String memberId = loginManager.getLoginID(request);
		if(null==memberId){
			throw new AuthorityUserException("로그인 뒤에 사용 가능 합니다.");
		}

		FindServerInfoListDTO dto=new FindServerInfoListDTO(useCache);

		if(!StringUtils.isBlank(memberId)){
			List<MemberVO> memberList=memberManager.findMemberList(new FindMemberListDTO(memberId));
			// 관리자가 아닌 경우에는 선택되어진 Databse 만 사용 가능하다
			if(!AuthType.ADMIN.equals(memberList.get(0).getAuthType())){
				if(CollectionUtils.isEmpty(memberList.get(0).getMemberDatabaseVOList())){
					throw new AuthorityUserException("사용 가능한 Database 가 없습니다.");
				}
				for(MemberDatabaseVO mdv : memberList.get(0).getMemberDatabaseVOList()){
					dto.getServerInfoSeqList().add(mdv.getServerInfo().getServerInfoSeq());
				}
			}
		}

		// 캐시를 사용한다.
		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(dto);

		logger.trace("serverList {}",infoList);
		model.addAttribute("serverInfo", infoList);
	}

	@ApiOperation(value = "데이터 베이스 스키마, SID 조회"
			,notes = "등록되어 있는 Database 서버 의 스키마(Mysql), SID(Oracle) 을 조회 한다."
			,response=ServerInfoVO.class)
	@RequestMapping(value="/schemaList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getSchemaList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){
		// 캐시를 사용한다.
		List<ServerInfoVO> infoList = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host,useCache));

		logger.trace("serverList {}",infoList);
		model.addAttribute("serverInfo", infoList);
	}

	@ApiOperation(value = "데이터베이스 테이블 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 을 조회 한다."
			,response=TableVO.class)
	@RequestMapping(value="/tableList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getTableList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		// 캐시 사용 여부를 결정한다.
		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<TableVO> tableList=null;
		if(null!=list & list.size()>0){
			// TODO cache 적용
			tableList=serverInfoManager.findTableVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("tableList : {}",tableList);
		model.addAttribute("tableList", tableList);
	}

	@ApiOperation(value = "데이터베이스 View 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 View 리스트를 조회 한다."
			,response=ViewVO.class)
	@RequestMapping(value="/viewList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getViewList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<ViewVO> viewList=null;
		if(null!=list & list.size()>0){
			viewList=serverInfoManager.findViewVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("viewList : {}",viewList);
		model.addAttribute("viewList", viewList);
	}

	@ApiOperation(value = "데이터베이스 View Detail 정보 조회"
			,notes = "등록되어 있는 Database 서버의 Detail 정보를 조회 한다."
			,response=ViewVO.class)
	@RequestMapping(value="/viewDetailLis",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getViewDetailList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<Map<String,String>> viewDetailList = null;
		if(null!=list && list.size()>0){
			viewDetailList = serverInfoManager.findViewDetailList(new FindTableDTO(list.get(0).getServerInfoSeq(), name, useCache));
		}

		logger.trace("viewDetailList : {}",viewDetailList);
		model.addAttribute("viewDetailList", viewDetailList);
	}

	@ApiOperation(value = "데이터베이스 View Source 정보 조회"
			,notes = "등록되어 있는 Database 서버의 Source 정보를 조회 한다."
			,response=ViewVO.class)
	@RequestMapping(value="/viewSourceList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getViewSourceList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<ViewVO> viewSourceList=null;
		if(null!=list & list.size()>0){
			viewSourceList=serverInfoManager.findViewVOSourceList(new FindTableDTO(list.get(0).getServerInfoSeq(), name, useCache));
		}

		logger.trace("viewSourceList : {}",viewSourceList);
		model.addAttribute("viewSourceList", viewSourceList);
	}


	@ApiOperation(value = "데이터베이스 Procedure 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Procedure 리스트를 조회 한다."
			,response=ProcedureVO.class)
	@RequestMapping(value="/procedureList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getProcedure(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<ProcedureVO> procedureList=null;
		if(null!=list & list.size()>0){
			procedureList=serverInfoManager.findProcedureVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("procedureList : {}",procedureList);
		model.addAttribute("procedureList", procedureList);
	}

	@ApiOperation(value = "데이터베이스 Procedure 상세 조회"
			,notes = "등록되어 있는 Database 서버의 Procedure 내용을 조회 한다."
			,response=ProcedureVO.class)
	@RequestMapping(value="/procedureDetailList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void procedureDetail(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<Map<String,String>> procedureList=null;
		if(null!=list & list.size()>0){
			procedureList=serverInfoManager.findProcedureDetailList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}

		logger.trace("procedureList : {}",procedureList);
		model.addAttribute("procedureList", procedureList);
	}

	@ApiOperation(value = "데이터베이스 Procedure Source 조회"
			,notes = "등록되어 있는 Database 서버의 Procedure Source 조회 한다."
			,response=ProcedureVO.class)
	@RequestMapping(value="/procedureSourceList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void procedureSource(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<ProcedureVO> procedureList=null;
		if(null!=list & list.size()>0){
			procedureList=serverInfoManager.findProcedureVOSourceList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}
		logger.trace("procedureList : {}",procedureList);
		model.addAttribute("procedureList", procedureList);
	}

	@ApiOperation(value = "데이터베이스 Function 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 function 리스트를 조회 한다."
			,response=FunctionVO.class)
	@RequestMapping(value="/functionList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getFunction(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<FunctionVO> functionList=null;
		if(null!=list & list.size()>0){
			functionList=serverInfoManager.findFunctionVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("functionList : {}",functionList);
		model.addAttribute("functionList", functionList);
	}


	@ApiOperation(value = "데이터베이스 Function 상세 조회"
			,notes = "등록되어 있는 Database 서버의 Function 내용을 조회 한다."
			,response=Map.class)
	@RequestMapping(value="/functionDetailList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getFunctionDetail(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<Map<String,String>> functionList=null;
		if(null!=list & list.size()>0){
			functionList=serverInfoManager.findFunctionDetailList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}

		logger.trace("functionList : {}",functionList);
		model.addAttribute("functionList", functionList);
	}


	@ApiOperation(value = "데이터베이스 Function Source 조회"
			,notes = "등록되어 있는 Database 서버의 Function Source 조회 한다."
			,response=FunctionVO.class)
	@RequestMapping(value="/functionSourceList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getFunctionSource(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<FunctionVO> functionList=null;
		if(null!=list & list.size()>0){
			functionList=serverInfoManager.findFunctionVOSourceList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}

		logger.trace("functionList : {}",functionList);
		model.addAttribute("functionList", functionList);
	}

	@ApiOperation(value = "데이터베이스 Trigger 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Trigger 리스트를 조회 한다."
			,response=TriggerVO.class)
	@RequestMapping(value="/triggerList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getTrigger(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<TriggerVO> triggerList=null;
		if(null!=list & list.size()>0){
			triggerList=serverInfoManager.findTriggerVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("triggerList : {}",triggerList);
		model.addAttribute("triggerList", triggerList);
	}


	@ApiOperation(value = "데이터베이스 Trigger 상세 조회"
			,notes = "등록되어 있는 Database 서버의 Trigger 내용을 조회 한다."
			,response=Map.class)
	@RequestMapping(value="/triggerDetailList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getTriggerDetail(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<Map<String,String>> triggerList=null;
		if(null!=list & list.size()>0){
			triggerList=serverInfoManager.findTriggerDetailList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}

		logger.trace("triggerList : {}",triggerList);
		model.addAttribute("triggerList", triggerList);
	}


	@ApiOperation(value = "데이터베이스 Trigger Source 조회"
			,notes = "등록되어 있는 Database 서버의 Trigger Source 조회 한다."
			,response=TriggerVO.class)
	@RequestMapping(value="/triggerSourceList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getTriggerSource(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<TriggerVO> triggerList=null;
		if(null!=list & list.size()>0){
			triggerList=serverInfoManager.findTriggerVOSourceList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}

		logger.trace("triggerList : {}",triggerList);
		model.addAttribute("triggerList", triggerList);
	}

	@ApiOperation(value = "데이터베이스 Sequence 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Sequence 리스트를 조회 한다."
			,response=SequenceVO.class)
	@RequestMapping(value="/sequenceList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getSequence(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<SequenceVO> sequenceList=null;
		if(null!=list & list.size()>0){
			sequenceList=serverInfoManager.findSequenceVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),useCache));
		}

		logger.trace("sequenceList : {}",sequenceList);
		model.addAttribute("sequenceList", sequenceList);
	}


	@ApiOperation(value = "데이터베이스 Sequence 상세내용 조회"
			,notes = "등록되어 있는 Database 서버의 Sequence 상세내용 조회 한다."
			,response=SequenceVO.class)
	@RequestMapping(value="/sequenceDetailList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getSequenceDetail(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="name",required=true)
			@ApiParam 	String  name,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<Map<String,String>> sequenceList=null;
		if(null!=list & list.size()>0){
			sequenceList=serverInfoManager.findSequenceDetailList(new FindTableDTO(list.get(0).getServerInfoSeq(),name,useCache));
		}
		logger.trace("sequenceList : {}",sequenceList);
		model.addAttribute("sequenceList", sequenceList);
	}


	@ApiOperation(value = "데이터베이스 테이블  필드 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 의 필드를 조회 한다."
			,response=FieldVO.class)
	@RequestMapping(value="/fieldList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getFieldList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String account,
			@RequestParam(value="tableName",required=true)
			@ApiParam	String tableName,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){


		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<FieldVO> fieldList=null;
		if(null!=list & list.size()>0){
			fieldList=serverInfoManager.findTableFieldVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName,useCache));
		}

		logger.trace("fieldList : {}",fieldList);
		model.addAttribute("fieldList", fieldList);
	}

	@ApiOperation(value = "데이터베이스 테이블 DDL 조회"
			,notes = "등록되어 있는 Database 서버의 Table DDL 문을 조회 한다. EX) Create Table."
			,response=FieldVO.class)
	@RequestMapping(value="/showCreateTable",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getShowCreateTable(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String account,
			@RequestParam(value="tableName",required=true)
			@ApiParam	String tableName,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){


		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<DatabaseDdlVO> ddlList=null;
		if(null!=list & list.size()>0){
			ddlList=serverInfoManager.findShowCreateTable(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName,useCache));
		}

		logger.trace("ddlList : {}",ddlList);
		model.addAttribute("ddlList", ddlList);
	}



	@ApiOperation(value = "데이터베이스 테이블 인덱스 리스트 조회"
			,notes = "등록되어 있는 Database 서버의 Table 의 인덱스 리스트 조회."
			,response=IndexVO.class)
	@RequestMapping(value="/indexList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getIndexList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String account,
			@RequestParam(value="tableName",required=true)
			@ApiParam	String tableName,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<IndexVO> indexList=null;
		if(null!=list & list.size()>0){
			indexList=serverInfoManager.findTableIndexVOList(new FindTableDTO(list.get(0).getServerInfoSeq(),tableName,useCache));
		}

		logger.trace("indexList : {}",indexList);
		model.addAttribute("indexList", indexList);
	}

	@ApiOperation(value = "데이터베이스 서버 정보 저장 All Rows"
			,notes = "데이터베이스 서버 정보를 저장한다.여러 서버를 한번에 입력 한다.<br/>"
					+ "여러 개의 서버를 동시에 입력할 수 있으며, 모든 필드의 값을 row 별로 동일하게 입력해야 한다.<br/>"
					+ "정보를 Row 별로 빠짐없이 넣지 않으면 입력되지 않는다.<br/><br/>"
					+ "serverInfoSeq[] 를 넣지 않으면 입력, 있으면 업데이트 한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/saveDatabases",method=RequestMethod.POST)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.ADMIN})
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

	@ApiOperation(value = "데이터베이스 서버 정보 저장 1 Row"
			,notes = "데이터베이스 서버 정보를 저장한다. 1 Row 만 입력 가능<br/>"
			,response=ResponseResult.class)
	@RequestMapping(value="/addDatabases",method=RequestMethod.POST)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.ADMIN})
	public void addDatabases(
			@RequestParam(value="host",required=true)
			@ApiParam	String host,
			@RequestParam(value="hostAlias",required=false)
			@ApiParam	String hostAlias,
			@RequestParam(value="schemaName",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String  account,
			@RequestParam(value="password",required=true)
			@ApiParam	String  password,
			@RequestParam(value="driver",required=true)
			@ApiParam	DatabaseDriver  driver,
			@RequestParam(value="charset",required=true)
			@ApiParam	String  charset,
			@RequestParam(value="port",required=true)
			@ApiParam	String  port,
			HttpServletRequest request,
			ModelMap model){

		SaveServerInfoDTO dto = new SaveServerInfoDTO(
				host,
				hostAlias,
				schemaName,
				account,
				password,
				driver,
				charset,
				port);
		serverInfoManager.saveServerInfo(dto);


		model.clear();
		model.addAttribute("message", "서버 정보가 저장되었습니다.");
	}


	@ApiOperation(value = "데이터베이스 서버 정보 수정 1 Row"
			,notes = "데이터베이스 서버 정보를 수정한다. 1 Row 만 입력 가능<br/>"
			,response=ResponseResult.class)
	@RequestMapping(value="/modifyDatabase",method=RequestMethod.POST)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.ADMIN})
	public void modifyDatabases(
			@RequestParam(value="serverInfoSeq",required=true)
			@ApiParam	Integer serverInfoSeq,
			@RequestParam(value="host",required=true)
			@ApiParam	String host,
			@RequestParam(value="hostAlias",required=false)
			@ApiParam	String hostAlias,
			@RequestParam(value="schemaName",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String  account,
			@RequestParam(value="password",required=true)
			@ApiParam	String  password,
			@RequestParam(value="driver",required=true)
			@ApiParam	DatabaseDriver  driver,
			@RequestParam(value="charset",required=true)
			@ApiParam	String  charset,
			@RequestParam(value="port",required=true)
			@ApiParam	String  port,
			HttpServletRequest request,
			ModelMap model){

		ModifyServerInfoDTO dto = new ModifyServerInfoDTO(
				serverInfoSeq,
				host,
				hostAlias,
				schemaName,
				account,
				password,
				driver,
				charset,
				port);
		serverInfoManager.modifyServerInfo(dto);

		model.clear();
		model.addAttribute("message", "서버 정보가 수정되었습니다.");
	}

	@ApiOperation(value = "서버 정보를 삭제한다"
			,notes = "등록된 서버 정보를 삭제 한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/deleteDatabases",method=RequestMethod.DELETE)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.ADMIN})
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
	@RequestMapping(value="/executeQuery",method={RequestMethod.GET,RequestMethod.POST})
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void executeQuery(
			@RequestParam(value="server",required=true)				@ApiParam									String  host,
			@RequestParam(value="schema",required=true)				@ApiParam									String  schemaName,
			@RequestParam(value="account",required=true)			@ApiParam									String  account,
			@RequestParam(value="autoCommit",required=true)			@ApiParam("commit 가능 여부")					boolean autoCommit,
			@RequestParam(value="query",required=true)				@ApiParam("Database Query SQL")				String  query,
			@RequestParam(value="htmlAllow",required=true)			@ApiParam("결과물에 HTML 테그를 허용할 것인가 여부")	boolean htmlAllow,
			@RequestParam(value="limit",required=false)				@ApiParam									Long 	limit,
			@RequestParam(value="offset",required=false)			@ApiParam									Long 	offset,
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
		// query 내의 문자열 ; 를 제거 한다.
		decodedQuery=decodedQuery.replace(";", "");

		Long startTime = System.currentTimeMillis();

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, true));

		// reference 를 이용해서 실행시간, query 시간을 측정한다.
		List<Map<String,String>> resultList=null;
		if(null!=list & list.size()>0){
			ExecuteResultListDTO dto = new ExecuteResultListDTO(list.get(0).getServerInfoSeq(),
					host,
					schemaName,
					account,
					autoCommit,
					decodedQuery,
					htmlAllow,
					loginManager.getLoginID(request),
					request.getRemoteAddr());

			dto.setLimit((null == limit) ?  dto.getLimit() : limit);
			dto.setOffset((null == offset) ?  dto.getOffset() : offset);
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
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getDatabaseDriver(
			ModelMap model){
		model.addAttribute("databaseDriverList", DatabaseDriver.values());
	}


	@ApiOperation(value = "즐겨찾는 쿼리 입력"
			,notes = "자주 사용하는 쿼리를 저장하기 위한 컨트롤러"
			,response=ResponseResult.class)
	@RequestMapping(value="/saveFavoritiesQuery",method=RequestMethod.POST)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
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
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
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
	@RequestMapping(value="/findFavoritiesQueryList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void findFavoritiesQueryList(
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

		logger.trace("findFavoritiesQueryList {}",fqList);
		model.addAttribute("findFavoritiesQueryList", fqList);
	}


	@ApiOperation(value = "캐시 데이터 삭제"
			,notes = "database 정보 캐시 데이터를 삭제한다."
			,response=ResponseResult.class)
	@RequestMapping(value="/deleteCache",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void deleteCache(
			HttpServletRequest request,
			ModelMap model){

		serverInfoManager.clearCache();
		model.clear();
		model.addAttribute("message", "캐시가 삭제되었습니다.");
	}


	@ApiOperation(value = "데이터 베이스에 실행중인 쿼리를 중단"
			,notes = "사용자가 실행한 쿼리를 중단시킨다. 컨트롤+C 를 한 효과를 만든다."
			,response=ResponseResult.class)
	@RequestMapping(value="/killExecuteQuery",method=RequestMethod.POST)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void killExecuteQuery(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam	String  account,
			@RequestParam(value="query",required=true)
			@ApiParam("Database Query SQL")	String  query,
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

		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, true));

		// reference 를 이용해서 실행시간, query 시간을 측정한다.
		if(null!=list & list.size()>0){
			serverInfoManager.killExecutedQuery(
				new ExecuteResultListDTO(
					list.get(0).getServerInfoSeq(),
					host,
					schemaName,
					account,
					true,
					decodedQuery,
					false,
					loginManager.getLoginID(request),
					request.getRemoteAddr()));
		}
		model.clear();
		model.addAttribute("message", "쿼리가 중지 되었습니다.");
	}

	@ApiOperation(value = "데이터베이스 전체 필드 리스트 조회"
			,notes = "데이터베이스의 모든 필드 리스트를 조회 한다."
			,response=TableVO.class)
	@RequestMapping(value="/allFieldList",method=RequestMethod.GET)
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public void getAllFieldList(
			@RequestParam(value="server",required=true)
			@ApiParam	String host,
			@RequestParam(value="schema",required=true)
			@ApiParam	String  schemaName,
			@RequestParam(value="account",required=true)
			@ApiParam 	String  account,
			@RequestParam(value="useCache",required=false)
			@ApiParam 	boolean  useCache,
			HttpServletRequest request,
			ModelMap model){

		// 캐시 사용 여부를 결정한다.
		List<ServerInfoVO> list = serverInfoManager.findServerInfoList(new FindServerInfoListDTO(host, schemaName, account, useCache));

		List<FieldVO> fieldList=null;
		if(null!=list & list.size()>0){
			fieldList=serverInfoManager.findAllFieldList(new FindServerInfoDTO(list.get(0).getServerInfoSeq(), useCache));
		}

		logger.trace("fieldList : {}",fieldList);
		model.addAttribute("fieldList", fieldList);
	}

}