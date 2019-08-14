package com.bondex.ysl.camera;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2019/8/6
 * Author: ysl
 * description: 用于获取传送过来的主分单号，每一个分单号对应一张照片，可以一一保存
 */
public class MainHawbBean  implements Parcelable {

    private String mianCode;
    private String hawb;


    public MainHawbBean(String mianCode, String hawb) {
        this.mianCode = mianCode;
        this.hawb = hawb;
    }

    protected MainHawbBean(Parcel in) {
        mianCode = in.readString();
        hawb = in.readString();
    }

    public static final Creator<MainHawbBean> CREATOR = new Creator<MainHawbBean>() {
        @Override
        public MainHawbBean createFromParcel(Parcel in) {
            return new MainHawbBean(in);
        }

        @Override
        public MainHawbBean[] newArray(int size) {
            return new MainHawbBean[size];
        }
    };

    public String getMianCode() {
        return mianCode == null ? "" : mianCode;
    }

    public void setMianCode(String mianCode) {
        this.mianCode = mianCode == null ? "" : mianCode;
    }

    public String getHawb() {
        return hawb == null ? "" : hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb == null ? "" : hawb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mianCode);
        dest.writeString(hawb);
    }
}
