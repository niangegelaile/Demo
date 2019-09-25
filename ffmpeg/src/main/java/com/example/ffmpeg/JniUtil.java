package com.example.ffmpeg;

public class JniUtil {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    public native void meta(String path);
}
