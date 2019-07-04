package com.ange.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ange.demo.WheelView.WheelActivity;
import com.ange.demo.controlView.ControlActivity;
import com.ange.demo.databinding.ActivityMainBinding;
import com.ange.demo.fileProvider.FileProviderActivity;
import com.ange.demo.http.HttpActivity;
import com.ange.demo.js.WebActivity;

import com.ange.demo.lifecycle.LifecycleActivity;
import com.ange.demo.midea.MideaActivity;
import com.ange.demo.nested.NestActivity;
import com.ange.demo.page.PageActivity;
import com.ange.demo.parallax.ParallaxActivity;
import com.ange.demo.plugin.SubActivity;
import com.ange.demo.pullToRefresh.RefreshActivity;
import com.ange.demo.shadow.ShadowActivity;
import com.ange.demo.sroller.ScrollerActivity;
import com.niangegelaile.kotlinlibrary.KotlinActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Catalog> list=new ArrayList<>();
    private String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        Catalog catalogXiaomi=new Catalog("小米橡皮筋效果",ParallaxActivity.class.getName());
        Catalog catalogviewPager=new Catalog("仿viewPager",ScrollerActivity.class.getName());
        Catalog catalogLifeCycle=new Catalog("activity/fragment生命周期",LifecycleActivity.class.getName());
        Catalog catalogWeb=new Catalog("js/web",WebActivity.class.getName());
        Catalog catalogFileProvide=new Catalog("FileProvide",FileProviderActivity.class.getName());
        Catalog cataloghttp=new Catalog("http封装",HttpActivity.class.getName());
        Catalog catalogRefresh=new Catalog("自定义刷新控件",RefreshActivity.class.getName());
        Catalog catalogControl=new Catalog("自定义摇杆控件",ControlActivity.class.getName());
        Catalog catalogWheelView=new Catalog("自定义WheelView",WheelActivity.class.getName());

        Catalog catalogKolin=new Catalog("Kotlin",KotlinActivity.class.getName());

        Catalog catalogShodow=new Catalog("Shadow",ShadowActivity.class.getName());
        addItem("视频录制",MideaActivity.class);

        addItem("android插件化",SubActivity.class);
        addItem("nested",NestActivity.class);
        addItem("page",PageActivity.class);
        list.add(catalogControl);
        list.add(catalogXiaomi);
        list.add(catalogRefresh);
        list.add(cataloghttp);
        list.add(catalogWeb);
        list.add(catalogviewPager);
        list.add(catalogLifeCycle);
        list.add(catalogFileProvide);
        list.add(catalogWheelView);

        list.add(catalogKolin);

        list.add(catalogShodow);

        binding.setCatalogs(list);
        Log.d(TAG,"main: onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"main:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"main:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"main:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"main:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"main:onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"main:onRestart");
    }

    private void addItem(String name, Class clz){
        Catalog catalog=new Catalog(name,clz.getName());
        list.add(catalog);
    }


}
