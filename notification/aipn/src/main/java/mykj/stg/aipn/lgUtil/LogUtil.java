package mykj.stg.aipn.lgUtil;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;

public class LogUtil {
	
    public static String className="LogUtil";

    public static int v(String msg) {
        return Log.v(className, addTimeString(msg));
    }

    public static int d(String msg) {
        return Log.d(className, addTimeString(msg));
    }

    public static int i(String msg) {
        return Log.i(className, addTimeString(msg));
    }

    public static int w(String msg) {
        return Log.w(className, addTimeString(msg));
    }

    public static int e(String msg) {
        return Log.e(className, addTimeString(msg));
    }

    @SuppressLint("SimpleDateFormat")
    private static String addTimeString(String msg) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timeString = format.format(System.currentTimeMillis());
        return "[" + timeString + "]" + msg;
    }
}
