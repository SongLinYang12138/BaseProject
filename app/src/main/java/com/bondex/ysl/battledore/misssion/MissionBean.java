package com.bondex.ysl.battledore.misssion;

import java.util.List;

/**
 * date: 2019/6/5
 * Author: ysl
 * description:
 */
public class MissionBean {

    private String mainCode;
    private List<String> hawbs;
    private boolean isSelected;

    public String getMainCode() {
        return mainCode == null ? "" : mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode == null ? "" : mainCode;
    }

    public List<String> getHawbs() {
        return hawbs;
    }

    public void setHawbs(List<String> hawbs) {
        this.hawbs = hawbs;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
