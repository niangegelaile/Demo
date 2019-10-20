package com.ange.demo.shortcut;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ange.demo.R;

public class ShortcutActivity extends AppCompatActivity {

    private static final String TAG="ShortcutActivity";
    private MyReceiver myReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_cut);
        findViewById(R.id.but_add_short_cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortCutCompact(ShortcutActivity.this);
            }
        });
        findViewById(R.id.but_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean flag=hasShortcut(ShortcutActivity.this);
                Toast.makeText(ShortcutActivity.this,"flag="+flag,Toast.LENGTH_SHORT).show();
            }
        });
        myReceiver=new MyReceiver();
        registerReceiver(myReceiver,new IntentFilter("ShortcutActivity.MyReceiver"));
    }

    public static void addShortCutCompact(Context context) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            Intent shortcutInfoIntent = new Intent(context, ShortcutActivity.class);
            shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错

            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, "ange")
                    .setIcon(IconCompat.createWithResource(context,R.mipmap.ic_launcher))
                    .setShortLabel("ange")
                    .setIntent(shortcutInfoIntent)
                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent("ShortcutActivity.MyReceiver"), PendingIntent.FLAG_UPDATE_CURRENT);
            ShortcutManagerCompat.requestPinShortcut(context, info, shortcutCallbackIntent.getIntentSender());
        }
    }

    /**
     * 判断是否已经存在桌面图标
     *
     * @return
     */
    public static boolean hasShortcut(Context context) {
        boolean result = false;
        String title = "ange";
        final String uriStr;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else if (android.os.Build.VERSION.SDK_INT < 19) {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher3.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = context.getContentResolver().query(CONTENT_URI, null,
                "title=?", new String[]{title}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: ");
        }
    }
}
