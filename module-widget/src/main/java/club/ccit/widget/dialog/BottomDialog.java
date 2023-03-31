package club.ccit.widget.dialog;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.ccit.widget.R;
import club.ccit.widget.dialog.action.OnBottomClickListener;
import club.ccit.widget.dialog.base.BaseDialog;

/**
 * FileName: BottomDialog
 *
 * @author: 张帅威
 * Date: 2021/12/17 9:58 上午
 * Description: 底部弹窗选择
 * Version:
 */
public class BottomDialog extends BaseDialog{
    private static String[] strings;
    private static int[] pics;
    /** 图片和文本数据 */
    private static List<Map<String, Object>> dataList;
    private static Context context;
    private static OnBottomClickListener action;
    public static BottomDialog Builder;
    public BottomDialog(Context context,OnBottomClickListener itemClickListener) {
        super(context);
        action = itemClickListener;
        Builder = this;
    }

    @Override
    public void onDialogDismiss() {
        super.onDialogDismiss();
        action = null;
        Builder = null;
        context = null;
        Log.i("LOG111","onDialogDismiss");
    }

    @Override
    protected void initView() {
        dataList = new ArrayList<Map<String, Object>>();
        SimpleAdapter adapter = new SimpleAdapter(context,
                    getData(),
                    R.layout.item_pic_bottom,
                    //这里一定要写对应map的key
                    new String[]{"pic", "name"},
                    //ImageView和TextView对应的id
                    new int[]{R.id.bottomMenuImageView, R.id.bottomMenuTextView});
        // 绑定listview 控件
        ListView listView = findViewById(R.id.bottomMenu);
        // 设置适配器
        listView.setAdapter(adapter);
        clickListener(listView,action);
    }

    @Override
    protected boolean setDialogTransparent() {
        return true;
    }

    @Override
    protected int setDialogGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public boolean setDialogCancelable() {
        return true;
    }

    @Override
    protected int setDialogAnimations() {
        return ANIM_BOTTOM;
    }

    @Override
    protected int setWidth() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int setHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int setDialogBackgroundResId() {
        return R.drawable.radius_white_8;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.bottom_dialog;
    }

    public static BaseDialog Builder(Context c, String[] text, OnBottomClickListener itemClickListener){
        context = c;
        strings = text;
        new BottomDialog(context,itemClickListener);
        return Builder;
    }
      public static BaseDialog Builder(Context c, String[] text,int[] pic, OnBottomClickListener itemClickListener){
        context = c;
        strings = text;
        pics = pic;
        new BottomDialog(context,itemClickListener);
        return Builder;
    }

    /**
     * 点击事件
     * @param listView
     * @param itemClickListener
     */
    private void clickListener(ListView listView, OnBottomClickListener itemClickListener) {
        //为每个功能绑定单击事件
        listView.setOnItemClickListener((adapterView, v, i, l) -> {
            itemClickListener.onClick(strings[i],i);
            //点击事件后关闭弹窗
            onDialogDismiss();
        });

        TextView cancelTextView = findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(v -> {
            onDialogDismiss();
        });
    }

    /**
     * 生成数据
     * @return
     */
    private static List<Map<String, Object>> getData() {
        if (pics == null) {
            for (int i = 0; i < strings.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>(strings.length);
                map.put("name", strings[i]);
                dataList.add(map);
            }
        } else {
            for (int i = 0; i < strings.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>(strings.length);
                map.put("pic", pics[i]);
                map.put("name", strings[i]);
                dataList.add(map);
            }
        }
        return dataList;
    }
}
