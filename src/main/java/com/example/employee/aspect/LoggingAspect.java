package com.example.employee.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect 
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.employee.controller.EmployeeRestController.*(..))")
    public void employeeControllerMethods() {}

    @Before("employeeControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("👉 [REQUEST] Method: {}() | Args: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "employeeControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("✅ [RESPONSE] Method: {}() | Result: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "employeeControllerMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("❌ [ERROR] Method: {}() | Exception: {}", methodName, exception.getMessage());
    }
}
