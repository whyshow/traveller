package club.ccit.common;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import club.ccit.widget.dialog.WaitDialog;

/**
 * @author: 张帅威
 * Date: 2021/11/18 16:07
 * Description: 网络请求封装
 * Version:
 */
public class VolleyJson {
    private RequestQueue mQueue;
    private HashMap<String, String> headers;
    private Context context;

    public VolleyJson(Context context) {
        this.context = context;
        this.mQueue = Volley.newRequestQueue(context);
    }
    public void post(String url, JSONObject params, final OnResponseSucceedListener onResponseListener) {

        JsonObjectRequest request = new JsonObjectRequest(1, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // 响应成功
                onResponseListener.onRequestSucceed(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 请求失败
                onResponseListener.onRequestError(volleyError);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                // 添加 header参数
                if (headers == null) {
                    headers = new HashMap(5);
                }
                headers.clear();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("client-id", "");
                headers.put("Authorization", "");
                return headers;
            }
        };
        this.mQueue.add(request);
    }

    public void get(String url, final OnResponseSucceedListener onResponseListener) {
        JsonObjectRequest request = new JsonObjectRequest(0, url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onResponseListener.onRequestSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResponseListener.onRequestError(error);
            }
        });
        this.mQueue.add(request);
    }

    /**
     * 回调接口
     */
    public interface OnResponseSucceedListener {
        /**
         * 请求数据失成功
         *
         * @param response
         */
        void onRequestSucceed(JSONObject response);

        /**
         * 请求数据失败
         *
         * @param error
         */
        void onRequestError(VolleyError error);

    }

}
