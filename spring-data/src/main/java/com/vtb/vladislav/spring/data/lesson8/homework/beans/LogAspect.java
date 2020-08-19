package com.vtb.vladislav.spring.data.lesson8.homework.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@AllArgsConstructor
@Getter
public class LogAspect {
    // каждому сервису будет соответствовать свой Map,
    // в котором каждому вызванному методу будет соответствовать число вызовов
    private Map<String, Map<String, Integer>> statistics;

    @After("execution(public * com.vtb.vladislav.spring.data.lesson8.homework.services..*(..))")
    public void countMethodCalls(JoinPoint joinPoint) {
        // получаем имя класса без package
        String className = joinPoint.getSignature().getDeclaringTypeName().split("\\.")[8];
        String methodName = joinPoint.getSignature().getName();
        Map<String, Integer> methodsMap =  statistics.getOrDefault(className, new HashMap<>());
        methodsMap.put(methodName, methodsMap.getOrDefault(methodName, 0) + 1);
        statistics.put(className, methodsMap);
    }
}
