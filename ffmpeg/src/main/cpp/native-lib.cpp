//
// Created by niangegelaile on 2019/9/15.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
 Java_com_example_ffmpeg_JniUtil_stringFromJNI
  (JNIEnv *env, jobject){
    std::string hello="hello from C++";
    return env->NewStringUTF(hello.c_str());
}



