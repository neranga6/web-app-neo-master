package com.webapp.neo.aspectConfig;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingControllerAspect.class);

    @Pointcut("execution(* com.webapp.neo.controller..*(..))")
    private void controllerMethods() {}

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Exception occurred: {}", exception.getMessage(), exception);
    }

}