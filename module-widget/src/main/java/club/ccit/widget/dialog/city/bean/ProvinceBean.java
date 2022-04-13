package club.ccit.widget.dialog.city.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * FileName: ProvinceBean
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:38 上午
 * Description:
 * Version:
 */
public class ProvinceBean implements Parcelable {

    private String id; /*110101*/

    private String name; /*东城区*/


    private ArrayList<CityBean> cityList;



    @Override
    public String toString() {
        return  name ;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityBean> cityList) {
        this.cityList = cityList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.cityList);
    }

    public ProvinceBean() {
    }

    protected ProvinceBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.cityList = in.createTypedArrayList(CityBean.CREATOR);
    }

    public static final Parcelable.Creator<ProvinceBean> CREATOR = new Creator<ProvinceBean>() {
        @Override
        public ProvinceBean createFromParcel(Parcel source) {
            return new ProvinceBean(source);
        }

        @Override
        public ProvinceBean[] newArray(int size) {
            return new ProvinceBean[size];
        }
    };
}