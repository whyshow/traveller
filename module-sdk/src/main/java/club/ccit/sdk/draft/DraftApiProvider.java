package club.ccit.sdk.draft;

import android.content.Context;

import club.ccit.sdk.net.AppApiProvider;

/**
 * FileName: DraftApiProvider
 *
 * @author: 张帅威
 * Date: 2021/12/3 2:25 下午
 * Description:
 * Version:
 */
public class DraftApiProvider extends AppApiProvider {
    /**
     * 实例化一些连接网络配置
     *
     * @param context
     */
    public DraftApiProvider(Context context) {
        super(context);
    }

    @Override
    protected String baseUrl() {
        return "http://api.ccit.club/";
    }

    public NewsApi getNewsList() {
        return getRetrofit().create(NewsApi.class);
    }

}
