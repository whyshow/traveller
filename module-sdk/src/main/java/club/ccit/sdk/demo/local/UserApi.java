package club.ccit.sdk.demo.local;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

/**
 * FileName: UserApi
 *
 * @author: 张帅威
 * Date: 2021/12/19 2:12 下午
 * Description:
 * Version:
 */
public interface UserApi {

    /**
     * 获取本地的数据
     * @return
     */
    @GET("/")
    Observable<User> getUser();
}
