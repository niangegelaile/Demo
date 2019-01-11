package com.ange.demo.plugin;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650244933&idx=1&sn=15631267e4ac1d9f3e37c26016784d56&chksm=8863762abf14ff3ccd32a18ebd0caa3e05c18ea425a7b64188e8f66095c19b463c8548d500ab&mpshare=1&scene=23&srcid=0109iZKUVwZUC7NiOo4pTXWk#rd
public class HookHelper {
    public static final String TRANSFER_INTENT = "TRANSFER_INTENT";

    public static void hookAMS() throws Exception {
        Object singleton = null;
        if (Build.VERSION.SDK_INT >= 26) {
            Class<?> clazz = Class.forName("android.app.ActivityManager");
            singleton = FieldUtil.getField(clazz, null, "IActivityManagerSingleton");
        } else {
            Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
            singleton = FieldUtil.getField(activityManagerNativeClazz, null, "gDefault");//拿到静态字段
        }
        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Method get = singletonClazz.getMethod("get");
        Object am = get.invoke(singleton);
        Class<?> amClazz = Class.forName("android.app.IActivityManager");
        Object proxy= Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{amClazz},new IActivityManagerProxy(am));
        FieldUtil.setField(singletonClazz, singleton, "mInstance", proxy);//将代理后的对象设置回去
    }


    public static void hookH() throws Exception {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object activityThread = FieldUtil.getField(activityThreadClazz, null, "sCurrentActivityThread");//拿到activityThread
        Object mH = FieldUtil.getField(activityThreadClazz, activityThread, "mH");//拿到mH
        FieldUtil.setField(Handler.class, mH, "mCallback", new HCallback());//给mH设置callback
    }


}
