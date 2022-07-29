package club.ccit.common;

/**
 * @author: 张帅威
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
