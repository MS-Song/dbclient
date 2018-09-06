package com.song7749.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Class Name : StringUtils.java
 * Description : String Utils
 * org.apache.commons.lang.StringUtils 에서 제공하지 않는 기능을 확장에서 사용
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 5. 8.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 5. 8.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	/**
	 * String replace
	 * @param before
	 * @param after
	 * @param target
	 * @return String
	 */
	public static String replacePatten(String before, String after, String target) {
		if(!isEmpty(before)){
			Pattern p = Pattern.compile(before);
			Matcher m = p.matcher(target);
			return m.replaceAll(after);
		}
		return null;
	}

	/**
	 * html 테그를 그대로 출력하기 위해 변환
	 *
	 * @param content
	 * @return String
	 */
	public static String htmlentities(String content) {
		if (!isEmpty(content)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < content.length(); i++) {
				char c = content.charAt(i);
				switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\'':
					sb.append("&apos;");
					break;
				default:
					sb.append(c);
				}
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 주어진 스트링 내에 match 하는 단어가 일치하는가 검증 한다.
	 * @param give  주어진 단어
	 * @param match 매치할 단어
	 * @param startIndex 검색 시작 시점
	 * @param beforeWordList 앞에 예외를 허용해야 하는 word
	 * @param afterWordList 뒤에 예외를 허용해야하는 word
	 * @return boolean
	 */
	public static boolean isSubStringEqualMatch(String give, String match, Integer startIndex, List<String> beforeWordList, List<String> afterWordList) {
		return getSubStringEqualMatchIndex(give, match, startIndex, beforeWordList, afterWordList) >= 0;
	}

	/**
	 * 주어진 스트링 내에 match 하는 단어가 일치하는가 검증 한다.
	 * @param give  주어진 단어
	 * @param match 매치할 단어
	 * @param startIndex 검색 시작 시점
	 * @param beforeWordList 앞에 예외를 허용해야 하는 word
	 * @param afterWordList 뒤에 예외를 허용해야하는 word
	 * @return miss match return -1, match is return index
	 */
	public static int getSubStringEqualMatchIndex(String give, String match, Integer startIndex, List<String> beforeWordList, List<String> afterWordList) {
		boolean isMatch = false;
		int findIndex = 0;
		while(true) {
//			if(logger.isTraceEnabled()) {
//				Object[] o = {isMatch,findIndex,startIndex,give.length()};
//				logger.trace(format("isMatch : {}\nfindIndex:{}\nstartIndex:{}\ngive.length():{}", "getSubStringEqualMatchIndex Message"),o);
//			}
			// 일치 단어를 찾았거나, 단어가 없거나, offset 이 give 의 범위를 초과하거나 하면 멈춘다.
			if(isMatch || findIndex < 0 ||  startIndex  >= give.length()) {
				break;
			}
			// 단어의 index 를 찾는다.
			findIndex = give.toLowerCase().indexOf(match,startIndex);
//			if(logger.isTraceEnabled()) {
//				Object[] o = {match,findIndex,startIndex,give.length()};
//				logger.trace(format("match : {}\nfindIndex:{}\nstartIndex:{}\ngive.length():{}", "getSubStringEqualMatchIndex Message"),o);
//			}

			// 검색해야 하는 단어가 있는 경우
			if(findIndex > 0) { // 앞 단어 검색
				String beforeWord = give.substring(findIndex-1, findIndex);
				for(String s : beforeWordList) {
					if(s.equals(beforeWord)) {
						isMatch=true;
						break;
					}
				}
			} else if(findIndex==0){
				isMatch=true;
			}

			// 앞 단어가 일치하는 경우 뒷 단어도 검색
			if(isMatch) {
//				if(logger.isTraceEnabled()) {
//					Object[] o = {match,findIndex,startIndex,give.length()};
//					logger.trace(format("match : {}\nfindIndex:{}\nstartIndex:{}\ngive.length():{}", "getSubStringEqualMatchIndex Message"),o);
//				}

				// 뒷단어 검증을 위해 리셋
				isMatch=false;
				// give 의 끝이 아닌 경우
				if(give.length() > findIndex + match.length()+1) {
					String afterWord =  give.substring(findIndex + match.length(), findIndex + match.length() + 1);
					for(String s : afterWordList) {
						if(s.equals(afterWord)) {
							isMatch=true;
							break;
						}
					}
				} else { // give 의 끝..
					isMatch=true;
				}
			}
			// 찾을 인덱스를 뒤로 보낸다.
			startIndex = findIndex + match.length();
		}
		return findIndex;
	}
}