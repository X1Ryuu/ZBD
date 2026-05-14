package com.example.zbd;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class DatabaseQueryMonitor {

    private static final Logger sysLog = LoggerFactory.getLogger("syslogs");
    private static final Logger dbQualityLog = LoggerFactory.getLogger("databaselogs");

    private static final long SLOW_QUERY_THRESHOLD = 100;

    @Around("execution(* com.example.zbd.repositories.*.*(..))")
    public Object logDatabaseMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        String returnType = "Unknown";

        sysLog.info("[SYSTEM] Attempting to execute repository method: {}", methodName);

        Object proceed;
        try {
            proceed = joinPoint.proceed();
            if (proceed != null) {
                returnType = proceed.getClass().getSimpleName();
            }
        } catch (Exception e) {
            sysLog.error("[SYSTEM ERROR] Method failure: {}", methodName);
            dbQualityLog.error("[DATABASE ERROR] Method: {} | Args: {} | Exception: {}",
                    methodName, args, e.getMessage());
            throw e;
        }

        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > SLOW_QUERY_THRESHOLD) {
            dbQualityLog.warn("[PERFORMANCE - LOW] Method: {} | Execution Time: {}ms | Args: {} | Return Type: {} - Optimization recommended!",
                    methodName, executionTime, args, returnType);
        } else {
            dbQualityLog.info("[PERFORMANCE - OK] Method: {} | Execution Time: {}ms | Status: SUCCESS | Return Type: {}",
                    methodName, executionTime, returnType);
        }

        sysLog.info("[SYSTEM] Method {} executed in {}ms", methodName, executionTime);
        return proceed;
    }
}