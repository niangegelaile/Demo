package com.example.ffmpeg;

public class JniUtil {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    public static native void meta(String path);
}
