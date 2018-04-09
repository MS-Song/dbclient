package com.song7749.dl.config;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberByAdminDTO;
import com.song7749.dl.member.service.MemberManager;
import com.song7749.dl.member.type.AuthType;
import com.song7749.dl.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : InitConfigBean.java
 * Description : App 실행 시 사전에 처리해야 하는 작업들을 정의 함.
 *
 * 1. Root 유저 등록
 * 2. Local - H2 DB 에 대한 내용 확인
 * 3. 테스트 Data 입력 -- 서비스 배포시에는 삭제 필요
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 29.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 29.
*/

public class InitConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

    public void init(){
		FindMemberListDTO dto = new FindMemberListDTO("root");
		List<MemberVO> list = memberManager.findMemberList(dto );
		// root 회원이 없다.
		if(null==list || list.size() == 0) {
			AddMemberDTO add = new AddMemberDTO(
					"root",
					"12345678",
					"root@test.com",
					"Password Question?",
					"Password Answer?");
			memberManager.addMember(add);
			logger.info(format("{}", "Install Root user"),add);

			ModifyMemberByAdminDTO admin = new ModifyMemberByAdminDTO();
			admin.setAuthType(AuthType.ADMIN);
			admin.setId(add.getId());
			memberManager.modifyMember(admin);
		}
    }
}
