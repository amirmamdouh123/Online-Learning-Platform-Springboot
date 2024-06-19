package com.learning.OnlineLearning.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

@Component
@Aspect
public class ServiceMonitor {

    Logger logger=Logger.getLogger("Aspect");
    @Around("execution(* com.learning.OnlineLearning.Services..*(..))")
    public Object aroundAllServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date beforeExecution =new Date(System.currentTimeMillis());

        Object result =proceedingJoinPoint.proceed();

        Date afterExecution = new Date(System.currentTimeMillis());

        Long x= afterExecution.getTime()-beforeExecution.getTime();
        logger.info("the "+
                proceedingJoinPoint.getSignature().getName()+
                " Service is executed with argument "+
                Arrays.toString(proceedingJoinPoint.getArgs()) +
                " and took " +
                x +
                "ms. time"
        );
        return result;
    }
}
