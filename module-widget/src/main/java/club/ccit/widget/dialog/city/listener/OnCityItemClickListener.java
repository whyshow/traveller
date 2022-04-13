package club.ccit.widget.dialog.city.listener;

import club.ccit.widget.dialog.city.bean.CityBean;
import club.ccit.widget.dialog.city.bean.DistrictBean;
import club.ccit.widget.dialog.city.bean.ProvinceBean;

/**
 * FileName: OnCityItemClickListener
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:37 上午
 * Description:
 * Version:
 */
public abstract class OnCityItemClickListener {
    /**
     * 当选择省市区三级选择器时，需要覆盖此方法
     * @param province
     * @param city
     * @param district
     */
    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

    }

    /**
     * 取消
     */
    public void onCancel() {

    }
}
