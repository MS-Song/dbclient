package com.song7749.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * Class Name : ObjectJosinUtil.java
 * Description : 객체를 json 혹은 Json 을 객체로 변경
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 9.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 9.
*/
public class ObjectJsonUtil {

	public static String getJsonStringByObject(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}

	public static Object getObjectByJsonString(String json,Class<?> objectClass) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, objectClass);
	}
}