package com.song7749.web.dbclient;

import static com.song7749.util.LogMessageFormatter.format;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.common.MessageVo;
import com.song7749.common.YN;
import com.song7749.dbclient.service.DBClientMemberManager;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.service.DatabaseManager;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseFindDto;
import com.song7749.dbclient.value.DatabaseModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyAddDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyFindDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyModifyDto;
import com.song7749.dbclient.value.DatabasePrivacyPolicyVo;
import com.song7749.dbclient.value.DatabaseRemoveDto;
import com.song7749.dbclient.value.DatabaseVo;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "데이터베이스 정보 관리")
@RestController
@RequestMapping("/database")
public class DatabaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DBclientManager dbclientManager;

	@Autowired
	LoginSession session;

	@Autowired
	DBClientMemberManager dbClientMemberManager;

	@PostMapping("/add")
	@ApiOperation(value = "데이터베이스 정보 입력", response = DatabaseVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo addDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseAddDto dto) {

		logger.trace(format("{}", "addDatabase"),dto);

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.addDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@PutMapping("/modify")
	@ApiOperation(value = "데이터베이스 정보 수정", response = DatabaseVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo modifyDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseModifyDto dto) {

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.modifyDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@DeleteMapping("/remove")
	@ApiOperation(value = "데이터베이스 정보 삭제", response = MessageVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo removeDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseRemoveDto dto) {

		databaseManager.removeDatabase(dto);
		return new MessageVo(HttpServletResponse.SC_OK , 1, "Database 정보가 삭제 되었습니다.");
	}

	@GetMapping("/list")
	@ApiOperation(value = "데이터베이스 정보 조회", response = DatabaseVo.class)
	@Login({ AuthType.NORMAL, AuthType.DEVELOPER, AuthType.ADMIN })
	public Page<DatabaseVo> getList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id")
			Pageable page){
		logger.trace(format("{}", "Database List Log"),dto);

		// 관리자만 전체 DB에 접근 가능하고, 나머지는 속해있는 DB 만 접근 가능하다. -- 정보성 조회의 경우에도 허용함.
		boolean isAccessAllDatabases = session.getLogin().getAuthType().equals(AuthType.ADMIN) || dto.isAccessAll()==true;
		if(isAccessAllDatabases){
			return databaseManager.findDatabaseList(dto, page);
		} else {
			return dbClientMemberManager.findDatabaseListByMemberAllow(
					new MemberDatabaseFindDto(
							session.getLogin().getId()), page);
		}
	}

	@PostMapping("/test")
	@ApiOperation(value = "데이터베이스 연결 테스트", response = DatabaseVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo testDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseAddDto dto) throws SQLException {
		logger.trace(format("{}", "addDatabase"),dto);
		return dbclientManager.testConnection(dto);
	}

	@GetMapping("/getDatabaseDriver")
	@ApiOperation(value = "데이터 베이스 드라이버 조회", response=DatabaseDriver.class)
	public DatabaseDriver[] getDatabaseDriver(HttpServletRequest request, HttpServletResponse response){
		return DatabaseDriver.values();
	}


	@GetMapping("/getCharset")
	@ApiOperation(value = "Charset 조회", response=Charset.class)
	public Charset[] getCharset(HttpServletRequest request, HttpServletResponse response){
		return Charset.values();
	}

	@PostMapping("/addDatabasePrivacyPolicy")
	@ApiOperation(value = "데이터베이스 개인정보 식별 입력", response = DatabasePrivacyPolicyVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo addDatabasePrivacyPolicy(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabasePrivacyPolicyAddDto dto) {
		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.addDatabasePrivacyPolicy(dto), "Database 개인정보 식별 정보가 저장 되었습니다.");
	}

	@PostMapping("/addOrModifyDatabasePrivacyPolicy")
	@ApiOperation(value = "데이터베이스 개인정보 식별 입력 또는 수정 일괄 처리", response = DatabasePrivacyPolicyVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo addOrModifyDatabasePrivacyPolicy(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=true) Long databaseId,
			@RequestParam(required=true) String[] databaseTableColumns) {

		List<DatabasePrivacyPolicyAddDto> list = new ArrayList<DatabasePrivacyPolicyAddDto>();
		if(null!=databaseTableColumns) {
			for(String tableColumnString : databaseTableColumns) {
				if(!StringUtils.isBlank(tableColumnString)) {
					String[] tc=tableColumnString.split("\\.");
					list.add(new DatabasePrivacyPolicyAddDto(
							tc[0],
							tc[1],
							YN.Y,
							"",
							databaseId));
				}
			}
		}
		databaseManager.addOrModifyDatabasePrivacyPolicyFasade(list);
		return new MessageVo(HttpServletResponse.SC_OK, 1, "Database 개인정보 식별 정보가 저장 되었습니다.");
	}


	@PutMapping("/modifyDatabasePrivacyPolicy")
	@ApiOperation(value = "데이터베이스 개인정보 식별  수정", response = DatabasePrivacyPolicyVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo modifyDatabasePrivacyPolicy(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabasePrivacyPolicyModifyDto dto) {
		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.modifyDatabasePrivacyPolicy(dto), "Database 개인정보 식별 정보가  저장 되었습니다.");
	}

	@DeleteMapping("/removeDatabasePrivacyPolicy")
	@ApiOperation(value = "데이터베이스 개인정보 식별  삭제", response = MessageVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo removeDatabasePrivacyPolicy(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=true) Long id) {
		databaseManager.removeDatabasePrivacyPolicy(id);
		return new MessageVo(HttpServletResponse.SC_OK , 1, "Database 개인정보 식별 정보가  삭제 되었습니다.");
	}

	@GetMapping("/findDatabasePrivacyPolicyList")
	@ApiOperation(value = "데이터베이스 개인정보 식별  조회", response = DatabasePrivacyPolicyVo.class)
	@Login({AuthType.ADMIN })
	public Page<DatabasePrivacyPolicyVo> findDatabasePrivacyPolicyList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabasePrivacyPolicyFindDto dto,
			@PageableDefault(page=0, size=500, direction=Direction.DESC, sort="id")
			Pageable page){
		return databaseManager.findDatabasePrivacyPolicyList(dto, page);
	}
}