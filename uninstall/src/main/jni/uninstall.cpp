//
// Created by taotao on 16/8/1.
//

#include "uninstall.h"

#ifdef __cplusplus
extern "C" {
#endif
#define JNIREG_CLASS "com/nile/uninstall/UninstallJni"
using namespace std;
int watchDescriptor;
int fileDescriptor;
pid_t observer = -1;
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
    string path1 = path + "/lib";
    int fd = open(path1.c_str(), O_RDONLY);
    return flock(fd, LOCK_EX | LOCK_NB);
}

/**
 * 判断是否进程活着
 */
int isProcessAlive(const char *pid) {

    FILE *pidFile;
    char observerPID[32];
    if ((pidFile = fopen(pid, "rb")) == NULL) {
        LOGE("isProcessAlive can't open pid file");
        return ERROR;
    }
    // fread(&observerPID,sizeof(observerPID),1,pidFile);
    fscanf(pidFile, "%d", &observer);
    fclose(pidFile);
    if (observer > 1) {
        sprintf(observerPID, "%d/n", observer);
        LOGD("isProcessAlive read saved pid");

        if (kill(observer, 0) == 0) {
            LOGD("isProcessAlive process is alive");
            return OK;
        }

        LOGD("isProcessAlive process is not alive");
    } else {
        LOGD("isProcessAlive not read saved pid");
        return ERROR;
    }
}

/**
 * 监听
 */
int startObserver(void *p_buf, string path) {

    // 若监听文件所在文件夹不存在，创建文件夹
    string APP_FILES_DIR = path + "/files";
    FILE *p_filesDir = fopen(APP_FILES_DIR.c_str(), "r");
    if (p_filesDir == NULL) {
        int filesDirRet = mkdir(APP_FILES_DIR.c_str(), S_IRWXU | S_IRWXG | S_IXOTH);
        if (filesDirRet == -1) {
            LOGE("startObserver create app files dir failed");
            exit(1);
        }
    }

//    if (access(APP_FILES_DIR, F_OK) != 0) {
//        LOGD("folder not exists");
//        if (mkdir(APP_FILES_DIR, 0755) == -1) {
//            LOGE("mkdir failed!");
//            exit(1);
//        }
//    }


    // 若被监听文件不存在，创建监听文件
    string APP_OBSERVED_FILE = path + "/files/observedFile";
    FILE *p_observedFile = fopen(APP_OBSERVED_FILE.c_str(), "r");
    if (p_observedFile == NULL) {
        p_observedFile = fopen(APP_OBSERVED_FILE.c_str(), "w");
        LOGD("startObserver create app observed file");
    }
    fclose(p_observedFile);

    // 创建锁文件，通过检测加锁状态来保证只有一个卸载监听进程
    string APP_LOCK_FILE = path + "/files/lockFile";
    int lockFileDescriptor = open(APP_LOCK_FILE.c_str(), O_RDONLY);
    if (lockFileDescriptor == -1) {
        lockFileDescriptor = open(APP_LOCK_FILE.c_str(), O_CREAT);
        LOGD("startObserver create app lock file");
    }
    int lockRet = flock(lockFileDescriptor, LOCK_EX | LOCK_NB);
    if (lockRet == -1) {
        LOGE("startObserver watch by other process");
        return ERROR;
    }

    // 初始化inotify进程
    fileDescriptor = inotify_init();
    if (fileDescriptor < 0) {
        LOGE("startObserver inotify init failed");
        free(p_buf);
        exit(1);
    }

    // 添加inotify监听器，监听APP_OBSERVED_FILE文件
    watchDescriptor = inotify_add_watch(fileDescriptor, APP_OBSERVED_FILE.c_str(), IN_ALL_EVENTS);
    if (watchDescriptor < 0) {
        LOGE("startObserver inotify watch failed");
        free(p_buf);
        exit(1);
    }
    return OK;
}

/**
 * 记录pid
 */
void writePidFile(const char *pid) {
    char str[32];
    int pidFile = open(pid, O_WRONLY | O_TRUNC);
    if (pidFile < 0) {
        LOGE("pid is %d", pidFile);
        exit(1);
    }

    if (flock(pidFile, LOCK_EX | LOCK_NB) < 0) {
        LOGD("cann't lock pid file: %s", pid);
        fprintf(stderr, "can't lock pid file: %s", pid);
        exit(1);
    }

    sprintf(str, "%d/n", getpid());
    ssize_t len = strlen(str);
    ssize_t ret = write(pidFile, str, len);

    if (ret != len) {
        LOGE("can't write pid file: %s", pid);
        fprintf(stderr, "can't write pid file: %s", pid);
        exit(1);
    }
    close(pidFile);
    LOGD("write pid file success");
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
//    string file = packageNameStr + "/files/uninstall";
//    FILE *fileFd = fopen(file.c_str(), "r");
//    if (fileFd != NULL){
//        LOGD("file has created");
//        return;
//    }
//    fileFd = fopen(file.c_str(), "w+");
//    if (fileFd == NULL){
//        LOGD("file create failed");
//        return;
//    }

//    path += "/lib";
//    if (sdkVersion < 21) {
//        if (need_fork_new_process_with_fl(path) != 0) {
//            LOGD("file has locked by another process");
//            return;
//        } else {
//            LOGD("file has locked ok");
//        }
//    }
    string APP_OBSERVED_FILE = path + "/files/observedFile";
    if (isProcessAlive(APP_OBSERVED_FILE.c_str()) == OK) {
        LOGD("watch process already exists");
        return;
    }


    // 若被监听文件存在，删除
    FILE *p_observedFile = fopen(APP_OBSERVED_FILE.c_str(), "r");
    if (p_observedFile != NULL) {
        LOGD("delete observed file");
        remove(APP_OBSERVED_FILE.c_str());
        fclose(p_observedFile);
    }

    // 若被监听文件存在，删除
    string APP_LOCK_FILE = path + "/files/lockFile";
    FILE *p_LockedFile = fopen(APP_LOCK_FILE.c_str(), "r");
    if (p_LockedFile != NULL) {
        LOGD("delete lock file");
        remove(APP_LOCK_FILE.c_str());
        fclose(p_LockedFile);
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
//        int fd = inotify_init();
//        if (fd < 0) {
//            LOGD("inotify_init failed !!!");
//            exit(1);
//        }
//
//        //添加inotify监听器
//        int wd = inotify_add_watch(fd, path.c_str(), IN_DELETE);
//        if (wd < 0) {
//            LOGD("inotify_add_watch failed !!!");
//            exit(1);
//        }

        //分配缓存，以便读取event，缓存大小=一个struct inotify_event的大小，这样一次处理一个event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOGD("malloc failed !!!");
            exit(1);
        }

        if (startObserver(p_buf, path) != OK) {
            return;
        }

        writePidFile(APP_OBSERVED_FILE.c_str());

        LOGD("start uninstall observer");
        while (JNI_TRUE) {
            //开始监听
            int fd = fileDescriptor;
            ssize_t readBytes = read(fd, p_buf, sizeof(struct inotify_event));
            string APP_DIR = path;
            if (IN_DELETE_SELF == ((struct inotify_event *) p_buf)->mask) {
                LOGD("IN_DELETE_SELF");
                // 若文件被删除，可能是已卸载，还需进一步判断app文件夹是否存在
                FILE *p_appDir = fopen(APP_DIR.c_str(), "r");
//                if (p_appDir != NULL) {
//                    // 应用主目录还在（可能还没有来得及清除），sleep一段时间后再判断
//                    sleep(5);
//                    p_appDir = fopen(APP_DIR.c_str(), "r");
//                }
                // 确认已卸载
                if (p_appDir == NULL) {
                    LOGD("inotify rm watch");
                    inotify_rm_watch(fileDescriptor, watchDescriptor);
                    break;
                } else {  // 未卸载，可能用户执行了"清除数据"
                    LOGD("not uninstall");
                    fclose(p_appDir);
                    // 应用没有卸载，重新监听
                    if (startObserver(p_buf, path) != 0) {
                        return;
                    }
                }

            } else {
                LOGD("NOT IN_DELETE_SELF");
            }
            //read会阻塞进程，走到这里说明收到目录被删除的事件
//            path += "/lib";
//            FILE *libDir = fopen(path.c_str(), "r");//判断该目录是否存在,如果存在则是覆盖安装
//            //read会阻塞进程，走到这里说明收到目录被删除的事件，注销监听器
//            if (libDir == NULL) {
//                inotify_rm_watch(fd, IN_DELETE);
//                free(p_buf);
//                p_buf = NULL;
//
//                char *url = NULL;
//                jclass obj_cls = env->GetObjectClass(obj);
//                if (obj_cls != NULL) {
//
//                    jmethodID getUrl = env->GetMethodID(obj_cls, "getUrl", "()Ljava/lang/String;");
//                    jstring jurl = (jstring) env->CallObjectMethod(obj, getUrl);
//                    if (jurl != NULL) {
//                        url = Jstring2CStr(env, jurl);
//                    }
//                    LOGD("start observer url=%s", url);
//                }
//
//                // 应用被卸载了，通知系统打开用户反馈的网页
//                LOGD("app uninstall,current sdkversion = %d", sdkVersion);
//                int ret = -1;
//                if (url != NULL && sdkVersion >= 17) {
//                    // Android4.2系统之后支持多用户操作，所以得指定用户
//                    ret = execlp("am", "am", "start", "--user", "0", "-a",
//                                 "android.intent.action.VIEW", "-d", url, (char *) NULL);
//                } else {
//                    // Android4.2以前的版本无需指定用户
//                    ret = execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
//                                 "-d", url, (char *) NULL);
//                }
//            } else {
//                // 覆盖安装
//                LOGD("app reinstall");
//                fclose(libDir);
//                free(p_buf);
//                p_buf = NULL;
//                exit(0);
//            }
        }

        remove(APP_OBSERVED_FILE.c_str());
        remove(APP_LOCK_FILE.c_str());
        if (p_buf != NULL) {
            free(p_buf);
        }

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

        LOGE("execlp ret=%d" + ret);
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