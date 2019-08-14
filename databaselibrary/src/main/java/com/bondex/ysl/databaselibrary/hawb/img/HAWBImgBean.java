package com.bondex.ysl.databaselibrary.hawb.img;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * date: 2019/8/5
 * Author: ysl
 * description:
 */
@Entity
public class HAWBImgBean {

    @NonNull
    @PrimaryKey()
    private String ID;//主单号+分单号生成
    @ColumnInfo(name = "main_code")
    private String mainCode;
    @ColumnInfo(name = "hawb")
    private String hawb;
    @ColumnInfo(name = "path")
    private String path;
    @ColumnInfo(name = "imgName")
    private String imgName;
    @ColumnInfo(name = "type")
    private int type = 0;//代表该img所属状态 0未打板的分单，  1已打板完毕的分单，

    @NonNull
    public String getID() {
        return ID == null ? "" : ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID == null ? "" : ID;
    }

    public String getMainCode() {
        return mainCode == null ? "" : mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode == null ? "" : mainCode;
    }

    public String getHawb() {
        return hawb == null ? "" : hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb == null ? "" : hawb;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path == null ? "" : path;
    }

    public String getImgName() {
        return imgName == null ? "" : imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null ? "" : imgName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
