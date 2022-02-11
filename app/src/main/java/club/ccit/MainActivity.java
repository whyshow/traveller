package club.ccit;

import android.os.Bundle;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.alibaba.android.arouter.facade.annotation.Route;
import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;

import club.ccit.databinding.ActivityMainBinding;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 13:17
 * Description:
 * Version:
 */
@Route(path = AppRouter.PATH_APP_HOME)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除item长按显示 toast
        cleanToast();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_drafts, R.id.navigation_my)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /**
     * 去除item长按显示 toast
     */
    private void cleanToast() {
        View bottomBarView = binding.navView.getChildAt(0);
        bottomBarView.findViewById(R.id.navigation_home).setOnLongClickListener(v -> true);
        bottomBarView.findViewById(R.id.navigation_drafts).setOnLongClickListener(v -> true);
        bottomBarView.findViewById(R.id.navigation_my).setOnLongClickListener(v -> true);
    }

    @Override
    protected void initView() {
        super.initView();
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