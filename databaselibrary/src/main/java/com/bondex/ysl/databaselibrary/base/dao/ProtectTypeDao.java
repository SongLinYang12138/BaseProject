package com.bondex.ysl.databaselibrary.base.dao;

import android.arch.persistence.room.*;
import com.bondex.ysl.databaselibrary.base.bean.ProtectType;

import java.util.List;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Dao
public interface ProtectTypeDao {

    @Insert
    void insert(List<ProtectType> list);

    @Query("DELETE FROM ProtectType WHERE cityId IN (:cityId)")
    void delet(int cityId);

    @Update
    void update(List<ProtectType> list);

    @Query("SELECT * FROM ProtectType WHERE cityId IN (:cityId)")
    List<ProtectType> getAll(int cityId);


}
