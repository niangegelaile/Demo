//
// Created by niangegelaile on 2019/9/15.
//
#include <jni.h>
#include <android/log.h>
#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <string>
#include <unistd.h>

#define LOG_TAG "FFNative"
#define ALOGV(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__))
#define ALOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
#define ALOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
#define ALOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__))
#define ALOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))

extern "C" JNIEXPORT jstring JNICALL
 Java_com_example_ffmpeg_JniUtil_stringFromJNI
  (JNIEnv *env, jclass type){
    std::string hello="hello from C++";
    return env->NewStringUTF(hello.c_str());
}




extern "C" {
#include <stdio.h>
#include <libavformat/avformat.h>
#include <libavutil/log.h>
JNIEXPORT void JNICALL
Java_com_example_ffmpeg_JniUtil_meta(JNIEnv *env, jclass type, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    AVFormatContext* fmt_ctx=NULL;

    av_register_all();
    int ret=avformat_open_input(&fmt_ctx,path,NULL,NULL);
    if(ret<0){
        ALOGE("can't open file %s\n",path);
    }
    ALOGE("ret= %s\n",ret<0?"-1":"1");
    av_dump_format(fmt_ctx,0,path,0);
    avformat_close_input(&fmt_ctx);
    env->ReleaseStringUTFChars(path_, path);
}
}



