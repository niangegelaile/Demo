package com.ange.demo.http;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 截获类名最后含有Activity、Layout的类的所有方法
 * 监听目标方法的执行时间
 */
@Aspect
public class TraceAspect {
    private final static String TAG="TraceAspect";
    private static Object currentObject = null;
    //进行类似于正则表达式的匹配，被匹配到的方法都会被截获
    ////截获任何包中以类名以Activity、Layout结尾，并且该目标类和当前类是一个Object的对象的所有方法
    private static final String POINTCUT_METHOD =
            "(execution(* *..Activity+.*(..)) ||execution(* *..Layout+.*(..))) && target(Object) && this(Object)";
    //精确截获MyFrameLayou的onMeasure方法
    private static final String POINTCUT_CALL = "call(* com.ange.demo.fileProvider.FileProvider7.getUriForFile(..))";

    private static final String POINTCUT_METHOD_MAINACTIVITY = "execution(* *..MainActivity.onCreate(..))";

    //切点，ajc会将切点对应的Advise编织入目标程序当中
    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotated() {}
    @Pointcut(POINTCUT_METHOD_MAINACTIVITY)
    public void methodAnootatedWith(){}
    @Pointcut(POINTCUT_CALL)
    public void methodFile(){}
    @After("methodFile()")
    public void callgetUriForFile(JoinPoint joinPoint) throws Throwable{
        Log.e(TAG,"callgetUriForFile:");
    }


    /**
     * 在截获的目标方法调用之前执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Before("methodAnootatedWith()")
    public void onCreateBefore(JoinPoint joinPoint) throws Throwable{
        Activity activity = null;
        //获取目标对象
        activity = ((Activity)joinPoint.getTarget());
        //插入自己的实现，控制目标对象的执行

        //做其他的操作
        Log.e(TAG,"onCreateBefore:"+"MainActivity"+"onCreateBefore");
    }
    /**
     * 在截获的目标方法调用返回之后（无论正常还是异常）执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @After("methodAnootatedWith()")
    public void onCreateAfter(JoinPoint joinPoint) throws Throwable{
        Log.e("onCreateAfter:","onCreate is end .");
        Log.e(TAG,"onCreateAfter:"+"MainActivity"+"onCreateAfter");
    }
    /**
     * 在截获的目标方法体开始执行时（刚进入该方法实体时）调用
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodAnnotated()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        if (currentObject == null){
            currentObject = joinPoint.getTarget();
        }

        //调用原方法的执行。
        Object result = joinPoint.proceed();

        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className;
        //获取当前对象，通过反射获取类别详细信息
        className = joinPoint.getThis().getClass().getName();

        String methodName = methodSignature.getName();
        Log.e(TAG,"weaveJoinPoint:"+className+methodName);
        return result;
    }



}
