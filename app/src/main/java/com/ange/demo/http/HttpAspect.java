package com.ange.demo.http;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by niangegelaile on 2018/3/11.
 */
@Aspect
public class HttpAspect {

    @Around("execution(@com.example.httpproxy.Xhttp * *(..))")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("HttpAspect","aroundJoinPoint");
    }
}
