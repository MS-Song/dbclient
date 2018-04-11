package com.song7749.dbclient.drs.aop;

import static com.song7749.util.LogMessageFormatter.format;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.StringJoiner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.song7749.util.validate.BaseGroup;
import com.song7749.util.validate.Validate;

@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=false)
public class ValidateJpaAop {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * validator 설정
	 */
	@Autowired
	protected Validator validatorFactoryBean;

    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void isRepository() {}

    @Pointcut("@annotation(com.song7749.util.validate.Validate)")
    public void hasValidateAnnotation() {}

	@Around("isRepository() || hasValidateAnnotation()")
	public Object validateJpaAop(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method invocation = signature.getMethod();

	    // has parameter
		if(null!=invocation.getParameters()
				&& invocation.getParameters().length>0){

			Validate validate=null;
			// interface has annotation
			if(invocation.isAnnotationPresent(Validate.class)){
				validate = invocation.getAnnotation(Validate.class);

				// validate group
				Class<? extends BaseGroup>[] baseList=validate.group();
				// property has
				String[] properties = validate.property();

				// start validate
				for(Object o:joinPoint.getArgs()){
					// parameter is not null and object us null
					if(validate.nullable() == false && o == null){
						// 파라메터 중에 null 이 있는 것으로 간주하고, null 입력에 대한 안내를 한다.
						StringJoiner joiner = new StringJoiner(",");
						for(Class<?> cl : signature.getParameterTypes()) {
							joiner.add(cl.getName());
						}

						throw new IllegalArgumentException(joiner.toString() + " must be not null");
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

					//logger.trace(format("{}", "Log Message"),cv);

					if(null!=cv && cv.size()>0){
						for(ConstraintViolation<?> c:cv){
							// 프록시 객체에서 발생한 에러를 건너뛴
							if(c.getRootBeanClass().getName().indexOf("_$$_javassist_")==-1){
								logger.trace(format("{} , 입력값 : {}","Validate Exception"),c.getPropertyPath() + " 은(는)(=) " + c.getMessage(),c.getInvalidValue());
								throw new IllegalArgumentException(c.getPropertyPath() + " 은(는)(=) " + c.getMessage());
							}
						}
					}
				}
			}
		}

		try {
			return joinPoint.proceed();
		} catch (javax.validation.ConstraintViolationException e) {
			throw new IllegalArgumentException(e.getConstraintViolations().iterator().next().getPropertyPath() + " 은(는)(=) " + e.getConstraintViolations().iterator().next().getMessage());
		}
	}
}