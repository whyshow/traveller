package club.ccit.room.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.basic.action.AdapterAction;
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
    public RoomAdapter(List list, AdapterAction action) {
        this.list = list;
        this.action = action;
    }

    @Override
    protected void onBindingViewData(BaseAdapter<ItemRoomBinding>.ViewHolder holder, int position) {
        TestDataModel testDataModel = (TestDataModel) list.get(position);
        holder.itemBinding.ageTextView.setText("年龄："+testDataModel.getTestAge());
        holder.itemBinding.idTextView.setText("编号："+testDataModel.getTestId());
        holder.itemBinding.nameTextView.setText("名字："+testDataModel.getTestName());
    }

    @Override
    protected ItemRoomBinding getViewBinding(ViewGroup parent) {
        return ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }
}
