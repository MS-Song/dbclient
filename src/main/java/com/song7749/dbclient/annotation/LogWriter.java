package com.song7749.dbclient.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.song7749.dbclient.type.LogType;

/**
 * <pre>
 * Class Name : LogWriter.java
 * Description : 여러 타입의 로그를 자동으로 기록해 주는 Logwriter Annotation
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 28.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 28.
*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogWriter {

	/**
	 * Log Type
	 * @return LogType[]
	 */
	LogType value();

	/**
	 * true : log write async
	 * false : log write sync
	 * @return boolean
	 */
	boolean async()	default true;
}
