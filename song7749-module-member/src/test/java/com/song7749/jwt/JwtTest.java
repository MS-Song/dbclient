package com.song7749.jwt;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthJWTVo;
import com.song7749.util.ObjectJsonUtil;
import com.song7749.util.crypto.CryptoAES;

@RunWith(SpringRunner.class)
public class JwtTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testJWTCreateAndRead() throws JOSEException, ParseException, JsonProcessingException {
		JWSSigner signer = new MACSigner(CryptoAES.key);

		LoginAuthJWTVo lav = new LoginAuthJWTVo(1L
				, "song7749@gmail.com"
				, "10.10.10.10"
				, AuthType.ADMIN
				, "song7749.com"
				, "JWT Auth"
				, "song7749@gmail.com"
				, new Date(System.currentTimeMillis() + 60*1000)
				, new Date(System.currentTimeMillis() - 60*1000)
				, new Date(System.currentTimeMillis())
				, "1");



		JWTClaimsSet claimsSet = JWTClaimsSet.parse(ObjectJsonUtil.getJsonStringByObject(lav));

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		signedJWT.sign(signer);
		String jwtString = signedJWT.serialize();
		logger.trace(format("{}", "JWT Signed Log"),jwtString);



		signedJWT = SignedJWT.parse(jwtString);

		// check payload and applied algorithm in header
		logger.trace(format("{}", "JWT Header Log"),signedJWT.getHeader().getAlgorithm());
		logger.trace(format("{}", "JWT Payload Log"),signedJWT.getPayload().toJSONObject());

		// verification
		JWSVerifier verifier = new MACVerifier(CryptoAES.key);
		assertThat(signedJWT.verify(verifier), is(true));
	}
}