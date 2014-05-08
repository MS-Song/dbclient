package com.song7749.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String replace(String before, String after, String target) {
		Pattern p = Pattern.compile(before);
		Matcher m = p.matcher(target);
		return m.replaceAll(after);
	}

	/**
	 * html 테그를 그대로 출력하기 위해 변환
	 * @param content
	 * @return String
	 */
	public static String htmlentities(String content) {

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
}