package com.bondex.ysl.databaselibrary.hawb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:分单数据
 */
//@Entity
public class HAWBBean implements Parcelable {
//    mBillCode:主单号，String
//    hawbCode:分单号，String
//    allQty:分单总件数,String
//    allWeight:分单总重量,String
//    allVoulme:分单总体积,String
//    hawbFlight:分单航班号,String
//    hwabDestination:分单目的港,String
//
//    @NonNull
//    @PrimaryKey
//    private String id;
//    @ColumnInfo(name = "mBillCode")
//    private String mBillCode;
//   @ColumnInfo(name = "hawb")
//    private String hawb;
//   @ColumnInfo(name = "qty")
//    private String Qty;
//   @ColumnInfo(name = "weight")
//    private String Weight;
//   @ColumnInfo(name = "volume")
//    private String Volume;
//   @ColumnInfo(name = "flight")
//    private String flight;
//   @ColumnInfo(name = "destination")
//    private String detination;
//   @ColumnInfo(name = "flight_date")
//    private String date;//航班日期


    private String id;
    private String mBillCode;
    private String hawb;
    private int Qty;
    private String Weight;
    private String Volume;
    private String flight;
    private String detination;
    private String date;//航班日期
    private int loadQty;


    protected HAWBBean(Parcel in) {
        id = in.readString();
        mBillCode = in.readString();
        hawb = in.readString();
        Qty = in.readInt();
        Weight = in.readString();
        Volume = in.readString();
        flight = in.readString();
        detination = in.readString();
        date = in.readString();
        loadQty = in.readInt();
    }

    public static final Creator<HAWBBean> CREATOR = new Creator<HAWBBean>() {
        @Override
        public HAWBBean createFromParcel(Parcel in) {
            return new HAWBBean(in);
        }

        @Override
        public HAWBBean[] newArray(int size) {
            return new HAWBBean[size];
        }
    };

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getmBillCode() {
        return mBillCode == null ? "" : mBillCode;
    }

    public void setmBillCode(String mBillCode) {
        this.mBillCode = mBillCode == null ? "" : mBillCode;
    }

    public String getHawb() {
        return hawb == null ? "" : hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb == null ? "" : hawb;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getWeight() {
        return Weight == null ? "" : Weight;
    }

    public void setWeight(String weight) {
        Weight = weight == null ? "" : weight;
    }

    public String getVolume() {
        return Volume == null ? "" : Volume;
    }

    public void setVolume(String volume) {
        Volume = volume == null ? "" : volume;
    }

    public String getFlight() {
        return flight == null ? "" : flight;
    }

    public void setFlight(String flight) {
        this.flight = flight == null ? "" : flight;
    }

    public String getDetination() {
        return detination == null ? "" : detination;
    }

    public void setDetination(String detination) {
        this.detination = detination == null ? "" : detination;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date == null ? "" : date;
    }

    @Override
    public boolean equals(Object obj) {

        HAWBBean bean = (HAWBBean) obj;

        if (bean == null) return false;

        return this.getmBillCode().equals(bean.getmBillCode()) && this.hawb.equals(bean.getHawb());
    }

    @Override
    public int hashCode() {
        return this.getmBillCode().hashCode() * this.getHawb().hashCode();
    }

    public HAWBBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mBillCode);
        dest.writeString(hawb);
        dest.writeInt(Qty);
        dest.writeString(Weight);
        dest.writeString(Volume);
        dest.writeString(flight);
        dest.writeString(detination);
        dest.writeString(date);
        dest.writeInt(loadQty);
    }
}

