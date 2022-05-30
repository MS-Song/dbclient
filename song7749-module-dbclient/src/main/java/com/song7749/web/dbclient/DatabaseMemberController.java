package com.song7749.web.dbclient;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.song7749.common.base.MessageVo;
import com.song7749.common.base.WebSocketMessageVo;
import com.song7749.common.exception.AuthorityUserException;
import com.song7749.dbclient.service.DBClientMemberManager;
import com.song7749.dbclient.value.MemberDatabaseAddOrModifyDto;
import com.song7749.dbclient.value.MemberDatabaseFindDto;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.dbclient.value.MemberSaveQueryAddDto;
import com.song7749.dbclient.value.MemberSaveQueryFindDto;
import com.song7749.dbclient.value.MemberSaveQueryRemoveDto;
import com.song7749.dbclient.value.MemberSaveQueryVo;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginManager;
import com.song7749.member.service.LoginSession;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




/**
 * <pre>
 * Class Name : DatabsaeMemberController.java
 * Description : DBCLient 내의 회원 정보 관리 컨트롤러
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 10.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 10.
*/

@Api(tags="DBClinet 회원 관리 기능")
@RestController
@RequestMapping("/member")
public class DatabaseMemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DBClientMemberManager dbClientMemberManager;

	@Autowired
	LoginManager loginManager;

	@Autowired
	LoginSession session;

	@Autowired
	private SimpMessagingTemplate template;

	@ApiOperation(value = "회원과 Database 간의 연결 추가/삭제 - 관리자"
			,notes = "회원과 Database 간의 연결을 추가 한다"
			,response=MessageVo.class)
	@PostMapping(value="/addOrModifyMemberDatabaseByAdmin")
	@Login(AuthType.ADMIN)
	public MessageVo addOrModifyMemberDatabaseByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberDatabaseAddOrModifyDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, dbClientMemberManager.addOrModifyMemberDatabase(dto), "정보 수정이 완료 되었습니다.");
	}

	@ApiOperation(value = "회원과 Database 간의 연결 조회"
			,notes = "회원과 Database 간의 연결을 조회"
			,response=MemberDatabaseVo.class)
	@GetMapping(value="/findMemberDatabase")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public Page<MemberDatabaseVo> FindMemberDatabase(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberDatabaseFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id") Pageable page){

		return dbClientMemberManager.findMemberDatabaseList(dto, page);
	}

	@ApiOperation(value = "회원의 쿼리 저장",notes = "자주 사용하는 쿼리를 저장하기 위한 컨트롤러"
			,response=MessageVo.class)
	@PostMapping(value="/addMemberSaveQuery")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo addMemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryAddDto dto) throws UnsupportedEncodingException{

		// 본인 확인
		if(session.getLogin().getId().equals(dto.getMemberId())){
			dto.setQuery(
					URLDecoder.decode(
						new String(
								Base64.getDecoder().decode(dto.getQuery())
							,Charset.forName("UTF-8"))
					, "UTF-8"));
			logger.debug(format("{}", "DECODE URL QUERY"),dto.getQuery());

			return new MessageVo(HttpStatus.OK.value()
					, 1
					, dbClientMemberManager.addMemberSaveQuery(dto)
					,"쿼리 데이터가 저장되었습니다.");
		} else {
			throw new AuthorityUserException("본인의 정보만 수정 가능 합니다.");
		}
	}

	@ApiOperation(value = "회원의 쿼리 삭제"
			,notes = "자주 사용하는 쿼리를 삭제하기 위한 컨트롤러"
			,response=MessageVo.class)
	@DeleteMapping(value="/removeMemberSaveQuery")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo removeMemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryRemoveDto dto){
		// 본인 확인
		dto.setMemberId(session.getLogin().getId());
		dbClientMemberManager.removeMemberSaveQuery(dto);
		return new MessageVo(HttpStatus.OK.value()
				, 1
				,"쿼리 데이터가 삭제 되었습니다.");
	}

	@ApiOperation(value = "회원의 저장된 쿼리 조회"
			,notes = "자주 사용하는 쿼리 리스트를 조회 한다."
			,response=MemberSaveQueryVo.class)
	@RequestMapping(value="/findMemberSaveQuery",method={RequestMethod.GET,RequestMethod.POST})
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public Page<MemberSaveQueryVo> MemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id") Pageable page){

		// 본인 확인
		if(session.getLogin().getId().equals(dto.getMemberId())){
			return dbClientMemberManager.findMemberSaveQueray(dto, page);
		} else {
			throw new AuthorityUserException("본인의 정보만 조회 가능 합니다.");
		}
	}


	@ApiOperation(value = "공지사항 메세지 전송"
			,notes = "시스템 공지 사항을 접속중인 모든 사용자에게 전송 한다."
			,response=MessageVo.class)
	@GetMapping("/sendNoticesMessage")
	public MessageVo sendNoticesMessage(HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam String buildNumber){

		WebSocketMessageVo sendMessage = new WebSocketMessageVo();
		sendMessage.setHttpStatus(HttpStatus.OK.value());
		sendMessage.setContents(new LoginAuthVo("root@test.com",AuthType.ADMIN));
		sendMessage.setMessage("Message to Jenkins System Deploy Work Running \n\n 시스템 반작업 : " + buildNumber+ " 번이 실행을 시작 하였습니다. \n반영 작업 주엥 약 1분간 시스템이 중지 되오니, 실행 중인 작업을 잠시 중단해 주시기 바랍니다.");
		sendMessage.setProcessTime(System.currentTimeMillis());
		template.convertAndSend("/topic/recieve", sendMessage);
		return new MessageVo(HttpStatus.OK.value(), "관리자 공지사항 전송 완료");
	}
}