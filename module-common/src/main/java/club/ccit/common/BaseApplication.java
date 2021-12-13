package club.ccit.common;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 10:59
 * Description:
 * Version:
 */
public class BaseApplication extends Application{

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
    }

    private boolean isDebug() {
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception x) {
            return false;
        }
    }
}
