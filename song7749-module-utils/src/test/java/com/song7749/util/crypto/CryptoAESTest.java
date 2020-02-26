package com.song7749.util.crypto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.song7749.util.LogMessageFormatter.format;

@ExtendWith(SpringExtension.class)
public class CryptoAESTest {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testCrypto() {
		String encrypt = CryptoAES.encrypt("oracle");
		String decrypt = CryptoAES.decrypt(encrypt);
		logger.debug(format("before : {} , after : {} ", "CryptoAESTest"),encrypt,decrypt);
	}
}
