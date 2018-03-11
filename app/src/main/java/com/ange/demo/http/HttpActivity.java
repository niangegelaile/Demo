package com.ange.demo.http;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ange.demo.R;
import com.example.httpproxy.HttpUtil;
import com.example.httpproxy.IRequestCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public class HttpActivity extends AppCompatActivity {
    TextView tv;
    public static final String SELECT_ADVERT_URL =  "http://pension.uat.hengtech.com.cn/heyuan/"+ "api/config/selectAdvert.do";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        tv=findViewById(R.id.tv_http);
        request();
    }

    private void request() {
        Map<String,Object> map=new HashMap<>();
        map.put("userId", 0);
        HttpUtil.getInstance(this).request(map, SELECT_ADVERT_URL, new IRequestCallback<AdvertResponse>() {
            @Override
            public void onSuccess(AdvertResponse response) {
                if(response.isStatus()){
                    tv.setText("ok");
                }else {
                    tv.setText(response.getCode());
                }
            }

            @Override
            public void onFailed(String msg) {
                tv.setText(msg);
            }

            @Override
            public void onNotNetwork() {

            }
        });
    }
}
