package com.bondex.ysl.battledore.install.download;

/**
 * date: 2018/11/12
 * Author: ysl
 * description:
 */
public interface DownloadListener {

    void onStart();//下载开始

    void onProgress(int progress);//下载进度

    void onFinish(String path,byte[] data);//下载完成

    void onFail(String errorInfo);//下载失败

}
