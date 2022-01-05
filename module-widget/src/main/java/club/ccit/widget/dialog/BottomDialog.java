package club.ccit.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.ccit.widget.R;

/**
 * FileName: BottomDialog
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/17 9:58 上午
 * Description: 底部弹窗选择
 * Version:
 */
public class BottomDialog {
    private static String[] strings;
    private static int[] pics;
    /**
     * 图片和文本数据
     */
    private static List<Map<String, Object>> dataList;

    public static void show(Context context, String[] text, OnBottomClickListener itemClickListener) {
        strings = text;
        dataList = new ArrayList<Map<String, Object>>();
        // 创建适配器
        SimpleAdapter adapter = new SimpleAdapter(context,
                getData(),
                R.layout.item_bottom,
                //这里一定要写对应map的key
                new String[]{"name"},
                //TextView对应的id
                new int[]{ R.id.bottomMenuTextView});
        // 创建视图
        View view = LayoutInflater.from(context)
                .inflate(R.layout.bottom_dialog, null);
        // 绑定listview 控件
        ListView listView = view.findViewById(R.id.bottomMenu);
        // 设置适配器
        listView.setAdapter(adapter);
        // 创建对话框
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        // 设置对话框视图
        dialog.setContentView(view);
        //设置弹窗不可以点击背景取消
        dialog.setCancelable(true);
        // 设置背景是透明的以显示圆角
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //展示弹窗
        dialog.show();
        // 设置监听
        clickListener(listView,view, dialog, itemClickListener);
    }

    /**
     * 加入了图片
     * @param context
     * @param text
     * @param pic
     * @param itemClickListener
     */
    public static void show(Context context, String[] text, int[] pic, OnBottomClickListener itemClickListener) {
        strings = text;
        pics = pic;
        dataList = new ArrayList<Map<String, Object>>();
        // 创建适配器
        SimpleAdapter adapter = new SimpleAdapter(context,
                getData(),
                R.layout.item_pic_bottom,
                //这里一定要写对应map的key
                new String[]{"pic", "name"},
                //ImageView和TextView对应的id
                new int[]{R.id.bottomMenuImageView, R.id.bottomMenuTextView});
        // 创建视图
       View view = LayoutInflater.from(context)
                .inflate(R.layout.bottom_dialog, null);
        // 绑定listview 控件
        ListView listView = view.findViewById(R.id.bottomMenu);
        // 设置适配器
        listView.setAdapter(adapter);
        // 创建对话框
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        // 设置对话框视图
        dialog.setContentView(view);
        //设置弹窗不可以点击背景取消
        dialog.setCancelable(true);
        // 设置背景是透明的以显示圆角
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //展示弹窗
        dialog.show();
        // 设置监听
        clickListener(listView, view,dialog, itemClickListener);
    }

    /**
     * 点击事件
     * @param listView
     * @param view
     * @param dialog
     * @param itemClickListener
     */
    private static void clickListener(ListView listView, View view, BottomSheetDialog dialog, OnBottomClickListener itemClickListener) {
        //为每个功能绑定单击事件
        listView.setOnItemClickListener((adapterView, v, i, l) -> {
            itemClickListener.onClick(strings[i],i);
            //点击事件后关闭弹窗
            dialog.dismiss();
        });

        TextView cancelTextView = view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(v -> {
            dialog.dismiss();
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
