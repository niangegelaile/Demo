package com.ange.demo.js;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ange.demo.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsHook(),"android");
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
//        webView.loadUrl("http://localhost:8081/#/?id=59&userId=383");
        String tpl = getFromAssets("hygj/index.html");
        webView.loadDataWithBaseURL("file:///android_asset/hygj/", tpl, "text/html", "utf-8", null);

    }
    /*
        * 获取html文件
        */
    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
            Map<String,Object> map=new HashMap<>();
            map.put("id",22);
            map.put("tel","13750523051");
            return new JSONObject(map).toString();
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
