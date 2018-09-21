package com.aakkus.aspect;

import com.aakkus.annotation.Validate;
import com.aakkus.validation.Validation;
import com.aakkus.validation.exception.GuardianValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class GuardianValidationAspect {

    private final ApplicationContext applicationContext;

    public GuardianValidationAspect(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Around("@annotation(com.aakkus.annotation.Validate)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Validate validate = retrieveValidateAnnotation(joinPoint);
            Validation validation = (Validation) applicationContext.getBean(validate.validatorClass());

            Object arg = joinPoint.getArgs()[0];
            validation.validate(arg);

            return joinPoint.proceed();

        } catch (Exception e) {
            throw new GuardianValidationException(e);
        }
    }

    private Validate retrieveValidateAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();

        return method.getDeclaredAnnotation(Validate.class);
    }
}