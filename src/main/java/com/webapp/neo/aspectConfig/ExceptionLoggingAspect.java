package com.webapp.neo.aspectConfig;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @Pointcut("execution(* com.webapp.neo.serviceImpl..*(..))")
    private void serviceMethods() {}

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Exception occurred: " + exception.getMessage(), exception);
    }

}