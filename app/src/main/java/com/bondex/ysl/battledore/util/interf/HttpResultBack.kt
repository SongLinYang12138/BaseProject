package com.bondex.ysl.battledore.util.interf

/**
 * date: 2019/5/29
 * Author: ysl
 * description:
 */
interface HttpResultBack {

    open fun onSuccess( data:String)


    open fun onFaile(error:String)

}