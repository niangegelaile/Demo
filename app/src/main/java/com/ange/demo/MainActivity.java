package com.ange.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ange.demo.lifecycle.LifecycleActivity;
import com.ange.demo.parallax.ParallaxActivity;
import com.ange.demo.sroller.ScrollerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.but_xiaomi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ParallaxActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.but_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ScrollerActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.but_life).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LifecycleActivity.class);
                startActivity(intent);
            }
        });
    }
}
