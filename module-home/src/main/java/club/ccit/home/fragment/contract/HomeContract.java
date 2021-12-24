package club.ccit.home.fragment.contract;

/**
 * FileName: HomeContract
 *
 * @author: 张帅威
 * Date: 2021/12/20 8:40 上午
 * Description:
 * Version:
 */
public interface HomeContract {
    interface View {
        /**
         * 请求数据成功
         * @param object 请求网络数据成功，强转 Object获取相关数据
         */
        void requestSuccess(Object object);

        /**
         * 请求数据失败
         * @param msg 回调的错误信息
         */
        void requestFailure(String msg);
    }

    interface Presenter {
        /**
         * 请求数据
         * @param object 发送网络请求，强转Object数据到相关实体类以便获取相关数据
         */
        void requestData(Object object);

        /**
         * 请求数据成功
         * @param object 请求网络数据成功，强转 Object获取相关数据
         */
        void requestSuccess(Object object);

        /**
         * 请求数据失败
         * @param msg 回调的错误信息
         */
        void requestFailure(String msg);

        /**
         * View 结束回调
         */
        void onDestroy();
    }

    interface Model {
        /**
         * 请求数据
         * @param object 发送网络请求，强转Object数据到相关实体类以便获取相关数据
         */
        void requestData(Object object);
        /**
         * View 结束回调
         */
        void onDestroy();
    }
}
