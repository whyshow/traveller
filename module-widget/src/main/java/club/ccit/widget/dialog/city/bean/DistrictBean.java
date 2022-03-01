package club.ccit.widget.dialog.city.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * FileName: DistrictBean
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:39 上午
 * Description:
 * Version:
 */
public class DistrictBean implements Parcelable {
    /*110101*/
    private String id;
    /*东城区*/
    private String name;

    @Override
    public String toString() {
        return name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public DistrictBean() {
    }

    protected DistrictBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<DistrictBean> CREATOR = new Parcelable.Creator<DistrictBean>() {
        @Override
        public DistrictBean createFromParcel(Parcel source) {
            return new DistrictBean(source);
        }

        @Override
        public DistrictBean[] newArray(int size) {
            return new DistrictBean[size];
        }
    };
}
