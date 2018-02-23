package com.ange.demo.fileProvider;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ange.demo.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by ange on 2018/2/23.
 */

public class FileProviderActivity extends AppCompatActivity {
    private static final String TAG  = "FileProviderActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_provider);
        TextView tvFilesPath = (TextView) findViewById(R.id.tv_filesPath);
        TextView tvCachePath = (TextView) findViewById(R.id.tv_cachePath);
        TextView tvexternalCachePath = (TextView) findViewById(R.id.tv_externalCachePath);
        TextView tvExternalPath = (TextView) findViewById(R.id.tv_externalPath);
//        tvFilesPath.setText("FilesPath:"+this.getFilesDir().toString()+"----"+FileProvider7.getUriForFile(this,this.getFilesDir()));
//        tvCachePath.setText("CachePath:"+this.getCacheDir().toString()+"----"+FileProvider7.getUriForFile(this,this.getCacheDir()));
//        tvexternalCachePath.setText("externalCachePath:"+this.getExternalCacheDir().toString()+"----"+FileProvider7.getUriForFile(this,this.getExternalCacheDir()));
        Log.d(TAG,Environment.getExternalStorageDirectory().getPath()+"/abc/angeDemo");
        File file =makeDirs(Environment.getExternalStorageDirectory().getPath()+"/abc/angeDemo") ;
//        file=createFile();
        tvExternalPath.setText("ExternalPath:" + file + "-----" + FileProvider7.getUriForFile(this, file));
    }

    private File makeDirs(String path) {
        File file = new File(path);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建<span style="color: rgb(255, 0, 0);">目录中包含却不存在</span>的文件夹
            boolean flag=  file.mkdirs();
            Log.d("FileProviderActivity","flag:"+flag);
        }
        return file;
    }
    private File createFile() {
        //新建一个File类型的成员变量，传入文件名路径。
        File mFile = new File("/mnt/sdcard/work/mywork/zhiyuan.txt");
        //判断文件是否存在，存在就删除
        if (mFile.exists()) {
            mFile.delete();
        }
        try {
            //创建文件
            mFile.createNewFile();
            //给一个吐司提示，显示创建成功
            Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mFile;
    }


}
