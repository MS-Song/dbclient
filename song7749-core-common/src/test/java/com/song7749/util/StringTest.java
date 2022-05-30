package com.song7749.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class StringTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testSQLString() {
		String sql = "select 		APIKEY as key,\r\n" +
				"		AUTH_TYPE type,\r\n" +
				"		CREATE_DATE,\r\n" +
				"		LAST_LOGIN_DATE,\r\n" +
				"		LOGIN_ID,\r\n" +
				"		MOBILE_NUMBER,\r\n" +
				"		MODIFY_DATE,\r\n" +
				"		NAME,\r\n" +
				"		PASSWORD,\r\n" +
				"		PASSWORD_ANSWER,\r\n" +
				"		PASSWORD_QUESTION,\r\n" +
				"		TEAM_NAME from (\r\n" +
				"	select MEMBER_ID,\r\n" +
				"		APIKEY,\r\n" +
				"		AUTH_TYPE,\r\n" +
				"		CREATE_DATE,\r\n" +
				"		LAST_LOGIN_DATE,\r\n" +
				"		LOGIN_ID,\r\n" +
				"		MOBILE_NUMBER,\r\n" +
				"		MODIFY_DATE,\r\n" +
				"		NAME,\r\n" +
				"		PASSWORD,\r\n" +
				"		PASSWORD_ANSWER,\r\n" +
				"		PASSWORD_QUESTION,\r\n" +
				"		TEAM_NAME\r\n" +
				"	from MEMBER\r\n" +
				"  )";

		// SQL 을 분해 한다.
		List<String> selectList = new ArrayList<String>();
		int startSelectIndex=0;
		int endSelectIndex=0;
		while(true) {
			startSelectIndex= sql.toLowerCase().indexOf("select ",startSelectIndex);
			// select 구문이 있을 경우
			if(startSelectIndex>=0) {
				startSelectIndex=startSelectIndex+6;//select 문자열 만큼 더 뒤로 보낸다.
			}
			endSelectIndex 	= sql.toLowerCase().indexOf("from ",startSelectIndex);
			// from 문이 있을 경우
			if(endSelectIndex>=0) {
				endSelectIndex=endSelectIndex-1; // 첫 문자보다 -1 한다.
			} else {
				endSelectIndex=sql.length();
			}
			if(startSelectIndex>=0) {
				selectList.add(sql.substring(startSelectIndex,endSelectIndex));
			}
			if(startSelectIndex<0) break;
		}

		for(String sqlPart : selectList) {
			String[] part = sqlPart.replace("\r", "").replace("\n", "").split(",");
			for(String column : part) {
				logger.debug(column.trim());
			}
		}
	}
}
