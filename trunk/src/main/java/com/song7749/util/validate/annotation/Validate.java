package com.song7749.util.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.song7749.util.validate.ValidateGroupBase;

/**
 * <pre>
 * Class Name : Validate.java
 * Description : Validate
 * Spring Validate 를 이용하여, 특정한 메소드에 자동으로 Validate가 되도록 처리한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 3. 24.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 3. 24.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
	/**
	 * 파라메터에 null 허용 여부
	 * @return boolean
	 */
	boolean nullable() default false;
	/**
	 * validate group
	 * @return DanawaValidateGroup
	 */
	Class<? extends ValidateGroupBase>[] VG() default {};

	/**
	 * param 내의 특정한 필드만 valiate 하고 싶은 경우 사용.<br/>
	 * 해당 필드는 반드시 Validate Annotation 이 달려 있어야 한다.<br/>
	 * ex) @notNull 등.<br/>
	 * @return String[]
	 */
	String[] property() default {};
}