package com.inghub.sems.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.inghub.sems.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.inghub.sems.service.*.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.inghub.sems.service.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in method: {} with cause: {}", joinPoint.getSignature().toShortString(), error.getMessage());
    }

    // Pointcut for controller methods
    @Before("execution(* com.inghub.sems.controller.*.*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        log.info("Entering controller method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.inghub.sems.controller.*.*(..))", returning = "result")
    public void logAfterControllerMethods(JoinPoint joinPoint, Object result) {
        log.info("Exiting controller method: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.inghub.sems.controller.*.*(..))", throwing = "error")
    public void logAfterThrowingControllerMethods(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in controller method: {} with cause: {}", joinPoint.getSignature().toShortString(), error.getMessage());
    }
}