package com.ange.demo.plugin;

import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class FieldUtil {
    public static Object getField(Class<?> aClass, Object obj, String fieldName) {
        try {
            Field field= aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object object=field.get(obj);
            return object;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setField(Class<?> aClass, Object target, String fieldName, Object value) {
        try {
            Field field=aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
