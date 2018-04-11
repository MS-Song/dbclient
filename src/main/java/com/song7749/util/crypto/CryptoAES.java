package com.song7749.util.crypto;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Class Name : CryptoAES.java
 * Description : AES 암호화 복호화 모듈
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 5. 12.		song7749	신규작성
 *  2017. 11. 10 	song7749	java.util 의 Base64로 변경
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 5. 12.
 */
public class CryptoAES {

	static Logger logger = LoggerFactory.getLogger(CryptoAES.class);

	public static final String key="qeiuwhrsdkjfbxcbviuhqeorghfcv=-0asdkljzjxcveijaadlkjsfzcx,m";

	/**
	 * 복호화
	 * @param text 암호화 텍스트
	 * @return String  복호화된 문자열
	 * @throws Exception
	 */
	public static String decrypt(String text){
		try {
			return decrypt(text, key);
		} catch (Exception e) {
			logger.error(format("{}","CryptoAES.decrypt"),e.getMessage());
		}
		return text;
	}

	/**
	 * 복호화
	 * @param text 암호화 텍스트
	 * @param key 키
	 * @return String 복호화된 문자열
	 * @throws Exception
	 */
	public static String decrypt(String text, String key) throws Exception {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		Decoder d = Base64.getDecoder();
		byte[] results = cipher.doFinal(d.decode(text));
		return new String(results, "UTF-8");
	}

	/**
	 * 암호화
	 * @param text 암호화 대상 문자열
	 * @return String 암호화 된 문자열
	 * @throws Exception
	 */
	public static String encrypt(String text){
		try {
			return encrypt(text,key);
		} catch (Exception e) {
			logger.error(format("{}","CryptoAES.encrypt"),e.getMessage());
		}
		return text;
	}

	/**
	 * 암호화
	 * @param text 암호화 대상 문자열
	 * @param key 키
	 * @return String 암호화된 문자열
	 * @throws Exception
	 */
	public static String encrypt(String text, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		Encoder en = Base64.getEncoder();
		return en.encodeToString(results);
	}
}