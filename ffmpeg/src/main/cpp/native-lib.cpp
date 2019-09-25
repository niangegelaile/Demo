//
// Created by niangegelaile on 2019/9/15.
//
#include <jni.h>
#include <string>
#include <stdio.h>

extern "C" JNIEXPORT jstring JNICALL
 Java_com_example_ffmpeg_JniUtil_stringFromJNI
  (JNIEnv *env, jobject){
    std::string hello="hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ffmpeg_JniUtil_meta(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);



    env->ReleaseStringUTFChars(path_, path);
}



