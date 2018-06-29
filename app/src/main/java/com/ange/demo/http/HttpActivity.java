package com.ange.demo.http;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ange.demo.R;
import com.example.httpproxy.HttpUtil;
import com.example.httpproxy.ICancelTool;
import com.example.httpproxy.IRequestCallback;
import com.example.httpproxy.Xhttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public class HttpActivity extends AppCompatActivity {
    private List<ICancelTool> cancelTools=new ArrayList<>();
    TextView tv;
    TextView postTv;
    public static final String SELECT_ADVERT_URL =  "http://pension.uat.hengtech.com.cn/heyuan/"+ "api/friends/getFriendsRole.do";
    public static final String friends_addFriendsComments = "http://pension.uat.hengtech.com.cn/heyuan/"+ "api/friends/addFriendsComments.do";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        tv=findViewById(R.id.tv_http);
        postTv=findViewById(R.id.tv_post);
        request();
        post();
    }
    @Xhttp
    private ICancelTool request() {
        final Map<String, Object> map = new HashMap<>();
        map.put("userType", 2);
        return HttpUtil.getInstance(this).get(map, SELECT_ADVERT_URL, new IRequestCallback<String>() {
            @Override
            public void onSuccess(String response) {

                    tv.setText(response);

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

    private void post(){
        Map<String,Object> map=new HashMap<>();
        HttpUtil.getInstance(this).post(map, friends_addFriendsComments, new
                IRequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        postTv.setText(response);
                    }

                    @Override
                    public void onFailed(String msg) {
                        postTv.setText(msg);
                    }

                    @Override
                    public void onNotNetwork() {

                    }
                });
    }











    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cancelTools!=null){
            for(ICancelTool iCancelTool:cancelTools){
                Log.d("HttpActivity","ICancelTool");
                iCancelTool.cancel();
            }
        }
    }
}
