package club.ccit.common;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 16:25
 * Description:
 * Version:
 */
public class Constant {

    private static BaseApplication application = null;

    public static BaseApplication getApplication() {
        return application;
    }

    public static void setApplication(BaseApplication application) {
        Constant.application = application;
    }
}
