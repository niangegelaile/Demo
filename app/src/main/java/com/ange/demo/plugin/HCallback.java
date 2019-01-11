package com.ange.demo.plugin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HCallback implements Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case LAUNCH_ACTIVITY:
                Object obj = msg.obj;
                try {
                    Intent intent = (Intent) FieldUtil.getField(obj.getClass(), obj, "intent");//拿到ActivityClientRecord的intent字段
                    Intent targetIntent = intent.getParcelableExtra(HookHelper.TRANSFER_INTENT);//拿到我们要启动PluginActivity的Intent
                    intent.setComponent(targetIntent.getComponent());//替换为启动PluginActivity
                    Log.e("HCallback","HCallback");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return false;
    }
}
