package com.song7749.util.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;


public class WebFormTest {

	@Test
	public void testSelect() throws Exception {
		// give
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("20개 씩 선택","20");
		map.put("50개 씩 선택","50");
		map.put("100개 씩 선택","100");
		map.put("200개 씩 선택","200");

		String name="limit";

		String selectedValue="20";
		String firsetOpetionValue = "--선택하세요--";
		String selectAppend = "onclick=''";
		String optionAppend = "onclick=''";;

		// when
		String output = WebForm.select(map, name, selectedValue, firsetOpetionValue, selectAppend, optionAppend);
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='limit' onclick='' title='선택하세요'>\n");
		sb.append("<option value=''>--선택하세요--</option>\n");
		sb.append("<option value='20'  selected onclick=''>20개 씩 선택</option>\n");
		sb.append("<option value='50' onclick=''>50개 씩 선택</option>\n");
		sb.append("<option value='100' onclick=''>100개 씩 선택</option>\n");
		sb.append("<option value='200' onclick=''>200개 씩 선택</option>\n");
		sb.append("</select>\n");
		// then
		assertThat(output, is(sb.toString()));
	}

}
