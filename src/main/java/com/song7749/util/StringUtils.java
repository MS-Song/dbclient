package com.song7749.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}