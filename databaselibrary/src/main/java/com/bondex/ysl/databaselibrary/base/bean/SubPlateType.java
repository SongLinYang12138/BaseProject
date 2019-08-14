package com.bondex.ysl.databaselibrary.base.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:垫板类型
 */
@Entity
public class SubPlateType {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "subplate_name")
    private String subplateName;
    @ColumnInfo(name = "subplate_id")
    private String subplateId;
    @ColumnInfo(name = "cityId")
    private int cityId;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @NonNull
    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(@NonNull String id) {
        this.id = id == null ? "" : id;
    }

    public String getSubplateName() {
        return subplateName == null ? "" : subplateName;
    }

    public void setSubplateName(String subplateName) {
        this.subplateName = subplateName == null ? "" : subplateName;
    }

    public String getSubplateId() {
        return subplateId == null ? "" : subplateId;
    }

    public void setSubplateId(String subplateId) {
        this.subplateId = subplateId == null ? "" : subplateId;
    }
}
