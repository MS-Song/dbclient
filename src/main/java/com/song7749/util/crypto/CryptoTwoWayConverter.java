package com.song7749.util.crypto;

import static com.song7749.util.LogMessageFormatter.format;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Class Name : CryptoTwoWayConverter.java
 * Description : Database에 저장되는 내용을 양방향 암호화 하여 Convert 한다.
 * Two-way encrypted
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
public class CryptoTwoWayConverter implements AttributeConverter<String,String> {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
			return CryptoAES.encrypt(attribute);
		} catch (Exception e) {
			logger.error(format("{}","CryptoTwoWayConverter.convertToDatabaseColumn"),e.getMessage());
		}
		return attribute;
	}


	@Override
	public String convertToEntityAttribute(String dbData) {
		try {
			return CryptoAES.decrypt(dbData);
		} catch (Exception e) {
			logger.error(format("{}","CryptoTwoWayConverter.convertToEntityAttribute"),e.getMessage());
		}
		return dbData;
	}
}