package com.bondex.ysl.battledore.util.interf;

import java.util.Collection;

/**
 * date: 2019/5/28
 * Author: ysl
 * description:
 */

public interface ModelCallback<E> {


    void onSuccess(E data);

    void onFaile(String msg);

}
