package com.ange.demo.nested;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.ange.demo.R;

public class NestActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);
         webView=findViewById(R.id.web);
//        webView.loadUrl("https://www.baidu.com/");
        webView.loadUrl("file:///android_asset/test1.html");
       int h= getResources().getDisplayMetrics().heightPixels;
       webView.getLayoutParams().height=h;
       findViewById(R.id.but).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               webView.flingScroll(0,10000);
           }
       });
        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("NestActivity","scrollY="+scrollY);
            }
        });
    }
}
