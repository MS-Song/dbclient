package com.song7749.app.dbclient.controller;
import static com.song7749.util.LogMessageFormatter.format;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.song7749.dl.login.service.LoginManager;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.service.MemberManager;
import com.song7749.dl.member.type.AuthType;
import com.song7749.dl.member.vo.MemberVO;

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

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberManager memberManager;

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

		// 관리자 여부
		boolean isAdmin=false;

		// 로그인 되어 있는가 검증
		if(loginManager.isLogin(request)){
			// 로그인 아이디 획득
			String id = loginManager.getLoginID(request);

			// 회원을 조회한다.
			FindMemberListDTO dto = new FindMemberListDTO(id);
			List<MemberVO> list = memberManager.findMemberList(dto );

			// 관리자 여부 확인
			if(null != list
					&& null!=list.get(0)
					&& null!=list.get(0).getAuthType()){

				if(list.get(0).getAuthType().equals(AuthType.ADMIN)){
					isAdmin=true;
				}
			}
		}
		model.addAttribute("isAdmin", isAdmin);
		logger.debug(format("{}","Is Admin logined?"),isAdmin);
		return "/index";
	}
}