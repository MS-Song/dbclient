package com.song7749.member.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.song7749.member.type.AuthType;
import com.song7749.member.type.LoginResponseType;

/**
 * <pre>
 * Class Name : Login.java
 * Description : 로그인 인터셉터 처리를 위한 로그인 annotation
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

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
	AuthType[] value() default AuthType.NORMAL;
	LoginResponseType type() default LoginResponseType.EXCEPTION;
}