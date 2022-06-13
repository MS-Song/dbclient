package com.song7749.util.crypto;

import static com.song7749.util.LogMessageFormatter.format;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * Class Name : CryptoOneWayConverter.java
 * Description : 단방양 암호화 converter
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 26.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 26.
*/
public class CryptoOneWayConverter implements AttributeConverter<String,String> {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
			return CryptoAES.encrypt(attribute);
		} catch (Exception e) {
			logger.error(format("{}","CryptoOneWayConverter.convertToDatabaseColumn"),e.getMessage());
		}
		return attribute;
	}


	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}
}