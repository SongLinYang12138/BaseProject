package com.bondex.ysl.databaselibrary.base.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:保护类型
 */
@Entity
public class ProtectType {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "protectId")
    private String protectId;
    @ColumnInfo(name = "protectName")
    private String protectName;
    @ColumnInfo(name = "cityId")
    private int cityId;//机场三字码

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

    public String getProtectId() {
        return protectId == null ? "" : protectId;
    }

    public void setProtectId(String protectId) {
        this.protectId = protectId == null ? "" : protectId;
    }

    public String getProtectName() {
        return protectName == null ? "" : protectName;
    }

    public void setProtectName(String protectName) {
        this.protectName = protectName == null ? "" : protectName;
    }
}
