package club.ccit.sdk.net;

import android.util.Log;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 9:54 上午
 * Description: 接口回调 处理401 等代码
 * Version:
 */
public abstract class DefaultApiObserver<T> extends DisposableObserver<T> {

    private final int ERR401 = 401;

    @Override
    public void onNext(@NonNull T t) {
        succeed(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof HttpException) {
            HttpException ex = (HttpException) e;

            if (ex.code() == ERR401){
                // 业务逻辑
                try {
                    Log.i("LOG111",((HttpException) e).response().errorBody().string());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }
    }

    /**
     * 解除
     */
    @Override
    public void onComplete() {

    }

    /**
     * 请求数据成功
     * @param t
     */
    protected abstract void succeed(T t);

}
