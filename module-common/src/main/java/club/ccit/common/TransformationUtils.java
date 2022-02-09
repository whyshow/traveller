package club.ccit.common;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * FileName: TransformationUtils
 *
 * @author: 张帅威
 * Date: 2022/1/26 3:34 下午
 * Description:
 * Version:
 */
public class TransformationUtils extends ImageViewTarget<Bitmap> {

    private ImageView target;

    public TransformationUtils(ImageView target) {
        super(target);
        this.target = target;
    }

    @Override
    protected void setResource(Bitmap resource) {
        view.setImageBitmap(resource);
        if (resource != null){
            //获取原图的宽高
            int width = resource.getWidth();
            int height = resource.getHeight();

            //获取imageView的宽
            int imageViewWidth = target.getWidth();

            //计算缩放比例
            float sy = (float) (imageViewWidth * 1.1) / (float) (width * 1.1);

            //计算图片等比例放大后的高
            int imageViewHeight = (int) (height * sy);
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = imageViewHeight;
            target.setLayoutParams(params);
        }
    }
}
