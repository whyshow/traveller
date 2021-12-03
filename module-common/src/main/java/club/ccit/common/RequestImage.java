package club.ccit.common;

import com.bumptech.glide.Glide;

import club.ccit.widget.CustomImageView;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/18 16:24
 * Description:
 * Version:
 */
public class RequestImage {
    public static void start(String url, CustomImageView imageView){
        if (!url.isEmpty()){
                Glide.with(Constant.getApplication())
                        .load(url)
                        .into(imageView);
        }
    }
}
