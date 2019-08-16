package com.bondex.ysl.battledore.workbench;

import com.bondex.ysl.battledore.util.Constant;
import com.bondex.ysl.camera.ui.utils.SHA;
import com.bondex.ysl.databaselibrary.base.bean.PlateType;

/**
 * date: 2019/7/10
 * Author: ysl
 * description:
 */
public class WorkBentchChoiceBean {

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
    public String toString() {
        return name;
    }
}
