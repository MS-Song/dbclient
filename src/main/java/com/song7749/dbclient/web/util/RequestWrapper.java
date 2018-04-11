package com.song7749.dbclient.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

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
		for(int i=0; i<values.length; i++){
			// parameter is not null as clean
			if(StringUtils.isBlank(values[i])){
				values[i]= cleanXSS(values[i]);
			}
		}
		return values;
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
//		value = value.replaceAll("\\(", "& #40").replaceAll("\\(", "& #41");
//		value = value.replaceAll("'", "& #39");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}