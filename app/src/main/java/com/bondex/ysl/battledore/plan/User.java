package com.bondex.ysl.battledore.plan;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.bondex.ysl.battledore.BR;

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */
public class User extends BaseObservable {


    private String name;
    private String age;
    private int height;

    public User() {
    }

    public User(String name, String age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Bindable
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {

        this.name = name == null ? "" : name;

        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAge() {
        return age == null ? "" : age;
    }

    public void setAge(String age) {
        this.age = age == null ? "" : age;

        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        notifyPropertyChanged(BR.height);

    }
}
