package com.song7749.util.crypto;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoAESTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	String text = "패스워드입니다.";
	String key = CryptoAES.key;

	@Test
	public void testDecryptEncrypt() throws Exception {
		String before = CryptoAES.encrypt(text, key);
		String after = CryptoAES.decrypt(before, key);
		assertThat(text, is(after));
	}
}