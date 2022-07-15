#include <jni.h>
#include <sys/socket.h>

extern "C" {

	JNIEXPORT jint JNICALL
    Java_com_shangyun_p2ptester_Util_P2PUtil_getSocketType (
    JNIEnv *env, jobject obj, jint socket_fd)
    {
        int type;
        socklen_t t = sizeof(int);
        if (getsockopt(socket_fd, SOL_SOCKET, SO_TYPE, &type, &t) < 0)
        {
            return -1;
        }
        if (type == SOCK_STREAM) return 1;
        else return 0;
    }
}



