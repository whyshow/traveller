package club.ccit.common;

import android.util.Log;

/**
 * FileName: LogUtils
 *
 * @author swzhang
 * Date: 2021/6/20 10:11 下午
 * Description: 打印LOG日志
 * History:
 */
public class LogUtils {
    public static void i(String message){
        if (BuildConfig.DEBUG){
            if (message == null){
                Log.i("LOG111","null");
            }else {
                Log.i("LOG111",message);
            }
        }
    }
}
