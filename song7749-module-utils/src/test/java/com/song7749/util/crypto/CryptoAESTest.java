package com.song7749.util.crypto;

import static com.song7749.util.LogMessageFormatter.format;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoAESTest {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testCrypto() {
		String encrypt = CryptoAES.encrypt("oracle");
		String decrypt = CryptoAES.decrypt(encrypt);
		logger.debug(format("before : {} , after : {} ", "CryptoAESTest"),encrypt,decrypt);
	}
}
