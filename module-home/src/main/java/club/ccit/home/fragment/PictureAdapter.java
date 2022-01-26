package club.ccit.home.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.common.TransformationUtils;
import club.ccit.home.databinding.ItemPictureBinding;

/**
 * FileName: PictureAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/26 4:25 下午
 * Description:
 * Version:
 */
public class PictureAdapter extends BaseAdapter<ItemPictureBinding> {
    private Context context;

    public PictureAdapter(Context context, List<String> picture) {
        this.context = context;
        list = picture;
    }

    @Override
    protected void onBindingViewData(int position) {
        Glide.with(context)
                .asBitmap()
                .load(list.get(position))
                .into(new TransformationUtils(binding.customImageView));
    }

    @Override
    protected ItemPictureBinding getViewBinding(ViewGroup parent) {
        return ItemPictureBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }
}
