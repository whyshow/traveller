package club.ccit.sdk.demo;

import club.ccit.sdk.net.BaseApiProvider;

/**
 * FileName: DraftApiProvider
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 2:25 下午
 * Description:
 * Version:
 */
public class DraftApiProvider extends BaseApiProvider {

    NewsApi newsApi;
    /**
     * 实例化一些连接网络配置
     */
    public DraftApiProvider() {
        super();
        // 创建新闻API
        newsApi = getRetrofit().create(NewsApi.class);
    }

    @Override
    protected String baseUrl() {
        return "http://api.ccit.club/";
    }

    /**
     * 获取新闻列表
     * @return
     */
    public NewsApi getNewsList() {
        return newsApi;
    }

}
