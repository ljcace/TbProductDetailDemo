package com.ljc.aspectj.aops;

import android.app.Activity;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class MethodDelayAop {
    @Pointcut("execution(@com.ljc.aspectj.annotations.MethodDelay * *(..))")
    public void methodDelayPointCut() {
    }

    @Around("methodDelayPointCut()")
    public Object methodDelay(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        String methodName = methodSignature.getName();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        Log.e("MethodDelayInfo", String.format("\'%s\'类中\'%s()\'方法执行,耗时：%dms", className, methodName, duration));
        return result;
    }

}