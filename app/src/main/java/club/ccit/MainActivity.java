package club.ccit;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;
import club.ccit.common.Constant;

import club.ccit.common.VolleyJson.OnResponseSucceedListener;
import club.ccit.databinding.ActivityMainBinding;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;

/**
 * @author swzhang3
 */
@Route(path = AppRouter.PATH_APP_HOME)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // RequestImage.start("https://pic.miaoyibao.cn/534ba315-1e02-4140-9601-e8799f3d7232.jpg",binding.MainImageView);
       MessageDialog.show(this, "提示", "确认签署合同？", "确认", "取消", new MessageDialog.OnDialogButtonClickListener() {
           @Override
           public boolean onClick(Dialog baseDialog, View v) {
               myToast("你点击了确认");
               return false;
           }
       });
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.HomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation();

            }
        });

    }

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }

}