package com.bondex.ysl.battledore.misssion;

/**
 * date: 2019/6/13
 * Author: ysl
 * description:
 */
public class MissionChildBean {

    String title;
    boolean isSelect;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
