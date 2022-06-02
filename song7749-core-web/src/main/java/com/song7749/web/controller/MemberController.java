package com.song7749.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.song7749.common.base.MessageVo;
import com.song7749.common.exception.AuthorityUserException;
import com.song7749.member.annotation.Login;
import com.song7749.member.service.LoginManager;
import com.song7749.member.service.LoginSession;
import com.song7749.member.service.MemberManager;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberAddDto;
import com.song7749.member.value.MemberFindDto;
import com.song7749.member.value.MemberModifyByAdminDto;
import com.song7749.member.value.MemberModifyDto;
import com.song7749.member.value.MemberVo;
import com.song7749.member.value.RenewApikeyDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




/**
 * <pre>
 * Class Name : MemberController.java
 * Description : 회원 정보 관리 컨트롤러
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

@Api(tags="회원 관리 기능")
@RestController
@RequestMapping("/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@Autowired
	LoginManager loginManager;

	@Autowired
	LoginSession session;

	@ApiOperation(value = "회원가입"
			,notes = "회원 정보를 등록한다. 회원 가입 시에 권한 정보는 추후 승인하는 형태로 진행 한다."
			,response=MessageVo.class)
	@PostMapping("/add")
	public MessageVo addMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberAddDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, memberManager.addMemeber(dto), "회원 등록이 완료되었습니다.");
	}

	@ApiOperation(value = "회원수정"
			,notes = "회원 정보를 수정한다. 본인의 정보만 수정 가능하다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@PutMapping("/modify")
	public MessageVo modifyMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberModifyDto dto){
		// 본인확인
		if(session.getLogin().getId().equals(dto.getId())){
			return new MessageVo(HttpStatus.OK.value(), 1
					, memberManager.modifyMember(dto), "정보 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인의 정보만 수정 가능 합니다.");
		}

	}

	@ApiOperation(value = "회원 수정 - 관리자"
			,notes = "회원 정보를 수정한다. 관리자가 수정한다."
			,response=MessageVo.class)
	@Login(AuthType.ADMIN)
	@PutMapping("/modifyByAdmin")
	public MessageVo modifyMemberByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberModifyByAdminDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, memberManager.modifyMember(dto), "정보 수정이 완료 되었습니다.");
	}

	@ApiOperation(value = "회원 삭제"
			, notes = "회원 정보를 삭제한다."
			, response=MessageVo.class)
	@Login(AuthType.ADMIN)
	@DeleteMapping("/remove")
	public MessageVo removeMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) Long id){

		memberManager.removeMember(id);
		return new MessageVo(HttpStatus.OK.value(), 1, "회원이 삭제 되었습니다.");
	}

	@ApiOperation(value = "회원 조회"
			,notes = "회원 리스트를 조회 한다.<br/> 일반회원인 경우에는 개인정보가 제외된다."
			,response=MemberVo.class)
	@Login({AuthType.NORMAL, AuthType.ADMIN})
	@GetMapping("/list")
	public Page<MemberVo> listMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberFindDto dto,
			@PageableDefault(page=0, size=20, direction=Direction.DESC, sort="id") Pageable page){

		return memberManager.findMemberList(dto,page);
	}

	@ApiOperation(value = "회원 권한 목록 조회"
			,notes = "회원 권한 목록을 조회 한다"
			,response=AuthType.class)
	@GetMapping("/getAuthTypes")
	@Login({AuthType.NORMAL, AuthType.ADMIN})
	public AuthType[] getAuthTypes(
			HttpServletRequest request,
			HttpServletResponse response){
		return AuthType.values();
	}

	@ApiOperation(value = "API Key 관리"
			,notes = "회원의 API Key 를 발급 하거나 갱신한다."
			,response=MessageVo.class)
	@PutMapping("/renewApiKey")
	public MessageVo renewApiKey(
			HttpServletRequest request,
			HttpServletResponse response,
			RenewApikeyDto dto){
		return new MessageVo(HttpStatus.OK.value(),
				memberManager.renewApikey(dto), "apikey가 갱신 되었습니다.");
	}

	@ApiOperation(value = "API Key 관리 - 관리자"
			,notes = "회원의 API Key 를 발급 하거나 갱신한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PutMapping("/renewApiKeyByAdmin")
	public MessageVo renewApiKey(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) String loginId){

		return new MessageVo(HttpStatus.OK.value(),
				memberManager.renewApikeyByAdmin(loginId), "apikey가 갱신 되었습니다.");
	}

	@ApiOperation(value = "패스워드 메일 전송"
			,notes = "회원의 패스워드를 재 생성하여 메일로 전송해 준다."
			,response=MessageVo.class)
	@GetMapping("/sendPassword")
	public MessageVo sendPassword(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam String loginId){

		return memberManager.sendPassword(loginId);
	}
}