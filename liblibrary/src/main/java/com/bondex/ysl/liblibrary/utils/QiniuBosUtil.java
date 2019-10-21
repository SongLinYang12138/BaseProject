package com.bondex.ysl.liblibrary.utils;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by YHF on 2017/10/20.
 */

public class QiniuBosUtil {
    private static String AccessKey = "KFFzg_qdWFv2rjb7TtXbr3wdauIqQTMn44kps3wa";//此处填你自己的AccessKey
    private static String SecretKey = "n8m6ECH2BaRaLSHkqwc9zzpXm7hVdYolZyCsahNZ";
    private static UploadManager uploadManager = new UploadManager();
    public static void upload(String picPath, String key, UpCompletionHandler handler) {

        // 设置图片名字
        uploadManager.put(picPath, key, Auth.create(AccessKey, SecretKey).uploadToken("fortest"), handler, null);

    }

}
