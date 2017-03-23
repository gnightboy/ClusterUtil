package me.xiaohuai.clusterutil.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;

/**
 * 应用入口(初始化地图)
 *
 * @author XiaoHuai
 */
public class ClusterUtilApplication extends Application {
    /**
     * 常量：进程名
     */
    public static final String APP_PROCESS_NAME = "me.xiaohuai.clusterutil";

    @Override
    public void onCreate() {
        super.onCreate();
        String appName = getCurProcessName(getApplicationContext());
        if (!TextUtils.isEmpty(appName)) {
            //防止初始化两次耗资源
            if (appName.equalsIgnoreCase(APP_PROCESS_NAME)) {
                //初始化百度地图
                SDKInitializer.initialize(getApplicationContext());
            }
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context 上下文
     * @return 进程号(me.xiaohuai.clusterutil)
     */
    private String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
