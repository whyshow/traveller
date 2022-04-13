package club.ccit.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.tony.defenselib.DefenseCrash;
import com.android.tony.defenselib.handler.IExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 10:59
 * Description:
 * Version:
 */
public class BaseApplication extends Application {
    /**
     * 维护Activity 的list
     */
    private static final List<Activity> M_ACTIVITY = Collections.synchronizedList(new LinkedList<Activity>());
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.setApplication(this);
        if (isDebug()) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);
        // 注册Activity 的创建销毁的监听
        registerActivityListener();
    }

    private boolean isDebug() {
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception x) {
            return false;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // DefenseCrash.initialize(this);
        DefenseCrash.install(new IExceptionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCaughtException(Thread thread, Throwable throwable, boolean isSafeMode, boolean isCrashInChoreographer) throws Throwable {
                LogUtils.i("异常捕获原因：" + throwable.getLocalizedMessage());
                LogUtils.i("异常捕获位置" + Arrays.toString(Arrays.stream(throwable.getStackTrace()).toArray()));
            }
        });
    }

    /**
     * 注册Activity 监听
     */
    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull  Activity activity, @Nullable Bundle savedInstanceState) {
                pushActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull  Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull  Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull  Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull  Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull  Activity activity, @NonNull  Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull  Activity activity) {
                popActivity(activity);

            }
        });
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (M_ACTIVITY == null || M_ACTIVITY.isEmpty()) {
            return;
        }
        if (activity != null) {
            M_ACTIVITY.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (M_ACTIVITY.isEmpty()) {
            return;
        }
        for (Activity activity : M_ACTIVITY) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     *  只保留一个Activity
     * @param cls 包名+Activity名
     */
    public void saveOneActivity(Class<?> cls) {
        if (M_ACTIVITY.isEmpty()) {
            return;
        }
        for (Activity activity : M_ACTIVITY) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        M_ACTIVITY.remove(activity);
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (M_ACTIVITY != null) {
            for (Activity activity : M_ACTIVITY) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        M_ACTIVITY.add(activity);

    }
}
