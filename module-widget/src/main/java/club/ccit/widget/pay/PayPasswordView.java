package club.ccit.widget.pay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.ccit.widget.R;

/**
 * FileName: PayPassView
 *
 * @author: 张帅威
 * Date: 2022/2/9 10:36 上午
 * Description:
 * Version:
 */
public class PayPasswordView extends RelativeLayout {
    private Context mContext;
    /**
     * 支付键盘
     */
    private GridView mGridView;
    /**
     * 保存密码
     */
    private String strPass = "";
    /**
     * 密码
     */
    private StringBuffer stringBuffer = new StringBuffer();
    /**
     * 关闭
     */
    private ImageView mImageViewClose;
    /**
     * 输入框
     */
    private PayPassword payPassword;
    /**
     * 忘记密码
     */
    private TextView mTvForget;
    /**
     * 提示 (提示:密码错误,重新输入)
     */
    private TextView mTvHint;
    /**
     * 1,2,3---0
     */
    private List<Integer> listNumber;
    /**
     * 布局
     */
    private View mPassLayout;
    private boolean isRandom;
    /**
     * 密码长度
     */
    private int passwordCount;
    /**
     * 按钮对外接口
     */
    public static interface OnPayClickListener {
        /**
         * 输入完成
         * @param password 输入的密码
         */
        void onPassFinish(String password);
        /**
         * 关闭
         */
        void onPayClose();
        /**
         * 找回密码
         */
        void onPayForget();
    }

    private OnPayClickListener mPayClickListener;

    public void setPayClickListener(OnPayClickListener listener) {
        mPayClickListener = listener;
    }

    public PayPasswordView(Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用的时候调用,多个样式文件
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PayPasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在布局文件中使用的时候调用
     *
     * @param context
     * @param attrs
     */
    public PayPasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //初始化
        initView(context, attrs);
        //将子布局添加到父容器,才显示控件
        this.addView(mPassLayout);
    }

    /**
     * 初始化
     */
    private void initView(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.inputBox);
        passwordCount = ta.getInteger(R.styleable.inputBox_passwordCount, 6);
        mPassLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_pay, null);
        //关闭
        mImageViewClose = (ImageView) mPassLayout.findViewById(R.id.closeImageView);
        //忘记密码
        mTvForget = (TextView) mPassLayout.findViewById(R.id.TextView);
        //提示文字
        mTvHint = (TextView) mPassLayout.findViewById(R.id.payTitleTextView);
        mGridView = (GridView) mPassLayout.findViewById(R.id.keyboardGridView);
        payPassword = mPassLayout.findViewById(R.id.payPassword);
        // 关闭点击事件
        mImageViewClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanAllTv();
                mPayClickListener.onPayClose();
            }
        });
        /**
         * 忘记密码点击事件
         */
        mTvForget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayClickListener.onPayForget();
            }
        });
        //初始化数据
        initData();
    }

    /**
     * isRandom是否开启随机数
     */
    private void initData() {
        if (isRandom) {
            listNumber = new ArrayList<>();
            listNumber.clear();
            for (int i = 0; i <= 10; i++) {
                listNumber.add(i);
            }
            //此方法是打乱顺序
            Collections.shuffle(listNumber);
            for (int i = 0; i <= 10; i++) {
                if (listNumber.get(i) == 10) {
                    listNumber.remove(i);
                    listNumber.add(9, 10);
                }
            }
            listNumber.add(R.mipmap.ic_pay_del0);
        } else {
            listNumber = new ArrayList<>();
            listNumber.clear();
            for (int i = 1; i <= 9; i++) {
                listNumber.add(i);
            }
            listNumber.add(10);
            listNumber.add(0);
            listNumber.add(R.mipmap.ic_pay_del0);
        }
        mGridView.setAdapter(adapter);
    }


    /**
     * GridView的适配器
     */

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listNumber.size();
        }

        @Override
        public Object getItem(int position) {
            return listNumber.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"ClickableViewAccessibility", "SetTextI18n", "NewApi"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_password_gridview, null);
                holder = new ViewHolder();
                holder.btnNumber = (TextView) convertView.findViewById(R.id.btNumber);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-------------设置数据----------------
            holder.btnNumber.setText(listNumber.get(position) + "");
            if (position == 9) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundColor(mContext.getResources().getColor(R.color.gray,null));
            }
            if (position == 11) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundResource(listNumber.get(position));
            }
            //监听清除点击事件
            if (position == 11) {
                holder.btnNumber.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del1);
                            break;
                        case MotionEvent.ACTION_UP:
                            holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del0);
                            break;
                        default:
                    }
                    return false;
                });
            }
            // 监听键盘点击事件
            holder.btnNumber.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //0-9按钮
                    if (position < 9) {
                        if (strPass.length() == 6) {
                            return;
                        } else {
                            stringBuffer.append(position+1);
                            payPassword.setPassword(stringBuffer);
                            //输入完成
                            if (strPass.length() == 6) {
                                //请求服务器验证密码
                                mPayClickListener.onPassFinish(strPass);
                            }
                        }
                    }else if (position == 10){
                        // 点击了 0
                        stringBuffer.append(0);
                        payPassword.setPassword(stringBuffer);
                    }
                    else if (position == 11) {
                        //删除
                        if (stringBuffer.length() > 0){
                            stringBuffer.deleteCharAt(stringBuffer.length()-1);
                            payPassword.setPassword(stringBuffer);
                        }
                    }
                    if (stringBuffer.length() == passwordCount){
                        mPayClickListener.onPassFinish(stringBuffer.toString());
                        Log.i("LOG111",stringBuffer.toString());
                    }
                    if (stringBuffer.length() < passwordCount){
                        setInputStyle();
                    }
                }
            });

            return convertView;
        }
    };

    static class ViewHolder {
        public TextView btnNumber;
    }

    /***
     * 设置随机数
     * @param random
     */
    public PayPasswordView setRandomNumber(boolean random) {
        isRandom = random;
        initData();
        adapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 关闭图片
     * 资源方式
     */
    public PayPasswordView setCloseImgView(int resId) {
        mImageViewClose.setImageResource(resId);
        return this;
    }

    /**
     * 关闭图片
     * Bitmap方式
     */
    public PayPasswordView setCloseImgView(Bitmap bitmap) {
        mImageViewClose.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 关闭图片
     * drawable方式
     */
    public PayPasswordView setCloseImgView(Drawable drawable) {
        mImageViewClose.setImageDrawable(drawable);
        return this;
    }


    /**
     * 设置忘记密码文字
     */
    public PayPasswordView setForgetText(String text) {
        mTvForget.setText(text);
        return this;
    }

    /**
     * 设置忘记密码文字大小
     */
    public PayPasswordView setForgetSize(float textSize) {
        mTvForget.setTextSize(textSize);
        return this;
    }

    /**
     * 设置忘记密码文字颜色
     */
    public PayPasswordView setForgetColor(int textColor) {
        mTvForget.setTextColor(textColor);
        return this;
    }

    /**
     * 设置提醒的文字
     */
    public PayPasswordView setHintText(String text) {
        mTvHint.setText(text);
        return this;
    }

    /**
     * 设置错误提醒
     */
    public PayPasswordView setErrorStyle() {
        mTvHint.setText("密码错误，请重新输入");
        mTvHint.setTextColor(getResources().getColor(R.color.red));
        payPassword.setErrorStyle();
        return this;
    }

    /**
     * 设置正在输入的Style
     */
    public PayPasswordView setInputStyle() {
        mTvHint.setText("请输入支付密码");
        mTvHint.setTextColor(getResources().getColor(R.color.black));
        payPassword.setInputStyle();
        return this;
    }

    /**
     * 设置提醒的文字大小
     */
    public PayPasswordView setTvHintSize(float textSize) {
        mTvHint.setTextSize(textSize);
        return this;
    }

    /**
     * 设置提醒的文字颜色
     */
    public PayPasswordView setTvHintColor(int textColor) {
        mTvHint.setTextColor(textColor);
        return this;
    }

    /**
     * 清楚所有密码TextView
     */
    public PayPasswordView cleanAllTv() {
      if (payPassword != null){
          payPassword.clearPassword();
      }
        return this;
    }

    /**
     * 获取密码
     * @return
     */
    public CharSequence getPassword() {
        return payPassword.getPassword();
    }
}