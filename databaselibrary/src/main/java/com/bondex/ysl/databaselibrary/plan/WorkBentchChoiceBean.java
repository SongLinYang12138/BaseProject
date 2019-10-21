package com.bondex.ysl.databaselibrary.plan;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2019/7/10
 * Author: ysl
 * description:
 */
public class WorkBentchChoiceBean implements Parcelable {


    private String name;
    private int id;
    private int count;

    public WorkBentchChoiceBean() {

    }

    public WorkBentchChoiceBean(String name, int id, int count) {
        this.name = name;
        this.id = id;
        this.count = count;
    }


    protected WorkBentchChoiceBean(Parcel in) {
        name = in.readString();
        id = in.readInt();
        count = in.readInt();
    }

    public static final Creator<WorkBentchChoiceBean> CREATOR = new Creator<WorkBentchChoiceBean>() {
        @Override
        public WorkBentchChoiceBean createFromParcel(Parcel in) {
            return new WorkBentchChoiceBean(in);
        }

        @Override
        public WorkBentchChoiceBean[] newArray(int size) {
            return new WorkBentchChoiceBean[size];
        }
    };

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {

        WorkBentchChoiceBean bean = (WorkBentchChoiceBean) obj;

        if (bean == null) return false;
        return this.id == bean.getId();
    }

    @Override
    public int hashCode() {

        return this.id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(count);
    }
}
