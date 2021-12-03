package club.ccit.common;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 16:25
 * Description:
 * Version:
 */
public class Constant {
    private static VolleyJson volleyJson = null;

    public static VolleyJson getVolleyJson() {
        return volleyJson;
    }

    public static void setVolleyJson(VolleyJson volleyJson) {
        Constant.volleyJson = volleyJson;
    }

    private static MyApplication application = null;

    public static MyApplication getApplication() {
        return application;
    }

    public static void setApplication(MyApplication application) {
        Constant.application = application;
    }
}
