package club.ccit.sdk;

import android.util.Log;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 9:54 上午
 * Description: 接口回调 处理401 等代码
 * Version:
 */
public abstract class BaseApiDefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(@NonNull T t) {
        accept(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof HttpException) {
            HttpException ex = (HttpException) e;
            Log.i("LOG111",ex.toString());
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void accept(T t);
}
