package club.ccit.home.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.common.LogUtils;
import club.ccit.common.TransformationUtils;
import club.ccit.home.HomeActivity;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.banner.base.RecyclerViewBannerBase;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        String url = "https://pic.tiantu.ccit.club/6845451.jpg?imageMogr2/auto-orient/thumbnail/300x/format/jpg/blur/1x0/quality/60";
        String url2 = "https://pic.tiantu.ccit.club/6773800.jpg?imageMogr2/auto-orient/thumbnail/300x/format/jpg/blur/1x0/quality/60";
        List<String> picture = new ArrayList<>();
        picture.add(url);
        picture.add(url2);
        picture.add(url);
        picture.add(url2);
        picture.add(url);
        picture.add(url2);
        picture.add(url);
        picture.add(url2);
        picture.add(url);
        picture.add(url2);
        binding.homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        PictureAdapter adapter = new PictureAdapter(requireContext(), picture);
        binding.homeRecyclerView.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=3184221534,2238244948&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1794621527,1964098559&fm=27&gp=0.jpg");
        binding.recyclerViewBanner.initBannerImageView(list, new RecyclerViewBannerBase.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(requireActivity(), "clicked:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 进入相机预览
        binding.cameraActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation());
        // 录制视频
        binding.videoActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation());

        binding.startHomeActivity.setOnClickListener(view ->
                // ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation()
                HomeActivity.launch(requireActivity(), "HomeFragment 启动的")
        );

        binding.startBottomDialog.setOnClickListener(view -> {
            String[] phone = {"你", "好", "呀"};
            BottomDialog.show(getActivity(), phone, (text, index) -> myToast(text));
        });

        binding.startDialog.setOnClickListener(view -> MessageDialog.show(getActivity(), "提示", "你点击了对话框", (baseDialog, v) -> {
            MessageDialog.onDismiss();
            return false;
        }));

        binding.startWaitDialog.setOnClickListener(view -> WaitDialog.show(getActivity(), "请稍后..."));
    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause");
    }
}
