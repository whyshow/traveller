package club.ccit.room.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.room.databinding.ItemRoomBinding;
import club.ccit.room.model.TestDataModel;

/**
 * FileName: RoomAdapter
 *
 * @author: 张帅威
 * Date: 2022/2/24 8:51 上午
 * Description:
 * Version:
 */
public class RoomAdapter extends BaseAdapter<ItemRoomBinding> {
    public RoomAdapter(List list) {
        this.list = list;
    }

    @Override
    protected void onBindingViewData(RecyclerView.ViewHolder holder, int position) {
        TestDataModel testDataModel = (TestDataModel) list.get(position);
        binding.ageTextView.setText("年龄："+testDataModel.getTestAge());
        binding.idTextView.setText("编号："+testDataModel.getTestId());
        binding.nameTextView.setText("名字："+testDataModel.getTestName());
    }

    @Override
    protected ItemRoomBinding getViewBinding(ViewGroup parent) {
        return ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }
}
