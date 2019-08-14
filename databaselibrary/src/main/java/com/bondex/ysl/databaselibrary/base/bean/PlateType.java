package com.bondex.ysl.databaselibrary.base.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:板型
 */
@Entity
public class PlateType {

    @PrimaryKey
    @NonNull
    private String id;//id有三字码和plateid组成
    @ColumnInfo(name = "plateId")
    private String plateId;
    @ColumnInfo(name = "plate_name")
    private String plateName;
    @ColumnInfo(name = "cityId")
    private int cityId;//这里的cityId等于每个机场的三字码

    @NonNull
    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(@NonNull String id) {
        this.id = id == null ? "" : id;
    }

    public String getPlateId() {
        return plateId == null ? "" : plateId;
    }

    public void setPlateId( String plateId) {
        this.plateId = plateId == null ? "" : plateId;
    }

    public String getPlateName() {
        return plateName == null ? "" : plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName == null ? "" : plateName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
