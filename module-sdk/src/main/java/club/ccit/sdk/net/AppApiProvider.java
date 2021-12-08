package club.ccit.sdk.net;

import android.content.Context;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/1 4:37 下午
 * Description: 请求网络
 * Version:
 */
public abstract class AppApiProvider {
    protected Retrofit mRetrofit;
    private final WeakReference<Context> mContext;

    /**
     * 实例化一些连接网络配置
     * @param context 上下文
     */
   public AppApiProvider(Context context) {
        mContext = new WeakReference<>(context.getApplicationContext());
        OkHttpClient builder = new OkHttpClient();
        OkHttpClient client = builder.newBuilder()
                //  连接超时
                .connectTimeout(30, TimeUnit.SECONDS)
                // 流读取超时
                .readTimeout(120, TimeUnit.SECONDS)
                // 流写入超时
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                // 加入配置
                .client(client)
                // 基本的域名
                .baseUrl(baseUrl())
                // 绑定RxJava
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // 使用gson 自动解析数据
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取 Retrofit 实例
     * @return Retrofit
     */
    protected Retrofit getRetrofit() {
        return mRetrofit;
    }


    /**
     * 设置域名
     * @return 域名
     */
    protected abstract String baseUrl();

    @Nullable
    protected Context getContext() {
        return mContext.get();
    }


}
