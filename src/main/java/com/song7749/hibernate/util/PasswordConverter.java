package com.song7749.hibernate.util;

import static com.song7749.util.LogMessageFormatter.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.util.crypto.CryptoAES;

/**
 * <pre>
 * Class Name : PasswordConverter.java
 * Description : Database에 저장되는 Password 를 Convert 한다.
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 5. 12.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 5. 12.
*/
public class PasswordConverter{ //implements AttributeConverter<String,String> {

	Logger logger = LoggerFactory.getLogger(getClass());


	public String convertToDatabaseColumn(String attribute) {
		try {
			return CryptoAES.encrypt(attribute, CryptoAES.key);
		} catch (Exception e) {
			logger.error(format("{}","PasswordConverter.convertToDatabaseColumn"),e.getMessage());
		}
		return attribute;
	}


	public String convertToEntityAttribute(String dbData) {
		try {
			return CryptoAES.decrypt(dbData, CryptoAES.key);
		} catch (Exception e) {
			logger.error(format("{}","PasswordConverter.convertToEntityAttribute"),e.getMessage());
		}
		return dbData;
	}
}
