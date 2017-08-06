package com.ange.demo.js;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ange.demo.R;

/**
 * Created by liquanan on 2017/8/6.
 * email :1369650335@qq.com
 */

public class WebActivity extends AppCompatActivity {
    private static final String TAG  = "WebActivity";
    private WebView webView;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView=(WebView)findViewById(R.id.web);
        webView.loadUrl("http://192.168.1.104:8080/android.jsp");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsHook(),"hello");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, " url:"+url);
                view.loadUrl(url);// 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
                return true;
            }
        });
        findViewById(R.id.but_js).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:show('"+getPackageName()+"')");
            }
        });
    }



    public class JsHook{
        @JavascriptInterface
        public void javaMethod(String p){
            Log.d(TAG , "JSHook.JavaMethod() called! + "+p);
            Toast.makeText(WebActivity.this,"js调用了JsHook :javaMethod",Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void showAndroid(){
            String info = "来自手机内的内容！！！";
            webView.loadUrl("javascript:show('"+info+"')");
        }
        @JavascriptInterface
        public String getInfo(){
            return "获取手机内的信息！！";
        }





    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
        return true;
    }
}
