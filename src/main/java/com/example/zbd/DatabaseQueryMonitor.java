package com.example.zbd;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class DatabaseQueryMonitor {

    private static final Logger sysLog = LoggerFactory.getLogger("syslogs");
    private static final Logger dbQualityLog = LoggerFactory.getLogger("databaselogs");

    private static final long SLOW_QUERY_THRESHOLD = 100;
    private static final String CSV_FILE = "query_metrics.csv";


    static {
        File file = new File(CSV_FILE);
        if(!file.exists()) {
            try(FileWriter writer = new FileWriter(file, true)) {
                writer.append("timestamp;method;executionTimeMs;status;returnType\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @Around("execution(* com.example.zbd.repositories.*.*(..))")
    public Object logDatabaseMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        //long start = System.currentTimeMillis();
        long start = System.nanoTime();

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
            saveToCsv(methodName, -1, "ERROR", returnType);
            sysLog.error("[SYSTEM ERROR] Method failure: {}", methodName);
            dbQualityLog.error("[DATABASE ERROR] Method: {} | Args: {} | Exception: {}",
                    methodName, args, e.getMessage());
            throw e;
        }

        //long executionTime = System.currentTimeMillis() - start;
        long executionTime = System.nanoTime() - start;

        String status;

        if (executionTime > SLOW_QUERY_THRESHOLD) {
            status = "SLOW";
            /*dbQualityLog.warn("[PERFORMANCE - LOW] Method: {} | Execution Time: {}ms | Args: {} | Return Type: {} - Optimization recommended!",
                    methodName, executionTime, args, returnType);*/
            //dbQualityLog.warn("{};{};{};{};{};{}", LocalDateTime.now(), methodName, executionTime, args, returnType, "SLOW");
            dbQualityLog.warn("[PERFORMANCE - LOW] Method: {} | Execution Time: {}ms | Args: {} | Return Type: {}", methodName, executionTime, args, returnType);
        } else {
            status = "SUCCESS";
/*            dbQualityLog.info("[PERFORMANCE - OK] Method: {} | Execution Time: {}ms | Status: SUCCESS | Return Type: {}",
                    methodName, executionTime, returnType);*/
            //dbQualityLog.info("{};{};{};{};{}", LocalDateTime.now(), methodName, executionTime, "SUCCESS", returnType);
            dbQualityLog.info("[PERFORMANCE - OK] Method: {} | Execution Time: {}ms | Status: SUCCESS | Return Type: {}",
                    methodName, executionTime, returnType);
        }

        saveToCsv(methodName, executionTime, status, returnType);



        sysLog.info("[SYSTEM] Method {} executed in {}ms", methodName, executionTime);
        return proceed;
    }

    private synchronized void saveToCsv(String method, long executionTime, String status, String returnType){
        try(FileWriter writer = new FileWriter(CSV_FILE, true)) {
            writer.append(String.format("%s;%s;%d;%s;%s;%n", LocalDateTime.now(), method, executionTime, status, returnType));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}