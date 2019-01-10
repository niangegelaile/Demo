package com.ange.demo.plugin;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;

public class InjectUtil {

    private static final String TAG = "InjectUtil";
    private static final String CLASS_BASE_DEX_CLASSLOADER = "dalvik.system.BaseDexClassLoader";
    private static final String CLASS_DEX_PATH_LIST = "dalvik.system.DexPathList";
    private static final String FIELD_PATH_LIST = "pathList";
    private static final String FIELD_DEX_ELEMENTS = "dexElements";


    public static void inject(Context context, ClassLoader origin) throws Exception {
        File filePlugin =context.getExternalFilesDir("plugin");
        if(filePlugin==null||!filePlugin.exists()||filePlugin.list().length==0){
            Log.i(TAG, "插件文件不存在");
            return;
        }

        filePlugin=filePlugin.listFiles()[0];

        File optimizeFile=context.getFileStreamPath("plugin");

        if(!optimizeFile.exists()){
            optimizeFile.mkdirs();
        }
        DexClassLoader pluginClassLoader=new DexClassLoader(filePlugin.getAbsolutePath(),optimizeFile.getAbsolutePath(),null,origin);

        Object pluginDexPathList = FieldUtil.getField(Class.forName(CLASS_BASE_DEX_CLASSLOADER), pluginClassLoader, FIELD_PATH_LIST);

        Object pluginElements = FieldUtil.getField(Class.forName(CLASS_DEX_PATH_LIST), pluginDexPathList, FIELD_DEX_ELEMENTS);//拿到插件Elements


        Object originDexPathList = FieldUtil.getField(Class.forName(CLASS_BASE_DEX_CLASSLOADER), origin, FIELD_PATH_LIST);
        Object originElements = FieldUtil.getField(Class.forName(CLASS_DEX_PATH_LIST), originDexPathList, FIELD_DEX_ELEMENTS);//拿到Path的Elements

        Object array = combineArray(originElements, pluginElements);//合并数组
        FieldUtil.setField(Class.forName(CLASS_DEX_PATH_LIST), originDexPathList, FIELD_DEX_ELEMENTS, array);//设置回PathClassLoader
        Log.i(TAG, "插件文件加载成功");


    }

    private static Object combineArray(Object originElements, Object pluginElements) {
        Class<?> componentType=originElements.getClass().getComponentType();

        int i=Array.getLength(originElements);
        int j=Array.getLength(pluginElements);
        int k=i+j;
        Object result =Array.newInstance(componentType,k);
        System.arraycopy(pluginElements,0,result,0,j);
        System.arraycopy(originElements,0,result,j,i);

        return result;
    }


}
