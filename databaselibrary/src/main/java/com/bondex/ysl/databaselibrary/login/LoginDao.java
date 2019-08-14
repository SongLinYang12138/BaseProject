package com.bondex.ysl.databaselibrary.login;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * date: 2019/7/19
 * Author: ysl
 * description:
 */
@Dao
public interface LoginDao {

    @Insert
    void insert(LoginBean bean);

    @Update
    void update(LoginBean bean);

    @Query("SELECT * FROM LoginBean WHERE psncode in (:code)")
    LoginBean check(String code);

    @Query("SELECT * FROM LoginBean WHERE (psnname LIKE '%' || :name || '%')")
    List<LoginBean> selectByName(String name);

    @Query("SELECT * FROM LoginBean ORDER BY loginDate DESC")
    List<LoginBean> getByLoginDate();


}
