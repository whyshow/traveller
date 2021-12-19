package club.ccit.sdk.demo.local;

import club.ccit.sdk.net.BaseApiProvider;

/**
 * FileName: UserApiProvider
 *
 * @author: 张帅威
 * Date: 2021/12/19 2:14 下午
 * Description:
 * Version:
 */
public class UserApiProvider extends BaseApiProvider {

    UserApi userApi;

    /**
     * 实例化一些连接网络配置
     */
    public UserApiProvider() {
        super();
        // 创建新闻API
        userApi = getRetrofit().create(UserApi.class);
    }

    @Override
    protected String baseUrl() {
        return "http://10.0.2.2:8090";
    }

    public UserApi getUserApi() {
        return userApi;
    }
}
