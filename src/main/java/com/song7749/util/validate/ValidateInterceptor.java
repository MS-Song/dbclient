package com.song7749.util.validate;

import static com.song7749.util.LogMessageFormatter.format;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.song7749.util.validate.annotation.Validate;

/**
 * <pre>
 * Class Name : ValidateFactoryBean.java .<br/>
 * Description :Validate Factory Bean .<br/>
 * Validate 를 자동화 하기 위한 Factory Bean 클래스 .<br/>
 *
 *  ex) 메소드에 아래와 같은 Validate 을 사용하면 Validate 를 자동으로 처리해 준다 .<br/>
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 3. 24.		song7749	AP-18
 *
 * </pre>
 *
 * @author song7749
 * @param <T>
 * @since 2014. 3. 24.
 */

public class ValidateInterceptor<T> implements MethodInterceptor{
	/**
	 * logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 *  validate Factory Bean application context DI
	 */
	protected Validator validatorFactoryBean;

	public void setValidatorFactoryBean(LocalValidatorFactoryBean validatorFactoryBean){
		this.validatorFactoryBean=validatorFactoryBean;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// validate write logger
		if(logger.isTraceEnabled()){
			String[] log = {
					invocation.getMethod().toString()
					,String.valueOf(invocation.getArguments().length)
			};
			logger.trace(format("mehtod : {}\nparamsize:{}", "Validate AOP"),log);
		}

		// has parameter
		if(null!=invocation.getArguments()
				&& invocation.getArguments().length>0){

			Validate validate=null;
			// interface has annotation
			if(invocation.getMethod().isAnnotationPresent(Validate.class)){
				validate = invocation.getMethod().getAnnotation(Validate.class);

			} else{// has instance
				Method method = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
				validate = method.getAnnotation(Validate.class);
			}
			if(null != validate){

				// validate group
				Class<? extends ValidateGroupBase>[] baseList=validate.VG();
				// property has
				String[] properties = validate.property();

				// start validate
				for(Object o:invocation.getArguments()){
					// parameter is not null and object us null
					if(validate.nullable() == false && o == null){
						logger.debug(format("Throw New IllegalArgumentException"));
						throw new IllegalArgumentException(" parameter is not null");
					}

					Set<ConstraintViolation<Object>> cv= null;

					// has property
					if(null!=properties && properties.length>0){

						Set<ConstraintViolation<Object>> propertySet=null;

						for(String property : properties){
							// 프로퍼티 값이 존재하면..
							if(null!=property && ""!=property){
								if(null==baseList){
									propertySet=validatorFactoryBean.validateProperty(o, property);
								} else{
									propertySet=validatorFactoryBean.validateProperty(o, property, baseList);
								}
							}
							// validate 결과가 존재하면..
							if(null!=propertySet
									&& propertySet.size()>0){
								if(cv==null){
									cv=propertySet;
								} else{
									cv.addAll(propertySet);
								}
							}
						}
					} else{ // not has property
						if(null==baseList){
							cv=validatorFactoryBean.validate(o);
						} else {
							cv=validatorFactoryBean.validate(o,baseList);
						}
					}
					if(null!=cv && cv.size()>0){
						for(ConstraintViolation<?> c:cv){
							// 프록시 객체에서 발생한 에러를 건너뛴
							if(c.getRootBeanClass().getName().indexOf("_$$_javassist_")==-1){
								logger.debug(format("Throw New IllegalArgumentException"));
								throw new IllegalArgumentException(c.getPropertyPath() + " 은(는) " + c.getMessage());
							}
						}
					}
				}
			}
		}
		try {
			return invocation.proceed();
		} catch (javax.validation.ConstraintViolationException e) {
			throw new IllegalArgumentException(e.getConstraintViolations().iterator().next().getPropertyPath() + " 은(는) " + e.getConstraintViolations().iterator().next().getMessage());
		}
	}
}
