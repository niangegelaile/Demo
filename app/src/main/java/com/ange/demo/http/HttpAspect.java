package com.ange.demo.http;

import android.util.Log;

import com.example.httpproxy.CancelTool;
import com.example.httpproxy.ICancelTool;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by niangegelaile on 2018/3/11.
 */
@Aspect
public class HttpAspect {

    @Around("execution(@com.example.httpproxy.Xhttp * *(..))")
    public ICancelTool aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("HttpAspect","aroundJoinPoint");
        Object o=joinPoint.getTarget();
        if(o instanceof HttpActivity){
            Log.e("HttpAspect","o is HttpActivity");
            Field field= HttpActivity.class.getDeclaredField("cancelTools");
            field.setAccessible(true);
            if(field.get(o)==null){
                field.set(o,new ArrayList<CancelTool>());
            }
           ICancelTool ic= (ICancelTool) joinPoint.proceed();
            ((ArrayList<ICancelTool>)(field.get(o))).add(ic);
            return ic;
        }
        return null;
    }
}
