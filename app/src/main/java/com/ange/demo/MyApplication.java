package com.ange.demo;

import android.app.Application;

import com.ange.demo.plugin.HookHelper;
import com.ange.demo.plugin.InjectUtil;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            InjectUtil.inject(this,getClassLoader());
            HookHelper.hookAMS();
            HookHelper.hookH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
