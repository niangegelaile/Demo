package com.example.httpproxy;

import com.example.httpproxy.ICancelTool;

import rx.Subscription;

/**
 * Created by ange on 2017/11/28.
 */

public class CancelTool implements ICancelTool {
    private Subscription sb;

    public CancelTool(Subscription sb) {
        this.sb = sb;
    }

    @Override
    public void cancel() {
        if(sb!=null&&!sb.isUnsubscribed()){
            sb.unsubscribe();
        }
    }
}
