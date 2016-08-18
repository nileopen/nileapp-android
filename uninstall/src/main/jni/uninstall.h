//
// Created by taotao on 16/8/1.
//

#ifndef APP_ANDROID_UNINSTALL_H
#define APP_ANDROID_UNINSTALL_H

#include <jni.h>
#include <malloc.h>
#include <stdlib.h>
#include <string>
#include <stdio.h>
#include <sys/file.h>
#include <sys/inotify.h>
#include <sys/stat.h>
#include <android/log.h>
#include <assert.h>

#define LOG_TAG "uninstall_jni"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
static const int OK = 0;
static const int ERROR = -1;
#ifdef __cplusplus
extern "C" {
#endif

#ifdef __cplusplus
}
#endif
#endif //NILEAPP_ANDROID_UNINSTALL_H
