package club.ccit.widget.dialog.city;

import android.content.Context;

import club.ccit.widget.R;
import club.ccit.widget.dialog.city.bean.CityBean;
import club.ccit.widget.dialog.city.bean.DistrictBean;
import club.ccit.widget.dialog.city.bean.ProvinceBean;
import club.ccit.widget.dialog.city.citywhee.CityConfig;
import club.ccit.widget.dialog.city.listener.OnCityItemClickListener;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class Provinces {
    /** 申明对象 **/
    private CityPickerView mPicker;
    public Provinces(Context context) {
        if (mPicker == null){
            //预先加载仿iOS滚轮实现的全部数据
            mPicker = new CityPickerView();
            mPicker.init(context);
        }
    }
    public void showData(OnCityItemClick onCityItemClick){
        CityConfig cityConfig = new CityConfig.Builder()
                //标题
                .title("选择城市")
                //标题文字大小
                .titleTextSize(18)
                //标题文字颜  色
                .titleTextColor("#585858")
                //标题栏背景色
                .titleBackgroundColor("#E9E9E9")
                //确认按钮文字颜色
                .confirTextColor("#585858")
                //取消按钮文字颜色
                .cancelTextColor("#585858")
                //确认按钮文字
                .confirmText("确认")
                //确认按钮文字大小
                .confirmTextSize(16)
                //取消按钮文字
                .cancelText("取消")
                //取消按钮文字大小
                .cancelTextSize(16)
                //显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                //是否显示半透明背景
                .showBackground(false)
                //显示item的数量
                .visibleItemsCount(7)
                //默认显示的省份
                .province("江苏省")
                //默认显示省份下面的城市
                .city("常州市")
                //默认显示省市下面的区县数据
                .district("武进区")
                //省份滚轮是否可以循环滚动
                .provinceCyclic(false)
                //城市滚轮是否可以循环滚动
                .cityCyclic(false)
                //区县滚轮是否循环滚动
                .districtCyclic(false)
                //自定义item的布局
                .setCustomItemLayout(R.layout.item_city)
                //自定义item布局里面的textViewid
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                //滚轮不显示模糊效果
                .drawShadows(false)
                //中间横线的颜色
                .setLineColor("#DFDFDF")
                //中间横线的高度
                .setLineHeigh(3)
                //是否显示港澳台数据，默认不显示
                .setShowGAT(false)
                .build();
        //设置自定义的属性配置
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                onCityItemClick.onSelected(province.getName(),city.getName(),district.getName());
                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {

            }
        });
        //显示
        mPicker.showCityPicker();
    }

    public interface OnCityItemClick{
        /**
         * 选择省市区成功
         * @param name
         * @param cityName
         * @param districtName
         */
        void onSelected(String name, String cityName, String districtName);

        /**
         * 取消按钮
         */
        void onCancel();
    }
}
