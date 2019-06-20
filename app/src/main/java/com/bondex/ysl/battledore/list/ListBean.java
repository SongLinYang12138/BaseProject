package com.bondex.ysl.battledore.list;

/**
 * date: 2019/6/17
 * Author: ysl
 * description:
 */
public class ListBean {

    private boolean isSelect;
    private String text;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
    }
}
