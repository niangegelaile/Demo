package com.ange.demo.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ange.demo.R;

/**
 * Created by ange on 2017/7/29.
 */

public class LifecycleActivity extends AppCompatActivity{


    LifecycleFragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        if(fragment==null){
            Log.d("TAG", "onCreate: new");
            fragment=new LifecycleFragment();
        }
       if(!fragment.isAdded()){
           Log.d("TAG", "onCreate: add");
           getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();
       }

        fragment.setActivityLife("a:onCreate");
    }

    @Override
    protected void onStart() {//fragment的onStart先于activity
        super.onStart();
        fragment.setActivityLife("a:onStart");
    }

    @Override
    protected void onResume() {//activity的onResume先于fragment
        super.onResume();
        fragment.setActivityLife("a:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        fragment.setActivityLife("a:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        fragment.setActivityLife("a:onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment.setActivityLife("a:onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fragment.setActivityLife("a:onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragment.setActivityLife("a:onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragment.setActivityLife("a:onSaveInstanceState");
    }
}
