package com.song7749.web.dbclient;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.common.MessageVo;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.DatabaseManager;
import com.song7749.dbclient.value.DatabaseDdlVo;
import com.song7749.dbclient.value.DatabaseObjectSearchDto;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.dbclient.value.FieldVo;
import com.song7749.dbclient.value.FunctionVo;
import com.song7749.dbclient.value.IndexVo;
import com.song7749.dbclient.value.ProcedureVo;
import com.song7749.dbclient.value.SequenceVo;
import com.song7749.dbclient.value.TableVo;
import com.song7749.dbclient.value.TriggerVo;
import com.song7749.dbclient.value.ViewVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "데이터베이스 Schema 정보 관리")
@RestController
@RequestMapping("/database")
public class DatabaseSchemaController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DBclientManager dbclientManager;

	@Autowired
	LoginSession session;

	@Autowired
	ModelMapper mapper;


	@GetMapping("/getTableList")
	@ApiOperation(value = "테이블 리스트 조회", response=TableVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<TableVo> getTableList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTableVoList(dto);
	}

	@GetMapping("/showCreateTable")
	@ApiOperation(value = "데이터베이스 테이블 DDL 조회", response=TableVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<DatabaseDdlVo> showCreateTable(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectShowCreateTable(dto);
	}

	@GetMapping("/getTableFieldList")
	@ApiOperation(value = "선택되어진  테이블의 필드 리스트 조회", response=FieldVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<FieldVo> getTableFieldList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTableFieldVoList(dto);
	}

	@GetMapping("/getTableIndexList")
	@ApiOperation(value = "선택되어진  테이블의 인덱스 리스트", response=FieldVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<IndexVo> getTableIndexList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTableIndexVoList(dto);
	}

	@GetMapping("/getAllFieldList")
	@ApiOperation(value = "데이터베이스 전체 필드 리스트 조회", response=FieldVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<FieldVo> getAllFieldList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectAllFieldList(dto);
	}

	@GetMapping("/getViewList")
	@ApiOperation(value = "데이터베이스 View 리스트 조회", response=ViewVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<ViewVo> getViewList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectViewVoList(dto);
	}

	@GetMapping("/getViewDetailLis")
	@ApiOperation(value = "데이터베이스 View Detail 조회", responseContainer="List")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<Map<String,String>> getViewDetailLis(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectViewDetailList(dto);
	}

	@GetMapping("/getViewSourceList")
	@ApiOperation(value = "데이터베이스 View Source 조회", response=ViewVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<ViewVo> getViewSourceList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectViewVoSourceList(dto);
	}

	@GetMapping("/getProcedureList")
	@ApiOperation(value = "데이터베이스 Procedure 리스트 조회", response=ProcedureVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<ProcedureVo> getProcedureList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectProcedureVoList(dto);
	}

	@GetMapping("/getProcedureDetailList")
	@ApiOperation(value = "데이터베이스 Procedure Detail 조회", responseContainer="List")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<Map<String,String>>  getProcedureDetailList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectProcedureDetailList(dto);
	}

	@GetMapping("/getProcedureSourceList")
	@ApiOperation(value = "데이터베이스 Procedure Source 조회", response=ProcedureVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<ProcedureVo> getProcedureSourceList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectProcedureVoSourceList(dto);
	}

	@GetMapping("/getFunctionList")
	@ApiOperation(value = "데이터베이스 Function 리스트 조회", response=FunctionVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<FunctionVo> getFunctionList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectFunctionVoList(dto);
	}

	@GetMapping("/getFunctionDetailList")
	@ApiOperation(value = "데이터베이스 Function Detail 조회", responseContainer="List")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<Map<String,String>>  getFunctionDetailList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectFunctionDetailList(dto);
	}

	@GetMapping("/getFunctionSourceList")
	@ApiOperation(value = "데이터베이스 Function Source 조회", response=FunctionVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<FunctionVo> getFunctionSourceList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectFunctionVoSourceList(dto);
	}

	@GetMapping("/getTriggerList")
	@ApiOperation(value = "데이터베이스 Trigger 리스트 조회", response=TriggerVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<TriggerVo> getTriggerList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTriggerVoList(dto);
	}

	@GetMapping("/getTriggerDetailList")
	@ApiOperation(value = "데이터베이스 Trigger Detail 조회", responseContainer="List")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<Map<String,String>>  getTriggerDetailList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTriggerDetailList(dto);
	}

	@GetMapping("/getTriggerSourceList")
	@ApiOperation(value = "데이터베이스 Trigger Source 조회", response=TriggerVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<TriggerVo> getTriggerSourceList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectTriggerVoSourceList(dto);
	}

	@GetMapping("/getSequenceList")
	@ApiOperation(value = "데이터베이스 Sequence 리스트 조회", response=SequenceVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<SequenceVo> getSequenceList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectSequenceVoList(dto);
	}

	@GetMapping("/getSequenceDetailList")
	@ApiOperation(value = "데이터베이스 Sequence Detail 조회", responseContainer="List")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public List<Map<String,String>>  getSequenceDetailList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseObjectSearchDto dosDto){

		ExecuteQueryDto dto = mapper.map(dosDto, ExecuteQueryDto.class);
		dto.setLoginId(session.getLogin().getLoginId());
		return dbclientManager.selectSequenceDetailList(dto);
	}

	@ApiOperation(value = "데이터 베이스 쿼리 실행"
			,notes = "입력된 database query 를 실행 한다."
			,response=MessageVo.class)
	@RequestMapping(value="/executeQuery",method={RequestMethod.GET,RequestMethod.POST})
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo executeQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute ExecuteQueryDto dto){

		if(null!=dto.getQuery() && dto.getQuery().length()<1){
			throw new IllegalArgumentException("입력된 쿼리가 없습니다. 쿼리 입력후에 실행하시기 바랍니다.");
		}

		dto.setLoginId(session.getLogin().getLoginId());
		dto.setIp(request.getRemoteAddr());
		return dbclientManager.executeQuery(dto);
	}

	@ApiOperation(value = "데이터 베이스에 실행중인 쿼리를 중단"
			,notes = "사용자가 실행한 쿼리를 중단시킨다. 컨트롤+C 를 한 효과를 만든다."
			,response=MessageVo.class)
	@PostMapping(value="/killExecuteQuery")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo killExecuteQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute ExecuteQueryDto dto){

		dto.setLoginId(session.getLogin().getLoginId());
		dto.setIp(request.getRemoteAddr());
		dbclientManager.killQuery(dto);
		return new MessageVo(HttpStatus.OK.value(), "쿼리가 중지되었습니다.");
	}

	@ApiOperation(value = "데이터 베이스 캐시 삭제 "
			,notes = "저장되어 있는 캐시를 일괄 삭제 한다"
			,response=MessageVo.class)
	@GetMapping(value="/deleteCache")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@CacheEvict(cacheNames = {"com.song7749.database.cache"}, allEntries = true)
	public MessageVo deleteCache(
			HttpServletRequest request, HttpServletResponse response){
		return new MessageVo(HttpStatus.OK.value(), "캐시가 갱신되었습니다.");
	}
}