package com.song7749.util.web;

import java.util.Map;


public class WebForm {

	/**
	 * <select> 를 생성한다.
	 * @param map 의 key 는 option text, value 는 option value 로 설정된다. 순서가 중요하다면 LinkedHashMap 을 사용하라
	 * @param name 은 select name 속성
	 * @param selectedValue 옵션의  value 와 매치되는 선택된 값
	 * @param firsetOpetionValue --선택하세요-- 와 같이 별도의 값을 추가하고자 할때
	 * @param selectAppend select 테그 내에 onchange 등과 같은 액션 또는 css 를 추가하고자 할 때
	 * @param optionAppend option 테그 내에 onchange 등과 같은 액션 또는 css 를 추가하고자 할 때
	 * @return
	 */
	public static String select(Map<String,String> map,
			String name,
			String selectedValue,
			String firsetOpetionValue,
			String selectAppend,
			String optionAppend){

		StringBuffer sb= new StringBuffer();

		sb.append("<select name='"+name+"' " + selectAppend + " title='선택하세요'>\n");

		if(null!=firsetOpetionValue)
			sb.append("<option value=''>"+firsetOpetionValue+"</option>\n");
		for(String key: map.keySet()){
			sb.append("<option value='"+map.get(key)+"' ");
			if(selectedValue.equals(map.get(key))){
				sb.append(" selected ");
			}
			sb.append(optionAppend+">");
			sb.append(key+"</option>\n");
		}
		sb.append("</select>\n");
		return sb.toString();
	}
}