package com.song7749.log.convert;

import java.util.ArrayList;
import java.util.List;

import com.song7749.log.dto.SaveMemberLoginLogDTO;
import com.song7749.log.entities.MemberLoginLog;
import com.song7749.log.vo.MemberLoginLogVO;
/**
 * <pre>
 * Class Name : LogConvert.java
 * Description : 로그 객체 컨버터
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public class MemberLoginLogConvert {

	public static MemberLoginLog convert(SaveMemberLoginLogDTO dto){
		if(null==dto){
			return null;
		}
		return new MemberLoginLog(
				dto.getId(),
				dto.getIp(),
				dto.getCipher(),
				dto.getLoginDate());
	}

	public static MemberLoginLogVO convert(MemberLoginLog memberLoginLog){
		if(null==memberLoginLog){
			return null;
		}
		return new MemberLoginLogVO(
				memberLoginLog.getMemberLoginLogSeq(),
				memberLoginLog.getId(),
				memberLoginLog.getIp(),
				memberLoginLog.getCipher(),
				memberLoginLog.getLoginDate());
	}

	public static List<MemberLoginLogVO> convert(List<MemberLoginLog> list){
		if(null==list){
			return null;
		}
		List<MemberLoginLogVO> rList = new ArrayList<MemberLoginLogVO>();
		for(MemberLoginLog mll:list){
			rList.add(convert(mll));
		}
		return rList;
	}
}