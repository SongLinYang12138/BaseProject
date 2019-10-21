package com.bondex.ysl.databaselibrary.airport;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2019/8/29
 * Author: ysl
 * description:
 */
@Entity
public class AirPort {

    @NonNull
    @ColumnInfo(name = "city")
    private String city;
    @NonNull
    @PrimaryKey
    private String code;//机场三字码
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "airport_name")
    private String airPortName;

    @NonNull
    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(@NonNull String city) {
        this.city = city == null ? "" : city;
    }

    @NonNull
    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(@NonNull String code) {
        this.code = code == null ? "" : code;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public void setCountry(String country) {
        this.country = country == null ? "" : country;
    }

    public String getAirPortName() {
        return airPortName == null ? "" : airPortName;
    }

    public void setAirPortName(String airPortName) {
        this.airPortName = airPortName == null ? "" : airPortName;
    }

    @Override
    public String toString() {
        return airPortName.toString() + "      " + code;
    }
}
