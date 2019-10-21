package com.bondex.ysl.camera;

import android.os.Parcel;
import android.os.Parcelable;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;

import java.util.ArrayList;

/**
 * date: 2019/8/6
 * Author: ysl
 * description:
 */
public class ISCameraConfig implements Parcelable{

    private ArrayList<HAWBBean> hawbs;



    public ISCameraConfig(Builder builder) {

        this.hawbs = builder.hawbBeans;
    }

    protected ISCameraConfig(Parcel in) {
        hawbs = in.createTypedArrayList(HAWBBean.CREATOR);
    }

    public static final Creator<ISCameraConfig> CREATOR = new Creator<ISCameraConfig>() {
        @Override
        public ISCameraConfig createFromParcel(Parcel in) {
            return new ISCameraConfig(in);
        }

        @Override
        public ISCameraConfig[] newArray(int size) {
            return new ISCameraConfig[size];
        }
    };

    public ArrayList<HAWBBean> getHawbs() {
        if (hawbs == null) return new ArrayList<>();
        return hawbs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(hawbs);
    }


    public static class Builder implements Parcelable {

        private ArrayList<HAWBBean> hawbBeans;


        public Builder() {

        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        public Builder setHawbBeans(ArrayList<HAWBBean> hawbBeans) {
            this.hawbBeans = hawbBeans;

            return this;
        }

        protected Builder(Parcel in) {
            hawbBeans = in.createTypedArrayList(HAWBBean.CREATOR);
        }



        public ISCameraConfig build() {

            return new ISCameraConfig(this);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(hawbBeans);
        }
    }
}
