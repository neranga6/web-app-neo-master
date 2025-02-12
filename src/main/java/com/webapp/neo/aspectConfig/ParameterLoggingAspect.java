package com.webapp.neo.aspectConfig;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ParameterLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ParameterLoggingAspect.class);

    @Pointcut("execution(* com.webapp.neo.serviceImpl..*(..))")
    private void serviceMethods() {}

    @Before("serviceMethods()")
    public void logMethodParameters(org.aspectj.lang.JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length == 0) {
            logger.info("No parameters passed to the method.");
            return;
        }

        String parameters = Arrays.stream(args)
                .map(Object::toString) // Convert each argument to a string
                .collect(Collectors.joining(", ", "Service Details: ", ""));

        logger.info(parameters);
    }
}