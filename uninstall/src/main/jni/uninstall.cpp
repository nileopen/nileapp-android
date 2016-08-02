//
// Created by taotao on 16/8/1.
//

#include "uninstall.h"

#ifdef __cplusplus
extern "C" {
#endif
#define JNIREG_CLASS "com/nile/uninstall/UninstallJni"
using namespace std;
/**
 * 返回值 char* 这个代表char数组的首地址
 * Jstring2CStr 把java中的jstring的类型转化成一个c语言中的char 字符串
 */
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String"); //String
    jstring strencode = env->NewStringUTF("utf-8"); // 得到一个java字符串 "utf-8"
    jmethodID mid = env->GetMethodID(clsstring, "getBytes",
                                     "(Ljava/lang/String;)[B"); //[ String.getBytes("utf-8");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid,
                                                         strencode); // String .getByte("utf-8");
    jsize alen = env->GetArrayLength(barr); // byte数组的长度
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //""
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0); //
    return rtn;
}

//使用文件锁的形式判断
int need_fork_new_process_with_fl(string path) {
    int fd = open(path.c_str(), O_RDONLY);
    return flock(fd, LOCK_EX | LOCK_NB);
}

void uninstall(JNIEnv *env, jobject obj, jstring packageName, jint sdkVersion) {
    // 1，将传递过来的java的包名转为c的字符串
    if (packageName == NULL) {
        LOGD("packageDir is null");
        return;
    }

//    char *pd = Jstring2CStr(env, packageDir);
    const char *pd = env->GetStringUTFChars(packageName, NULL);
    string packageNameStr = pd;
    env->ReleaseStringUTFChars(packageName, pd);

    string path = "";
    path += packageNameStr;
    path += "/lib";
    if (need_fork_new_process_with_fl(path) != 0) {
        LOGD("file has locked by another process");
        return;
    }
    // 2，创建当前进程的克隆进程
    pid_t pid = fork();

    // 3，根据返回值的不同做不同的操作,<0,>0,=0
    if (pid < 0) {
        // 说明克隆进程失败
        LOGD("current create process failure");
    } else if (pid > 0) {
        // 说明克隆进程成功，而且该代码运行在父进程中
        LOGD("create process success,current parent pid = %d", pid);
    } else {
        // 说明克隆进程成功，而且代码运行在子进程中
        LOGD("create process success,current child pid = %d", pid);
        // 4，在子进程中监视/data/data/包名这个目录

        //初始化inotify进程
        int fd = inotify_init();
        if (fd < 0) {
            LOGD("inotify_init failed !!!");
            exit(1);
        }

        //添加inotify监听器
        int wd = inotify_add_watch(fd, path.c_str(), IN_DELETE);
        if (wd < 0) {
            LOGD("inotify_add_watch failed !!!");
            exit(1);
        }

        //分配缓存，以便读取event，缓存大小=一个struct inotify_event的大小，这样一次处理一个event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOGD("malloc failed !!!");
            exit(1);
        }

        while (JNI_TRUE) {
            //开始监听
            LOGD("start uninstall observer");
            ssize_t readBytes = read(fd, p_buf, sizeof(struct inotify_event));
            //read会阻塞进程，走到这里说明收到目录被删除的事件
            FILE *libDir = fopen(path.c_str(), "r");//判断该目录是否存在,如果存在则是覆盖安装
            //read会阻塞进程，走到这里说明收到目录被删除的事件，注销监听器
            if (libDir == NULL) {
                inotify_rm_watch(fd, IN_DELETE);
                free(p_buf);
                p_buf = NULL;

                char *url = NULL;
                jclass obj_cls = env->GetObjectClass(obj);
                if (obj_cls != NULL) {

                    jmethodID getUrl = env->GetMethodID(obj_cls, "getUrl", "()Ljava/lang/String;");
                    jstring jurl = (jstring) env->CallObjectMethod(obj, getUrl);
                    if (jurl != NULL) {
                        url = Jstring2CStr(env, jurl);
                    }
                    LOGD("start observer url=%s", url);
                }

                // 应用被卸载了，通知系统打开用户反馈的网页
                LOGD("app uninstall,current sdkversion = %d", sdkVersion);
                int ret = -1;
                if (url != NULL && sdkVersion >= 17) {
                    // Android4.2系统之后支持多用户操作，所以得指定用户
                    ret = execlp("am", "am", "start", "--user", "0", "-a",
                                 "android.intent.action.VIEW", "-d", url, (char *) NULL);
                } else {
                    // Android4.2以前的版本无需指定用户
                    ret = execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
                                 "-d", url, (char *) NULL);
                }
            } else {
                // 覆盖安装
                LOGD("app reinstall");
                fclose(libDir);
                free(p_buf);
                p_buf = NULL;
                exit(0);
            }
        }

        if (p_buf != NULL) {
            free(p_buf);
        }
    }
}

static JNINativeMethod gMethods[] = {
        {"uninstall", "(Ljava/lang/String;I)V", (void *) uninstall}
};

static int registerNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *gMethods,
                                 int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

static int registerNatives(JNIEnv *env) {
    if (!registerNativeMethods(env, JNIREG_CLASS, gMethods, sizeof(gMethods) / sizeof(gMethods[0])))
        return JNI_FALSE;

    return JNI_TRUE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGD("getEnv failed");
        return JNI_ERR;
    }
    assert(env != NULL);

    if (!registerNatives(env)) {
        LOGD("registerNatives failed");
        return JNI_ERR;
    }
    result = JNI_VERSION_1_4;
    LOGD("load library success: %d", result);
    return result;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGD("library was unload");
}
#ifdef __cplusplus
}
#endif