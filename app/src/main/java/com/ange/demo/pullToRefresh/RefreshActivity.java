package com.ange.demo.pullToRefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ange.demo.R;

/**
 * Created by ange on 2018/4/18.
 */

public class RefreshActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    RefreshView refreshView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        refreshView=findViewById(R.id.refresh_view);
        refreshView.addHeadView(new HeadViewImp(this));
        refreshView.addFootView(new FooterViewImp(this));
        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.complete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.complete();
                    }
                },2000);
            }
        });
    }
}
