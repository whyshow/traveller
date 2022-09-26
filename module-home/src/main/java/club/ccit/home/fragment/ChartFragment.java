package club.ccit.home.fragment;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import club.ccit.basic.BaseFragment;
import club.ccit.common.LogUtils;
import club.ccit.home.R;
import club.ccit.home.databinding.FragmentChartBinding;


/**
 * @author: 张帅威
 * Date: 2021/12/3 8:50 上午
 * Description:
 * Version: 1.0 版本
 */
public class ChartFragment extends BaseFragment<FragmentChartBinding> {

    WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate() {
        super.onCreate();
        webView = findViewById(R.id.webView);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        // webView.loadUrl("www.baidu.com");
        webView.loadUrl("file:///android_asset/echarts.html");
        webView.addJavascriptInterface(new ToAndroid(), "toAndroid");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.i("shouldOverrideUrlLoading");
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.i("onPageFinished");
                // html加载完成
                String x = "[\"06-16\",\"08-21\",\"08-28\",\"09-04\",\"09-11\",\"09-18\"]";
                String y = "[200.0,5340000.0,43000.0,3300.0,6300000.0,300.0]";
                String call = "javascript:loadData( '紫薇'" + "," + x + "," + y + ")";
                //LogUtils.i(call);
                view.loadUrl(call);
            }
        });

        binding.webView2.loadUrl("file:///android_asset/webview.html");
        binding.webView2.addJavascriptInterface(new FromJs(), "testJs");
        binding.webView2.getSettings().setJavaScriptEnabled(true);
    }

    public class FromJs {
        @JavascriptInterface
        public void toast() {
            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
        }
    }

     public class ToAndroid {
        @JavascriptInterface
        public void toast(String name, String x, String y) {
            Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
            LogUtils.i("name:"+name+"X:"+x.toString()+"Y:"+y.toString());
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
