package com.song7749.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String replace(String before,String after, String target) {
		Pattern p = Pattern.compile(before);
		Matcher m = p.matcher(target);
		return m.replaceAll(after);
	}
}
