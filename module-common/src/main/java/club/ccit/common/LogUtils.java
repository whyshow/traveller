package club.ccit.common;

import android.util.Log;

/**
 * Copyright (C), 2011-2021, 万臻生态科技有限公司
 * FileName: LogUtils
 *
 * @author swzhang
 * Date: 2021/6/20 10:11 下午
 * Description: 打印LOG日志
 * History:
 */
public class LogUtils {
    public static void i(String message){
        if (Config.isLog){
            if (message == null){
                Log.i("LOG111","");
            }else {
                Log.i("LOG111",message);
            }
        }
    }
}
