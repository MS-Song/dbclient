package com.song7749.util.validate;

import static com.song7749.util.LogMessageFormatter.logFormat;

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
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected Validator validatorFactoryBean;

	public void setValidatorFactoryBean(LocalValidatorFactoryBean validatorFactoryBean){
		this.validatorFactoryBean=validatorFactoryBean;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		logger.trace(logFormat(invocation.getMethod().toString(), "Validate AOP START"));


		// 파라메터가 존재하는 경우
		if(null!=invocation.getArguments() && invocation.getArguments().length>0){
			logger.trace(logFormat("find params , size : {}", "Validate AOP"),invocation.getArguments().length);


			Validate validate=null;
			// 인터페이스에 annotation 이 없는 경우 직접 객체에서 찾아야 한다.
			if(invocation.getMethod().isAnnotationPresent(Validate.class)){
				validate = invocation.getMethod().getAnnotation(Validate.class);
			} else{// 해당 인스턴스에서 찾아내야 한다.
				Method method = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
				validate = method.getAnnotation(Validate.class);
			}

			// 파라메터에 null 이 허용되는지..
			boolean nullAble = false;
			// group list 조회
			Class<? extends ValidateGroupBase>[] baseList=null;
			String[] properties = null;

			if(null != validate){
				// 그룹이 존재할 경우 그룹값일 추가
				baseList=validate.VG();
				// 파라메터에  null 이 허용되는지 확인
				nullAble=validate.nullable();
				// 필드 프로퍼티가 들어 있는 경우
				properties=validate.property();
			}
			// 파라메터 검증
			for(Object o:invocation.getArguments()){
				// 파라메터에 null 이 허용되지 않는데, 파라메터가 null 인 경우에는
				if(nullAble==false
						&& null==o){
					throw new IllegalArgumentException("파라메터는  null 이 아니어야 합니다.");
				}

				Set<ConstraintViolation<Object>> i= null;
				// 프로퍼티 설정이 있는 경우
				if(null!=properties
						&& properties.length>0){
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
							if(i==null){
								i=propertySet;
							} else{
								i.addAll(propertySet);
							}
						}
					}
				} else{ // 프로퍼티 설정이 없는 경우
					if(null==baseList){
						i=validatorFactoryBean.validate(o);
					} else {
						i=validatorFactoryBean.validate(o,baseList);
					}
				}
				if(null!=i && i.size()>0){
					// TODO 더 좋은 방법이 있으면 고친다.
					for(ConstraintViolation c:i){
						// 프록시 객체에서 발생한 에러를 건너뛴
						if(c.getRootBeanClass().getName().indexOf("_$$_javassist_")==-1){
							throw new IllegalArgumentException(c.getPropertyPath() + " 은(는) " + c.getMessage());
						}
					}
				}
			}
		}
		logger.trace(logFormat(invocation.getMethod().toString(), "Validate AOP END"));

		try {
			return invocation.proceed();
		} catch (javax.validation.ConstraintViolationException e) {
			throw new IllegalArgumentException(e.getConstraintViolations().iterator().next().getPropertyPath() + " 은(는) " + e.getConstraintViolations().iterator().next().getMessage());
		}
	}
}