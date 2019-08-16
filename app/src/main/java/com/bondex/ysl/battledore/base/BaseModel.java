package com.bondex.ysl.battledore.base;

import com.bondex.ysl.battledore.util.interf.ModelCallback;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */
public interface BaseModel<T> {

    void  iniData(T param, ModelCallback callback);
}
