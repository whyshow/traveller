package club.ccit.home.ui;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseActivity;
import club.ccit.home.adapter.MyAdapter;
import club.ccit.home.databinding.ActivityGridViewBinding;

/**
 * FileName: GridViewActivity
 *
 * @author: mosaic
 * Date: 2023/2/15 08:01
 * Description:
 * Version:
 */
public class GridViewActivity extends BaseActivity<ActivityGridViewBinding> {
    private final String URL_IMG = "https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg";
    private final String URL_IMG_2 = "https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg";
    private List<List<String>> mList = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate() {
        super.onCreate();

        List<String> urlList = new ArrayList<>();//图片url
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");
        urlList.add("https://pic.hm5988.com/eb250459-b6f2-424f-9588-93e225308093.jpg");


        List<String> testList = new ArrayList<>();
        testList.add(URL_IMG_2);
        for (int i = 0; i < 2; i++) {
            int count = i % 9 + 1;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                list.add(URL_IMG);
            }
            if (i % 8 == 0) {
                mList.add(testList);
            }
            mList.add(list);
        }

        mAdapter = new MyAdapter(urlList, this);
        binding.gridRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gridRecyclerView.setAdapter(mAdapter);

    }
}
