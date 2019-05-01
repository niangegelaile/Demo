package com.ange.demo.nested;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.ange.demo.R;

public class NestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);
        WebView webView=findViewById(R.id.web);
        webView.loadUrl("https://www.baidu.com/?tn=62095104_26_oem_dg");
       int h= getResources().getDisplayMetrics().heightPixels;
       webView.getLayoutParams().height=h;
        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("NestActivity","scrollY="+scrollY);
            }
        });
    }
}
