package com.bondex.ysl.databaselibrary.hawb.img;

import android.arch.persistence.room.*;

import java.util.List;

/**
 * date: 2019/8/5
 * Author: ysl
 * description:
 */
@Dao
public interface HAWBImgDao {

    @Insert
    void insert(HAWBImgBean bean);

    @Insert
    void insertList(List<HAWBImgBean> beans);

    @Update
    void update(HAWBImgBean bean);

    @Delete
    void delete(HAWBImgBean bean);

    @Delete
    void delteAll(List<HAWBImgBean> list);


    @Query("SELECT * FROM HAWBImgBean WHERE main_code IN (:mainCode) and hawb IN (:hawb) ")
    List<HAWBImgBean> getHAWBImage(String mainCode, String hawb);

    @Query("SELECT * FROM HAWBImgBean WHERE ID IN (:id)")
    HAWBImgBean check(String id);

    @Query("SELECT * FROM HAWBImgBean WHERE  main_code IN (:mHwab) and hawb IN (:hawb)")
    HAWBImgBean selectByHawb(String mHwab,String hawb);
}
