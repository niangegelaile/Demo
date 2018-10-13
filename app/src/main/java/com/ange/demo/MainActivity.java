package com.ange.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ange.demo.controlView.ControlActivity;
import com.ange.demo.coordinator.CoordinatorActivity;
import com.ange.demo.databinding.ActivityMainBinding;
import com.ange.demo.fileProvider.FileProviderActivity;
import com.ange.demo.http.HttpActivity;
import com.ange.demo.js.WebActivity;
import com.ange.demo.kotlin.KotlinActivity;
import com.ange.demo.lifecycle.LifecycleActivity;
import com.ange.demo.parallax.ParallaxActivity;
import com.ange.demo.pullToRefresh.RefreshActivity;
import com.ange.demo.sroller.ScrollerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        List<Catalog> list=new ArrayList<>();
        Catalog catalogXiaomi=new Catalog("小米橡皮筋效果",ParallaxActivity.class.getName());
        Catalog catalogviewPager=new Catalog("仿viewPager",ScrollerActivity.class.getName());
        Catalog catalogLifeCycle=new Catalog("activity/fragment生命周期",LifecycleActivity.class.getName());
        Catalog catalogWeb=new Catalog("js/web",WebActivity.class.getName());
        Catalog catalogFileProvide=new Catalog("FileProvide",FileProviderActivity.class.getName());
        Catalog cataloghttp=new Catalog("http封装",HttpActivity.class.getName());
        Catalog catalogRefresh=new Catalog("自定义刷新控件",RefreshActivity.class.getName());
        Catalog catalogControl=new Catalog("自定义摇杆控件",ControlActivity.class.getName());
        list.add(catalogControl);
        list.add(catalogXiaomi);
        list.add(catalogRefresh);
        list.add(cataloghttp);
        list.add(catalogWeb);
        list.add(catalogviewPager);
        list.add(catalogLifeCycle);
        list.add(catalogFileProvide);
        binding.setCatalogs(list);


    }
}
