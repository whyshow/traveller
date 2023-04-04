package club.ccit.room;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import club.ccit.basic.BaseActivity;
import club.ccit.basic.BaseAdapter;
import club.ccit.basic.BaseViewDataActivity;
import club.ccit.basic.action.AdapterAction;
import club.ccit.common.AppRouter;
import club.ccit.common.Constant;
import club.ccit.room.adapter.RoomAdapter;
import club.ccit.room.api.TestApi;
import club.ccit.room.databinding.ActivityRoomBinding;
import club.ccit.room.model.TestDataModel;
import club.ccit.room.viewModel.RoomViewModel;
import club.ccit.widget.title.OnTitleBarListener;
import club.ccit.widget.title.TitleBar;

/**
 * FileName: RoomActivity
 *
 * @author: 张帅威
 * Date: 2022/2/24 8:32 上午
 * Description: 数据库演示
 * Version:
 */
@Route(path = AppRouter.PATH_ROOM_ROOM)
public class RoomActivity extends BaseViewDataActivity<ActivityRoomBinding> {
    private RoomViewModel roomViewModel;
    private RoomAdapter adapter;
    private TestApi testApi;
    private List<TestDataModel> list;

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener(R.id.roomButton);
        roomViewModel = new ViewModelProvider(RoomActivity.this).get(RoomViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setData(roomViewModel);
        // 获取数据库数据
        initData();
        roomViewModel.success.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    TestApi testApi = AbstractRoomData.getRoomDataBase(Constant.getApplication()).getTestApi();
                    List<TestDataModel> list = testApi.queryAll();
                    showToast("添加成功");
                    adapter.putData(list,1);
                    adapter.setError(BaseAdapter.FOOTER_NO_DATA);
                }else {
                    showToast("请检查数据完整性");
                }
            }
        });

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        testApi= AbstractRoomData.getRoomDataBase(Constant.getApplication()).getTestApi();
        list = testApi.queryAll();
        binding.roomRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new RoomAdapter(list, new AdapterAction() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onNext() {

            }
        });
        binding.roomRecyclerView.setAdapter(adapter);
        adapter.setError(BaseAdapter.FOOTER_NO_DATA);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.roomButton){
            // 添加数据
            roomViewModel.addData();
        }
    }
}
