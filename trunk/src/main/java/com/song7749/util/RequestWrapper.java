package com.song7749.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {
	public RequestWrapper(HttpServletRequest servletRequest){
		super(servletRequest);
	}
	@Override
	public String[] getParameterValues(String parameter){
		String[] values = super.getParameterValues(parameter);
		if( values == null ){
			return null;
		}

		int count = values.length;
		List<String> valueList=new ArrayList<String>();
		for(int i=0; i<count; i++){
			// 파라메터 배열에 값이 있는 경우에만 필터를 적용한다.
			if(!(values[i].equals(null) || values[i].equals(""))){
				valueList.add(cleanXSS(values[i]));
			} else {
				valueList.add(values[i]);
			}
		}

		String[] encodedValues = new String[valueList.size()];
		for(int j=0;j<valueList.size();j++){
			encodedValues[j] = valueList.get(j);
		}
		valueList.remove(valueList);

		return encodedValues;
	}

	@Override
	public String getParameter(String parameter){
		String value = super.getParameter(parameter);
		if( value == null ){
			return null;
		}
		return this.cleanXSS(value);
	}

	@Override
	public String getHeader(String name){
		String value = super.getHeader(name);
		if( value == null ){
			return null;
		}
		return this.cleanXSS(value);
	}

	/**
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value){
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40").replaceAll("\\(", "& #41");
		value = value.replaceAll("'", "& #39");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}